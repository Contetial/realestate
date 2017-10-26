package com.contetial.realEstate.services;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.services.PropertyService;

public class PropertyServiceTest {

	@Test
	public void findPropByIdTest(){
		Property bean = new PropertyService().findPropById(1L);
		System.out.println(bean.getOnGoingDeals().toArray());
	}
	
	@Test
	public void findDealTest(){
		Property bean = new PropertyService().findPropById(1L);
		Set<Deal>deals = bean.getOnGoingDeals();
		Deal sampleDeal = new Deal();
		sampleDeal.setDealId(1L);
		Assert.assertTrue(deals.contains(sampleDeal));			
	}
}
