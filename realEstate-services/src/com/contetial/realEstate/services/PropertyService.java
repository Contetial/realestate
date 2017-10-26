package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.persistance.exception.DataAccessLayerException;
import com.contetial.realEstate.persistance.worker.WorkUnit;
import com.contetial.realEstate.utility.exception.ServiceException;

public class PropertyService  extends GenericService{

	public PropertyService(){
		super(PropertyService.class);
	}
	
	public Property findPropById(Long propId){
		Property prop = (Property) findById(Property.class, propId);		
		return prop;
	}
	
	public Property findPropByIdWithParentTransaction(WorkUnit parentWorkUnit,Long propId){
		Property prop = (Property) parentWorkUnit.find(Property.class, propId);		
		return prop;
	}
	
	@SuppressWarnings("unchecked")
	public List<Property> findProperties(
			Long appUserId, Property prop)throws ServiceException{
		try{
			List<Property> properties = (List<Property>) findEntity(prop);
			return properties;
		}catch(DataAccessLayerException e){
			throw new ServiceException("Error fetching properties: "+e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Property> findAllProperties()throws ServiceException{
		try{
			List<Property> properties = workUnit.executeCustomQuery(
					ALL_PROPERTY_QUERY, Property.class, null);
			return properties;
		}catch(DataAccessLayerException e){
			throw new ServiceException("Error fetching properties for gallery: "+e,e);
		}
	}
	
	public Property addProperty(Property prop){
		if(null==prop.getPropStatus()||prop.getPropStatus().isEmpty()){
			prop.setPropStatus("new");
		}
		prop.setPropertyId(addEntity(prop));
		return prop;		
	}
	
	public Property updateProperty(Property prop){		
		Property origProp = (Property) workUnit.find(
				Property.class, prop.getPropertyId().longValue());
		copyPropBean(prop, origProp);		
		return (Property)updateEntity(origProp);
	}
	
	public Property updatePropertyWithParentTransaction(WorkUnit parentWorkUnit,Property prop){		
		//Property origProp = (Property) parentWorkUnit.find(
				//Property.class, prop.getPropertyId().longValue());
		//copyPropBean(prop, origProp);
		return (Property)parentWorkUnit.saveOrUpdate(prop);
	}
	
	private void copyPropBean(Property prop, Property origProp) {
		if(null!=prop.getPropName()&&!prop.getPropName().isEmpty()){
			origProp.setPropName(prop.getPropName());
		}
		if(null!=prop.getPropAddress()&&!prop.getPropAddress().isEmpty()){
			origProp.setPropAddress(prop.getPropAddress());
		}
		if(null!=prop.getPropDetails()&&!prop.getPropDetails().isEmpty()){
			origProp.setPropDetails(prop.getPropDetails());
		}
		if(null!=prop.getPropStatus()&&!prop.getPropStatus().isEmpty()){
			String lstatus = prop.getPropStatus();
			origProp.setPropStatus(lstatus);
		}
		if(null!=prop.getOnGoingDeals()&&prop.getOnGoingDeals().size()>0){
			//check if this property already has deals
			if(null!=origProp.getOnGoingDeals()&&origProp.getOnGoingDeals().size()>0){
				//Loop through both the old and new deals and perform add or update accordingly
				for(Deal deal:prop.getOnGoingDeals()){
					//Making sure only unique deals are getting added to a property
					if(!origProp.getOnGoingDeals().contains(deal)){
						origProp.getOnGoingDeals().add(deal);
					}					
				}
			}else{//else there are no existing deals hence setting all.
				origProp.setOnGoingDeals(prop.getOnGoingDeals());
			}			
		}//else there are no new deals for this property
	}

	public boolean deleteProperty(List<String> propIds){
		List<Property> props = new ArrayList<Property>();
		for(String propId: propIds){
			Property prop =findPropById(Long.valueOf(propId));
			props.add(prop);	
		}
		deleteEntity(props);
		return true;
	}
	
	private static final String ALL_PROPERTY_QUERY= "select * from property where property_id in (select distinct(property_id) from gallery);";
}
