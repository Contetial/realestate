package com.contetial.realEstate.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.contetial.realEstate.controller.transfer.MessageTransfer;
import com.contetial.realEstate.persistance.entity.Gallery;
import com.contetial.realEstate.persistance.entity.ImageDetails;
import com.contetial.realEstate.persistance.entity.ImageProperties;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.services.GalleryService;
import com.contetial.realEstate.services.ImageService;
import com.contetial.realEstate.utility.exception.ServiceException;

@RestController
@RequestMapping("/imageService")
public class ImageWebController {

	@Autowired
	ImageService imageService;
	
	@Autowired
	GalleryService galleryService;
	
	@RequestMapping(value="/getImageById/{imageId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public ImageProperties getImageById(@PathVariable Long imageId){					
		return imageService.findImageById(imageId);
	}
	
	@RequestMapping(value="/getImagesByGalleryId/{galleryId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public List<ImageProperties> getImagesByGalleryId(@PathVariable Long galleryId){
		Gallery gallery = new Gallery();
		gallery.setGalleryid(galleryId);
		return imageService.findImagesByGallery(gallery);
	}
	
	@RequestMapping(value="/loadImageThumb/{imageId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json")
	public ResponseEntity<InputStreamResource> getImageThumb(@PathVariable Long imageId) {
		ImageDetails details = imageService.findDetailsById(imageId);
		return loadImage(imageId, details.getIthumbadd());
	}
	
	@RequestMapping(value="/loadImage/{imageId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json")
	public ResponseEntity<InputStreamResource> getImage(@PathVariable Long imageId) {
		ImageDetails details = imageService.findDetailsById(imageId);
		return loadImage(imageId, details.getImagepath());
	}

	private ResponseEntity<InputStreamResource> loadImage(Long imageId, String imagePath) {
		
		File imageFile = new File(imagePath);
		InputStream fileInputStream=null;
		try {
			fileInputStream = new FileInputStream(imageFile);			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				fileInputStream = new FileInputStream(new File("./conf/noThumb.jpg"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return ResponseEntity.ok()
				.contentLength( imageFile.length() )
				.contentType( MediaType.IMAGE_JPEG )
				.body(new InputStreamResource( fileInputStream ));
	}
	
	@RequestMapping(value="/addImage",
			method = RequestMethod.POST)
    public ResponseEntity<MessageTransfer> addImage(
    		@RequestParam ("galleryId") String galleryId,
    		@RequestParam ("propertyId") String propertyId,
    		@RequestParam ("imageName") String imageName,
    		@RequestParam ("file") MultipartFile file) {
		String returnMessage="image uploaded successfully";
		//Check if gallery is present, if not then create it first
		if(galleryId.isEmpty() || "undefined".equals(galleryId)){
			if(!propertyId.isEmpty() && !"undefined".equals(propertyId)){
				Gallery refGallery = new Gallery();
				Property prop = new Property();
				prop.setPropertyId(Long.valueOf(propertyId));
				refGallery.setProperty(prop);
				galleryId = galleryService.add(refGallery).toString();				
			}else{
				returnMessage="Gallery Id or Property Id required";
				return new ResponseEntity<MessageTransfer>(
						new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		//}else{//Gallery ID is present, no action required
		}
		if(!file.isEmpty()){
			ImageProperties imageProperty;
			try {
				imageProperty = processImageProperties(galleryId, imageName, file);
				imageService.add(imageProperty);
			} catch (IOException | ServiceException e) {
				e.printStackTrace();
				returnMessage="error while uploading image";
				return new ResponseEntity<MessageTransfer>(
						new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		else{
			returnMessage="image is required";
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(returnMessage),HttpStatus.OK);
    }

	@RequestMapping(value="/updateImage",
			method = RequestMethod.PUT)
    public ResponseEntity<MessageTransfer> updateImage(
    		@RequestParam ("galleryId") String galleryId,
    		@RequestParam ("imageName") String imageName,
    		@RequestParam ("file") MultipartFile file) {
		String returnMessage="image updated successfully";
		if(!file.isEmpty()){
			ImageProperties imageProperty;
			try {
				imageProperty = processImageProperties(galleryId, imageName, file);
			} catch (IOException e) {
				e.printStackTrace();
				returnMessage="error while uploading image";
				return new ResponseEntity<MessageTransfer>(
						new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			imageService.update(imageProperty);
		}else{
			returnMessage="image is required";
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(returnMessage),HttpStatus.OK);
    }

	private ImageProperties processImageProperties(
			String galleryId, String imageName, MultipartFile file) throws IOException {
		ImageProperties imageProperty = new ImageProperties();
		ImageDetails iDetails = new ImageDetails();
		Gallery gal = new Gallery();
		gal.setGalleryid(Long.valueOf(galleryId));
		iDetails.setImagename(imageName);
		iDetails.setGallery(gal);
		imageProperty.setImageDetails(iDetails);
		imageProperty.setImage(file.getBytes());
		return imageProperty;
	}
	
	
    @RequestMapping(value="/deleteImage",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})
    public boolean deleteImage(@RequestBody String imageIdsStr) {
    	String[] imageIds = imageIdsStr.split(",");
    	List<Long> imgIds = new ArrayList<Long>();
    	for(String id:Arrays.asList(imageIds)){
    		imgIds.add(Long.valueOf(id));
    	}    	
        return imageService.delete(imgIds);
    }
    
    /**
     * Handles chunked file upload, when file exceeds defined chunked size.
     * 
     * This method is also called by modern browsers and IE >= 10
     */
   /* @RequestMapping(value = "/content-files/upload/", 
    		method = RequestMethod.POST, 
    		headers = "content-type!=multipart/form-data")
    public UploadedFile uploadChunked(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        request.getHeader("content-range");//Content-Range:bytes 737280-819199/845769
        request.getHeader("content-length"); //845769
        request.getHeader("content-disposition"); // Content-Disposition:attachment; filename="Screenshot%20from%202012-12-19%2017:28:01.png"
        request.getInputStream(); //actual content.

        //Regex for content range: Pattern.compile("bytes ([0-9]+)-([0-9]+)/([0-9]+)");
        //Regex for filename: Pattern.compile("(?<=filename=\").*?(?=\")");

        //return whatever you want to json
        return new UploadedFile();
    }*/

    /**
     * Default Multipart file upload. This method should be invoked only by those that do not
     * support chunked upload.
     * 
     * If browser supports chunked upload, and file is smaller than chunk, it will invoke
     * uploadChunked() method instead.
     * 
     * This is instead a fallback method for IE <=9
     */
    /*@RequestMapping(value = "/content-files/upload/", 
    		method = RequestMethod.POST, 
    		headers = "content-type=multipart/form-data")
    @ResponseBody
    public HttpEntity<UploadedFile> uploadMultipart(
            final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestParam("file") final MultipartFile multiPart) {

        //handle regular MultipartFile

        // IE <=9 offers to save file, if it is returned as json, so set content type to plain.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new HttpEntity<>(new UploadedFile(), headers);
    }*/
}
