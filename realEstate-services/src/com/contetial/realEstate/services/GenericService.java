package com.contetial.realEstate.services;

import com.contetial.realEstate.repository.GenericRepository;

public class GenericService extends GenericRepository{

	public GenericService(Class<? extends GenericService> clazz) {
		super(clazz);		
	}
}
