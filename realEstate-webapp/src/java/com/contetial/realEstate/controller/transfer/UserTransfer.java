package com.contetial.realEstate.controller.transfer;

import java.util.Map;


public class UserTransfer
{

	private final String name;
	
	private final Long userId;

	private final Map<String, Boolean> roles;


	public UserTransfer(String userName, Long userId, Map<String, Boolean> roles)
	{
		this.name = userName;
		this.userId = userId;
		this.roles = roles;
	}


	public String getName()
	{
		return this.name;
	}


	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}


	public Long getUserId() {
		return userId;
	}

}