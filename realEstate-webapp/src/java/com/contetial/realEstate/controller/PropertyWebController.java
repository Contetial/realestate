package com.contetial.realEstate.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.services.PropertyService;
import com.contetial.realEstate.utility.exception.ServiceException;

@RestController
@RequestMapping("/propertyService")
public class PropertyWebController {

	//final static Logger logger = Logger.getLogger(PropertyWebController.class);
	
	@RequestMapping(value="/getProperty/{propId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	@ResponseBody
	public Property getPropertyById(
			@PathVariable Long propId){					
		return new PropertyService().findPropById(propId);
	}
	
	@RequestMapping(value="/getAllProperties", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	@ResponseBody
	public List<Property> getAllPropertys(){
		List<Property> properties = new ArrayList<Property>();
		try{
			properties = new PropertyService().findAllProperties();
		}catch(ServiceException e){
			System.out.println(e);
			//logger.error(e);
		}
		return properties;
	}
	
	@RequestMapping(value="/getPropertiesByUser/{appUserId}", 
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"},
			consumes = {"application/json"})
	@ResponseBody
	public List<Property> getPropertysByUser(			
			@PathVariable Long appUserId, @RequestBody Property property){
		List<Property> properties = new ArrayList<Property>();
		try{
			properties = new PropertyService().findProperties(appUserId,property);
		}catch(ServiceException e){
			System.out.println(e);
			//logger.error(e);
		}
		return properties;
	}
	
	@RequestMapping(value="/addProperty",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    @ResponseBody
    public boolean createProperty(@RequestBody Property property) {
        Property newProp= new PropertyService().addProperty(property);
        if(null!=newProp.getPropertyId()){
        	return true;
        }else{
        	return false;
        }
    }
	
	@RequestMapping(value="/updateProperty",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    @ResponseBody
    public boolean updateProperty(@RequestBody Property property) {        
		Property updatedProp= new PropertyService().updateProperty(property);
		if(null!=updatedProp.getPropertyId()){
        	return true;
        }else{
        	return false;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/deleteProperty",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})
    @ResponseBody
    public boolean deleteProperty(@RequestBody String propertyIdsStr) {
    	String[] propertyIds = propertyIdsStr.split(",");
    	List propIds = Arrays.asList(propertyIds);
        return new PropertyService().deleteProperty(propIds);
    }
}
