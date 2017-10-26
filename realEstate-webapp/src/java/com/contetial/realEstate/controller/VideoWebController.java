package com.contetial.realEstate.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contetial.realEstate.persistance.entity.Gallery;
import com.contetial.realEstate.persistance.entity.VideoDetails;
import com.contetial.realEstate.services.VideoService;

@RestController
@RequestMapping("/videoService")
public class VideoWebController {

	@Autowired
	VideoService videoService;
	
	@RequestMapping(value="/getVideoById/{videoId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public VideoDetails getVideoById(@PathVariable Long videoId){					
		return videoService.findVideoDetailsById(videoId);
	}
	

	@RequestMapping(value="/getVideosByGalleryId/{galleryId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public List<VideoDetails> getVideosByGalleryId(@PathVariable Long galleryId){
		Gallery gallery = new Gallery();
		gallery.setGalleryid(galleryId);
		return videoService.findVideosByGallery(gallery);
	}
	
	@RequestMapping(value="/addVideo",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public boolean addVideo(@RequestBody VideoDetails videoDetails) {
         videoService.add(videoDetails);
         return true;
    }
	
	@RequestMapping(value="/updateVideo",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public boolean updateVideo(@RequestBody VideoDetails videoProperty) {        
        return videoService.update(videoProperty);
    }

    @RequestMapping(value="/deleteVideo",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})
    public boolean deleteVideo(@RequestBody String videoIdsStr) {
    	String[] videoIds = videoIdsStr.split(",");
    	List<Long> vidIds = new ArrayList<Long>();
    	for(String id:Arrays.asList(videoIds)){
    		vidIds.add(Long.valueOf(id));
    	}
        return videoService.delete(vidIds);
    }
}
