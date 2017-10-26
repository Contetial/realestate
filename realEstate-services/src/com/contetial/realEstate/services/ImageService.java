package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.contetial.realEstate.persistance.entity.Gallery;
import com.contetial.realEstate.persistance.entity.ImageDetails;
import com.contetial.realEstate.persistance.entity.ImageProperties;
import com.contetial.realEstate.persistance.exception.DataAccessLayerException;
import com.contetial.realEstate.repository.ImageRepository;
import com.contetial.realEstate.utility.copy.CopyUtils;
import com.contetial.realEstate.utility.exception.ServiceException;

public class ImageService {
 
	@Autowired
	ImageRepository imgRepo;
	
	public ImageProperties findImageById(Long id){
		ImageProperties img= imgRepo.findImagePropertiesById(id);
		return img;
	}
	
	public ImageDetails findDetailsById(Long id)throws DataAccessLayerException {
		return imgRepo.findImageDetailsById(id);
	}
	
	public List<ImageProperties> findImagesByGallery(Gallery gallery){		
		ImageProperties iProps = new ImageProperties();
		ImageDetails iDetails = new ImageDetails();
		iDetails.setGallery(gallery);
		iProps.setImageDetails(iDetails);
		
		return imgRepo.findAllImages(iProps);
	}
	
	public boolean add(ImageProperties detailimage) throws ServiceException{
		try{
			imgRepo.add(detailimage);
		}catch(DataAccessLayerException de){
			throw new ServiceException(de);
		}
		return true;
	}
	
	public boolean update(ImageProperties detailimage){
		ImageProperties img=updateBean(detailimage);
		CopyUtils.copyBean(img, detailimage);
		imgRepo.update(img);
	   return true;
	}
	
	public ImageProperties updateBean(ImageProperties img){
		ImageProperties update=imgRepo.findImagePropertiesById(img.getImageDetails().getImageid());
		return update;
	}
	
	public boolean delete(List<Long> imgIds){
		List<ImageDetails> images = new ArrayList<ImageDetails>();
		for(Long imgId:imgIds){
			ImageDetails del=findDetailsById(imgId);
			images.add(del);			
		}
		imgRepo.delete(images);
		return true;
	}

	public void loadThumbs(ImageProperties image) {
		imgRepo.loadImageThumbFromDisk(image);		
	}
		
}
