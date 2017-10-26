package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import com.contetial.realEstate.persistance.entity.Gallery;
import com.contetial.realEstate.persistance.entity.VideoDetails;
import com.contetial.realEstate.persistance.worker.WorkUnit;
import com.contetial.realEstate.utility.copy.CopyUtils;

public class VideoService extends GenericService{
	
	public VideoService(){
		super(VideoService.class);
	}
	
	public VideoDetails findVideoDetailsById(Long id){
		VideoDetails video= (VideoDetails) findById(VideoDetails.class,id);
		return video;
	}
	
	@SuppressWarnings("unchecked")
	public List<VideoDetails> findVideosByGallery(Gallery gallery) {
		VideoDetails vDetails = new VideoDetails();
		vDetails.setGallery(gallery);
		return (List<VideoDetails>) findEntity(vDetails);
	}
	
	public Long add(VideoDetails videoDetails){		
		return add(videoDetails);
	}
	
	public boolean update(VideoDetails videoDetails){
		VideoDetails video=updateBean(videoDetails);
		CopyUtils.copyBean(video, videoDetails);		
		return update(video);
	}
	
	public VideoDetails updateBean(VideoDetails video){
		VideoDetails update = findVideoDetailsById(video.getVideoid());
		return update;
	}
	
	public boolean delete(List<Long> videoIds){
		List<VideoDetails> videos = new ArrayList<VideoDetails>();
		for(Long videoId: videoIds){
			VideoDetails del = findVideoDetailsById(videoId);
			videos.add(del);			
		}
		deleteEntity(videos);
		return true;
	}
	
	public Long addVideoWithParentTransaction(WorkUnit parentWorkUnit, VideoDetails videoDetails){
		parentWorkUnit.beginTransaction();
		Long id = (Long) parentWorkUnit.save(videoDetails);
		parentWorkUnit.commitTransaction();
		return id;
	}
	
	public boolean updateVideoWithParentTransaction(WorkUnit parentWorkUnit, VideoDetails videoDetails){
		VideoDetails video=updateVideoBeanWithParentTransaction(parentWorkUnit, videoDetails);
		CopyUtils.copyBean(video, videoDetails);		
		parentWorkUnit.saveOrUpdate(video);
		return true;
	}
	
	private VideoDetails updateVideoBeanWithParentTransaction(WorkUnit parentWorkUnit, VideoDetails video){
		VideoDetails update = (VideoDetails) parentWorkUnit.find(VideoDetails.class,video.getVideoid());
		return update;
	}
}
