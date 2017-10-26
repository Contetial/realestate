package com.contetial.realEstate.services;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.StringUtils;

import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.services.AppUserService;
import com.contetial.realEstate.utility.exception.ServiceException;

public class AppUserServiceTest {
	AppUserService appUserSer;
	AppUser user;
	
	
	@Before
	public void init(){
		appUserSer = new AppUserService();
		user = new AppUser();
	}
	
	@Ignore
	public void createAppUserTest(){
		user.setUserName("User9");
		user.setPassword("password");
		user.setAddress("Nagpur 9");		
		try {
			appUserSer.addAppUser(user);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("User created successfully");
		//assertEquals()
	}
	
	@Ignore
	public void updateAppUserTest(){
		//user.setUserId(1);
		user.setUserName("User8");
		user.setAddress("Nagpur 10");		
		try {
			appUserSer.updateAppUser(user);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("User updated successfully");
		//assertEquals()
	}
	
	@Test
	public void testGetAllSubordinateIds(){
		user = appUserSer.findAppUserById(2L);
		System.out.println(StringUtils.arrayToCommaDelimitedString(appUserSer.getAllSubordinateIds(user).toArray()));
	}

}
