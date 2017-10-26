package com.contetial.realEstate.services;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StringEncodingDecodingTest {

	@Test
	public void newPasswordEncodingTechnique(){
		String password = "pa$$w0rd";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		System.out.println(hashedPassword);
	}	
	
	@Test
	public void newPasswordDecodingTechnique(){
		String password = "password";
		String encodedPassword = "$2a$11$gAfzioVIfhHltoz6hVBAt.Jo4y.1gEk.u08ZuPd7ANnzD8m/JBJSu";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean ismatch = passwordEncoder.matches(password, encodedPassword);
		System.out.println(ismatch);
	}
}
