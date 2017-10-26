package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.contetial.realEstate.persistance.entity.Gallery;
import com.contetial.realEstate.persistance.entity.ImageProperties;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.persistance.entity.VideoDetails;
import com.contetial.realEstate.persistance.exception.DataAccessLayerException;
import com.contetial.realEstate.repository.ImageRepository;
import com.contetial.realEstate.utility.copy.CopyUtils;
import com.contetial.realEstate.utility.exception.ServiceException;

public class GalleryService extends GenericService {

	@Autowired
	VideoService videoService;
	
	@Autowired
	ImageRepository imgRepo;
	
	public GalleryService(){
		super(GalleryService.class);
	}
	
	public Gallery findGById(Long id){
		Gallery gallery=(Gallery) findById(Gallery.class,id);
		return gallery;
	}
	
	@SuppressWarnings("unchecked")
	public List<Gallery> findGalleryByProperty(Property prop){
		Gallery fGal = new Gallery();
		fGal.setProperty(prop);
		List<Gallery> galleries = (List<Gallery>) findEntity(fGal);
		return galleries;
	}

	public Long add(Gallery gallery){		
		return addEntity(gallery);
	}
	
	public Gallery update(Gallery gallery){		
		Gallery idgallery = findGById(gallery.getGalleryid());		
		return (Gallery)updateEntity((Gallery) copyBean(idgallery,gallery));
	}
	
	public boolean delete(List<String> gallerydel){		
		List<Gallery> del = new ArrayList<Gallery>();
		for(String delId: gallerydel){
			Gallery gal = findGById(Long.valueOf(delId));
			del.add(gal);
		}
		deleteEntity(del);
		return true;
	}

	public Long addImage(ImageProperties imageProperty) throws ServiceException {
		Long imageId=null;
		try{
			imageId = imgRepo.addWithParentTransaction(workUnit,imageProperty);
		}catch(DataAccessLayerException de){
			throw new ServiceException(de);
		}
		return imageId;		
	}
	
	public boolean updateImage(ImageProperties detailimage){
		ImageProperties img=updateBean(detailimage);
		CopyUtils.copyBean(img, detailimage);
		imgRepo.updateWithParentTransaction(workUnit,img);
		return true;
	}
	
	public ImageProperties updateBean(ImageProperties img){
		ImageProperties update=imgRepo.findImgPropsByIdWithParentTransaction(
				workUnit,img.getImageDetails().getImageid());
		return update;
	}
	
	public Long addVideo(VideoDetails videoDetails) throws ServiceException{
		Long id = (Long) videoService.addVideoWithParentTransaction(workUnit,videoDetails);
		return id;
	}
	
	public boolean updateVideo(VideoDetails videoDetails){
		videoService.updateVideoWithParentTransaction(workUnit, videoDetails);
		return true;
	}
}