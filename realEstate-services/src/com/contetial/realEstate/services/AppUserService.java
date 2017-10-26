package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.utility.communication.MessageUtility;
import com.contetial.realEstate.utility.exception.ServiceException;


public class AppUserService extends GenericService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public AppUserService(){
		super(AppUserService.class);
	}
		
	public AppUser findAppUserById(Long userId){	
		AppUser user = (AppUser) findById(AppUser.class, userId);		
		return user;
	}
		
	@SuppressWarnings("unchecked")
	public List<AppUser> findUsers(Long userId, AppUser user){
		List<AppUser> users = (List<AppUser>) findEntity(user);		
		return users;		
	}
	
	@SuppressWarnings("unchecked")
	public AppUser findByName(String userName){
		AppUser user = new AppUser();
		user.setUserName(userName);
		List<AppUser> users = (List<AppUser>) findEntityByExactName(user);
		if(null!=users && !users.isEmpty()){
			return users.iterator().next();
		}else{
			return null;
		}
				
	}
	
	public boolean addAppUser(AppUser user) throws ServiceException{
		AppUser origAppUser = new AppUser();
		copyUser(user, origAppUser);
		addEntity(origAppUser);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ACTIVITY,"new");
		notifyUser(user,params);
		return true;
	}
	
	public boolean updateAppUser(AppUser user) throws ServiceException{		
		AppUser origAppUser = (AppUser) workUnit.find(
				AppUser.class, user.getUserId());
		copyUser(user, origAppUser);
		updateEntity(origAppUser);
		return true;
	}

	private void copyUser(AppUser user, AppUser origAppUser) throws ServiceException {
		if(!checkDuplicateUser(origAppUser.getUserId(),user.getUserName())){
			origAppUser.setUserName(user.getUserName());
		}else{
			throw new ServiceException("User already exists.");
		}
		if(!user.getPassword().isEmpty()
				&& !isPasswordMatch(user.getPassword(), origAppUser.getPassword())){
			String rawPassword = user.getPassword();
			origAppUser.setPassword(getEncodedPassword(rawPassword));			
		}
		origAppUser.setAddress(user.getAddress());
		origAppUser.setContactNo(user.getContactNo());
		origAppUser.setEmail(user.getEmail());
		origAppUser.setUserRole(user.getUserRole());
		if(null==origAppUser.getManager() 
				|| !user.getManagerName().equals(origAppUser.getManagerName())){
			AppUser newManager = findByName(user.getManagerName());
			if(null==newManager){
				throw new ServiceException("Manager does not exist");
			}else{
				origAppUser.setManager(newManager);
			}
		}
	}
	
	private boolean checkDuplicateUser(Long userId, String userName) {
		AppUser user = findByName(userName);
		if(null!=user && null!=userId && userId!=user.getUserId()){
			return true;
		}else{
			return false;
		}		
	}

	public boolean deleteAppUser(List<String> appUserIds){
		List<AppUser> appUsers = new ArrayList<AppUser>();
		for(String userId: appUserIds){
			AppUser user = findAppUserById(Long.valueOf(userId));
			appUsers.add(user);	
		}
		deleteEntity(appUsers);
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		AppUser user = this.findByName(userName);
		if (null == user) {
			throw new UsernameNotFoundException("The user with name " + userName + " was not found");
		}
		return user;
	}
	
	public String getEncodedPassword(String rawPassword){
		return passwordEncoder.encode(rawPassword);		
	}
	
	public boolean isPasswordMatch(String rawPassword, String encodedPassword){
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	public void notifySubordinatesForVisit(
			AppUser currentUser, String location, String visitDate)throws ServiceException{
		if(null==currentUser){
			throw new ServiceException("Manager is required");
		}
		if(null==location || location.isEmpty()){
			throw new ServiceException("Location is required");
		}		
		if(null==visitDate || visitDate.isEmpty()){
			throw new ServiceException("Date of Visit is required");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ACTIVITY,"visit");
		params.put(LOCATION,location);
		params.put(DATE,visitDate);
		try{
			List<AppUser> subordinates = getLocationBasedSubordinates(currentUser, location);		
			for(AppUser subordinate:subordinates){
				notifyUser(subordinate, params);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public List<AppUser> getLocationBasedSubordinates(AppUser currentUser, String location){
		Set<AppUser> users = getAllSubordinates(currentUser);
		List<AppUser> userSubset = new ArrayList<AppUser>();
		for(AppUser user:users){
			if(user.getAddress().toLowerCase()
					.contains(location.toLowerCase())){
				userSubset.add(user);
			}
		}
		return userSubset;
	}
	
	public List<Long> getAllSubordinateIds(AppUser currentUser) {
		List<Long> subordinateIds = new ArrayList<Long>(); 
		Set<AppUser> users = getAllSubordinates(currentUser);
		for(AppUser user:users){
			subordinateIds.add(user.getUserId());
		}
		return subordinateIds;
	}
	
	private Set<AppUser> getAllSubordinates(AppUser currentUser) {
		Set<AppUser> allSubordinates = new HashSet<AppUser>();
		allSubordinates.add(currentUser);
		if(currentUser.isManager()){
			for(AppUser user:currentUser.getSubordinates()){
				if(!currentUser.getUserId().equals(user.getUserId())){				
					if(user.isManager()){					
						allSubordinates.addAll(getAllSubordinates(user));
					}else{
						allSubordinates.add(user);
					}
				}				
			}
		}		
		return allSubordinates;
	}
	
	private void notifyUser(AppUser user, Map<String,Object> params){
		boolean doNotify = false;
		StringBuffer message= new StringBuffer();
		
		if("new".equals(String.valueOf(params.get(ACTIVITY)))){
			message.append("Welcome ")
			.append(user.getUserName())
			.append(". Your user name is '")
			.append(user.getUserName())
			.append("' and passcode is '")
			.append(user.getPassword())
			.append("'");
			logger.debug(message.toString());
			doNotify=true;
		}else if("visit".equals(String.valueOf(params.get(ACTIVITY)))){
			message.append("Hello ")
			.append(user.getUserName())
			.append(", Mr. Pravin Shinde will be visiting ")
			.append(String.valueOf(params.get(LOCATION)))
			.append(" on ")
			.append(String.valueOf(params.get(DATE)));
			logger.debug(message.toString());
			doNotify=true;
		}
		if(doNotify){
			try{
				MessageUtility.sendSMS(user.getContactNo().toString(), message.toString());
			}catch(Exception e){
				logger.error("Error for user: "+user.getUserName());
				logger.error(e.getMessage());
			}
		}
	}
	
	private static final String ACTIVITY = "activity";
	private static final String LOCATION = "location";
	private static final String DATE = "date";
}
