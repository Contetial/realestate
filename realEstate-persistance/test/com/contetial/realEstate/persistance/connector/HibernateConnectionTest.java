package com.contetial.realEstate.persistance.connector;

import org.junit.Test;

public class HibernateConnectionTest {

	
	@Test
	public void openSessionTest(){
		HibernateConnection.getSession();
	}
}
