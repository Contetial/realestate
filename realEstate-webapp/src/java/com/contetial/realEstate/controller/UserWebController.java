package com.contetial.realEstate.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contetial.realEstate.controller.authorization.TokenUtils;
import com.contetial.realEstate.controller.transfer.MessageTransfer;
import com.contetial.realEstate.controller.transfer.TokenTransfer;
import com.contetial.realEstate.controller.transfer.UserTransfer;
import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.services.AppUserService;
import com.contetial.realEstate.utility.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@RestController
@RequestMapping("/userService")
public class UserWebController {
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	private AppUserService appUserService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
		
	
	@RequestMapping(value="/getUser/{userId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})	 
	public AppUser getAppUserById(
			@PathVariable Long userId){					
		return appUserService.findAppUserById(userId);
	}
	
	@RequestMapping(value="/getUsers", 
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"},
			consumes = {"application/json"})	 
	public List<AppUser> getAppUsers(			
			@RequestBody AppUser appUserBean){
		return appUserService.findUsers(null,appUserBean);
	}
	
	@RequestMapping(value="/addAppUser",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})     
    public ResponseEntity<MessageTransfer> createAppUser(@RequestBody AppUser appUser){
		String responseMessage="Record added successfully";
		try {
			appUserService.addAppUser(appUser);
		} catch (ServiceException e) {
			responseMessage = e.getMessage();
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(responseMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(responseMessage),HttpStatus.OK);
    }
	
	@RequestMapping(value="/updateAppUser",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})     
    public ResponseEntity<MessageTransfer> updateAppUser(@RequestBody AppUser appUserBean){        
		String responseMessage="Record updated successfully";
		try {
			appUserService.updateAppUser(appUserBean);
		} catch (ServiceException e) {
			responseMessage = e.getMessage();
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(responseMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(responseMessage),HttpStatus.OK);
    }
	
	@RequestMapping(value="/notifyAppUser",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})     
    public ResponseEntity<MessageTransfer> notifyAppUser(
    		@RequestBody NotifyUser notifyUser){
		String responseMessage="Users notified successfully";
		try {
			AppUser user = appUserService.findAppUserById(notifyUser.getUserId());
			appUserService.notifySubordinatesForVisit(
					user,notifyUser.getLocation(),notifyUser.getVisitDate());
		} catch (ServiceException e) {
			responseMessage = e.getMessage();
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(responseMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(responseMessage),HttpStatus.OK);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/deleteAppUser",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})     
    public boolean deleteAppUser(@RequestBody String appUserIdsStr) {
    	String[] appUserIds = appUserIdsStr.split(",");
    	List userIds = Arrays.asList(appUserIds);
        return appUserService.deleteAppUser(userIds);
    }
    
	/**
	 * Retrieves the currently logged in user.
	 * 
	 * @return A transfer containing the username and the roles.
	 */
	@RequestMapping(value="/getUser", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public ResponseEntity<UserTransfer> getUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return new ResponseEntity<UserTransfer>(HttpStatus.UNAUTHORIZED);
		}
		UserDetails userDetails = (UserDetails) principal;
		AppUser appUser = (AppUser) this.userService.loadUserByUsername(userDetails.getUsername());
		
		return new ResponseEntity<UserTransfer>(
				new UserTransfer(appUser.getUsername(),
								appUser.getUserId(),
								this.createRoleMap(appUser)),HttpStatus.OK);
	}
	
    /**
	 * Authenticates a user and creates an authentication token.
	 * 
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return A transfer containing the authentication token.
	 */
    @RequestMapping(value="/authenticate", 
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"},
			consumes = {"application/json"})
	public TokenTransfer authenticate(@RequestBody AppUser user)
	{
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
		UserDetails userDetails = this.userService.loadUserByUsername(user.getUserName());

		return new TokenTransfer(TokenUtils.createToken(userDetails));
	}


	private Map<String, Boolean> createRoleMap(UserDetails userDetails)
	{
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

}


@JsonAutoDetect
class NotifyUser{
	private Long userId;
	private String location;
	private String visitDate;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
}