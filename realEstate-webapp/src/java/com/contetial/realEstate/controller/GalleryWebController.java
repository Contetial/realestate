package com.contetial.realEstate.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.contetial.realEstate.persistance.entity.VideoDetails;
import com.contetial.realEstate.services.GalleryService;
import com.contetial.realEstate.utility.exception.ServiceException;

@RestController
@RequestMapping("/galleryService")
public class GalleryWebController {

	@Autowired
	GalleryService galleryService;
	
	@RequestMapping(value="/getGalleryById/{galleryId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public Gallery getGalleryById(@PathVariable Long galleryId){					
		return galleryService.findGById(galleryId);
	}
	
	@RequestMapping(value="/getGalleryByPropertyId/{propertyId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public List<Gallery> getGalleryByProperty(@PathVariable Long propertyId){
		Property prop = new Property();
		prop.setPropertyId(propertyId);
		return galleryService.findGalleryByProperty(prop);
	}
	
	@RequestMapping(value="/addGallery",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public boolean createGallery(@RequestBody Gallery gallery) {
        galleryService.add(gallery);
        return true;
    }
	
	@RequestMapping(value="/updateGallery",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public boolean updateGallery(@RequestBody Gallery gallery) {        
        galleryService.update(gallery);
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/deleteGallery",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})
    public boolean deleteGallery(@RequestBody String galleryIdsStr) {
    	String[] galleryIds = galleryIdsStr.split(",");
    	List galIds = Arrays.asList(galleryIds);
        return galleryService.delete(galIds);
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
				galleryService.addImage(imageProperty);
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
			galleryService.updateImage(imageProperty);
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
		Gallery gal = galleryService.findGById(Long.valueOf(galleryId));	
		iDetails.setImagename(imageName);
		iDetails.setGallery(gal);
		imageProperty.setImageDetails(iDetails);
		imageProperty.setImage(file.getBytes());
		return imageProperty;
	}
	
	@RequestMapping(value="/addVideo",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public ResponseEntity<MessageTransfer> addVideo(
    		@RequestBody VideoDetails videoDetails) {
		String returnMessage="video uploaded successfully";
		//Check if gallery is present, if not then create it first
		if(null== videoDetails.getGallery()
				|| null== videoDetails.getGallery().getGalleryid()){		
			if(null!=videoDetails.getPropertyId()){
				Gallery refGallery = new Gallery();
				Property prop = new Property();
				prop.setPropertyId(Long.valueOf(videoDetails.getPropertyId()));
				refGallery.setProperty(prop);
				Long galleryId = galleryService.add(refGallery);
				refGallery.setGalleryid(galleryId);
				videoDetails.setGallery(refGallery);
			}else{
				returnMessage="Gallery Id or Property Id required";
				return new ResponseEntity<MessageTransfer>(
						new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		//}else{//Gallery ID is present, no action required
		}
		if(!videoDetails.getVideopath().isEmpty()){
			try {
				Long gId = videoDetails.getGallery().getGalleryid();
				Gallery gal = galleryService.findGById(Long.valueOf(gId));
				videoDetails.setGallery(gal);
				galleryService.addVideo(videoDetails);				
			} catch (ServiceException e) {
				e.printStackTrace();
				returnMessage="error while uploading video";
				return new ResponseEntity<MessageTransfer>(
						new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		else{
			returnMessage="video path is required";
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(returnMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(returnMessage),HttpStatus.OK);
		
        
    }
	
	@RequestMapping(value="/updateVideo",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public boolean updateVideo(@RequestBody VideoDetails videoProperty) {        
        return galleryService.updateVideo(videoProperty);
    }
}
