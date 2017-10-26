package com.contetial.realEstate.repository;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

import com.contetial.realEstate.persistance.entity.ImageDetails;
import com.contetial.realEstate.persistance.entity.ImageProperties;
import com.contetial.realEstate.persistance.exception.DataAccessLayerException;
import com.contetial.realEstate.persistance.worker.WorkUnit;
import com.contetial.realEstate.utility.fileIO.FileOpps;
import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class ImageRepository extends GenericRepository{
	
	public ImageRepository(){
		super(ImageRepository.class);
	}

	/* FIND */
	public ImageProperties findImagePropertiesById(Long id) throws DataAccessLayerException {
		ImageProperties img = null;
		ImageDetails imgdetail = (ImageDetails) findById(ImageDetails.class,id);
		
		if (null != imgdetail) {
			img = new ImageProperties();
			img.setImageDetails(imgdetail);
			loadImageFromDisk(img);
		}
		return img;
	}
	
	public ImageDetails findImageDetailsById(Long id)throws DataAccessLayerException {		
		return (ImageDetails) findById(ImageDetails.class, id);
	}

	/* loadImageFromDisk */
	private boolean loadImageFromDisk(ImageProperties properties)
			throws DataAccessLayerException {
		String imagePath = properties.getImageDetails().getImagepath();
		String ithumbPath = properties.getImageDetails().getIthumbadd();
		try {
			properties.setImage(FileOpps.readFileFromDisk(imagePath));
			
		} catch (IOException e) {
			logger.error(e);
			throw new DataAccessLayerException("error reading image at path: "+ imagePath, e);
			}
		try{
			properties.setImgThumb(FileOpps.readFileFromDisk(ithumbPath));
		}
		catch (IOException ex) {
			logger.error(ex);
			throw new DataAccessLayerException("error reading ithumbnail at ithumbPath: "+ ithumbPath, ex);
			}
		
		return true;
	}
	
	/* loadImageFromDisk */
	public boolean loadImageThumbFromDisk(ImageProperties properties)
			throws DataAccessLayerException {
		String ithumbPath = properties.getImageDetails().getIthumbadd();
		try{
			properties.setImgThumb(FileOpps.readFileFromDisk(ithumbPath));
		}
		catch (IOException ex) {
			logger.error(ex);
			throw new DataAccessLayerException("error reading ithumbnail at ithumbPath: "+ ithumbPath, ex);
		}		
		return true;
	}
	
	/*FIND ALL*/
	@SuppressWarnings("unchecked")
	public List<ImageProperties> findAllImages(ImageProperties properties) throws DataAccessLayerException{
		List<ImageDetails> imgs= (List<ImageDetails>) findEntity(properties.getImageDetails());		
		return findAndLoadImages(imgs);  
	}	
	
	/*FIND AND LOAD IMG (FIND ALL)*/
	private List<ImageProperties> findAndLoadImages(List<ImageDetails> imgs) {
		List<ImageProperties> propertiesList = new ArrayList<ImageProperties>();		
		for (ImageDetails obj:imgs){
			ImageProperties newProperties =null;
			if(null!=obj && obj instanceof ImageDetails){
				ImageDetails img = (ImageDetails) obj;
				newProperties = new ImageProperties();
				newProperties.setImageDetails(img);			 
				loadImageFromDisk(newProperties);
			}
			propertiesList.add(newProperties);
		}
		return propertiesList;
	}
	

	/* ADD */
	public boolean add(ImageProperties properties) throws DataAccessLayerException {
		String imagePath = "No Path";
		String thumbPath = "No Path";
		imagePath = saveImageToDisk(properties);
		properties.getImageDetails().setImagepath(imagePath);
		thumbPath = saveThumbToDisk(properties);
		ImageDetails event = properties.getImageDetails();
		event.setImagepath(imagePath);
		event.setIthumbadd(thumbPath);
		addEntity(event);
		return true;
	}

	/*saveImageToDisk*/
	private String saveImageToDisk(ImageProperties properties)throws DataAccessLayerException {
		String path="";
		try {

			String fileSeparator = FileSystems.getDefault().getSeparator();
			String imageFolderPath = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("ImageLocation");
			if(null==imageFolderPath || imageFolderPath.isEmpty()){
				imageFolderPath="D:\\temp\\img";
			}else if(!"\\".equals(imageFolderPath.substring(imageFolderPath.length()-1))){
				imageFolderPath = imageFolderPath+fileSeparator;
			}
			
			path = imageFolderPath 
					+ properties.getImageDetails().getImagename()
					+".jpg";

			FileOpps.saveFileOnDisk(properties.getImage(), path);			
		} catch (IOException e) {
			logger.error(e);
			throw new DataAccessLayerException("Error saving image",e);
		}		
		return path;
	}

	/*saveThumbToDisk*/
	private String saveThumbToDisk(ImageProperties properties)throws DataAccessLayerException {
		String path="";
		try {

			String fileSeparator = FileSystems.getDefault().getSeparator();
			String imageFolderPath = ReadConfigurations.getInstance("./conf/application.properties").
					getProps().getProperty("ImageLocation");
			if(null==imageFolderPath || imageFolderPath.isEmpty()){
				imageFolderPath="D:\\temp\\img";
			}else if(!"\\".equals(imageFolderPath.substring(imageFolderPath.length()-1))){
				imageFolderPath = imageFolderPath+fileSeparator;
			}
					
			
			path = imageFolderPath 
					+ properties.getImageDetails().getImagename()
					+"thumbnail.jpg";

			Thumbnails.of(properties.getImageDetails().getImagepath())
	        .size(90, 90)
	        .toFile(path);			
		} catch (IOException e) {
			logger.error(e);
			throw new DataAccessLayerException("Error saving thumb",e);
		}		
		return path;
	}
	
	/* UPDATE */
	public boolean update(ImageProperties properties)
			throws DataAccessLayerException {
		String imagePath = "No Path";
		imagePath = saveImageToDisk(properties);
		String thumbPath = "No Path";
		thumbPath = saveThumbToDisk(properties);
		
		ImageDetails event = properties.getImageDetails();
		event.setImagepath(imagePath);
		event.setImagepath(thumbPath);
		updateEntity(event);
		return true;

	}

	/*checkandSaveImageToDisk
	private String checkandSaveImageToDisk(ImageProperties properties)
			throws DataAccessLayerException {
		if (null != properties.getimageDetails().getImageid()) {
			File validateFile = new File(properties.getimageDetails()
					.getImagepath());
			if (validateFile.exists()) {
				throw new DataAccessLayerException("File Already Exists");
			}
		}
		return saveImageToDisk(properties);
	}*/

	/* DELETE */
	public boolean delete(List<ImageDetails> events) throws DataAccessLayerException {
		for(ImageDetails event:events){
			deleteImageFromDisk(event);
		}
		deleteEntity(events);
		return true;
	}
	
  /* public boolean deleteAll(List<ImageDetails> imgIds) throws DataAccessLayerException {
		
		workunit.beginTransaction();
		for(ImageDetails img:imgIds){
			deleteImageFromDisk(img);
			workunit.delete(img);
		}
		workunit.commitTransaction();
		return true;
	}*/
	

	/*deleteImageFromDisk*/
	private boolean deleteImageFromDisk(ImageDetails properties)
			throws DataAccessLayerException {
		String imagePath = properties.getImagepath();
		FileOpps.deleteImage(imagePath);
		return true;
	}

	public Long addWithParentTransaction(WorkUnit parentWorkUnit, ImageProperties imageProperty) {
		String imagePath = "No Path";
		String thumbPath = "No Path";
		imagePath = saveImageToDisk(imageProperty);
		imageProperty.getImageDetails().setImagepath(imagePath);
		thumbPath = saveThumbToDisk(imageProperty);
		ImageDetails event = imageProperty.getImageDetails();
		event.setImagepath(imagePath);
		event.setIthumbadd(thumbPath);
		parentWorkUnit.beginTransaction();
		Long generatedId = (Long) parentWorkUnit.save(event);
		parentWorkUnit.commitTransaction();		
		return generatedId;		
	}

	public ImageDetails updateWithParentTransaction(WorkUnit parentWorkUnit, ImageProperties properties) {
		String imagePath = "No Path";
		imagePath = saveImageToDisk(properties);
		String thumbPath = "No Path";
		thumbPath = saveThumbToDisk(properties);
		
		ImageDetails event = properties.getImageDetails();
		event.setImagepath(imagePath);
		event.setImagepath(thumbPath);
		parentWorkUnit.beginTransaction();
		ImageDetails updatedImageDetails = (ImageDetails) parentWorkUnit.saveOrUpdate(event);
		parentWorkUnit.commitTransaction();
		return updatedImageDetails;
		
	}

	public ImageProperties findImgPropsByIdWithParentTransaction(WorkUnit parentWorkUnit, Long imageId) {
		ImageProperties img = null;
		ImageDetails imgdetail = (ImageDetails) parentWorkUnit.find(ImageDetails.class,imageId);
		
		if (null != imgdetail) {
			img = new ImageProperties();
			img.setImageDetails(imgdetail);
			loadImageFromDisk(img);
		}
		return img;
	}

}
