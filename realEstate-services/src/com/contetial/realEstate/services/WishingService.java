package com.contetial.realEstate.services;

import java.util.List;

import com.contetial.realEstate.persistance.entity.Customer;
import com.contetial.realEstate.utility.communication.MessageUtility;
import com.contetial.realEstate.utility.exception.ServiceException;

public class WishingService extends GenericService{

	public WishingService(){
		super(WishingService.class);
	}
	
	public void wishCustomers() throws ServiceException{
		try{
			List<Customer> birthdayCustomers =  findCustomersToWish(BIRTHDAY);
			if(!birthdayCustomers.isEmpty()){
				callNotify(birthdayCustomers, BIRTHDAY);
			}
			List<Customer> anniversaryCustomers =  findCustomersToWish(ANNIVERSARY);
			if(!anniversaryCustomers.isEmpty()){
				callNotify(anniversaryCustomers, ANNIVERSARY);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	private void callNotify(List<Customer> customers, String wishFor){
		for(Customer cust:customers){
			notifyCustomer(cust, wishFor);
		}
	}
	
	/**
	 * @param wishFor, acceptable values are 'birthday','anniversary'
	 * @return List of Customers matching the criteria
	 */
	@SuppressWarnings("unchecked")
	private List<Customer> findCustomersToWish(String wishFor){
		List<Customer> customers = workUnit.executeCustomQuery(
				getCustomerToWishQuery(wishFor), Customer.class, null);		
		
		return customers;
	}
	
	private String getCustomerToWishQuery(String wishFor){
		
		StringBuffer sb = new StringBuffer();
		
		if(BIRTHDAY.equalsIgnoreCase(wishFor)){
			sb.append("select * from customer ");
			sb.append("where EXTRACT(DAY FROM DOB) = EXTRACT(DAY FROM CURDATE()) ");
			sb.append("AND EXTRACT(MONTH FROM DOB) = EXTRACT(MONTH FROM CURDATE());");
		}else if(ANNIVERSARY.equalsIgnoreCase(wishFor)){
			sb.append("select * from customer ");
			sb.append("where EXTRACT(DAY FROM DOA) = EXTRACT(DAY FROM CURDATE()) ");
			sb.append("AND EXTRACT(MONTH FROM DOA) = EXTRACT(MONTH FROM CURDATE());");
		}
		
		return sb.toString();
	}
	
	private void notifyCustomer(Customer cust,  String wishFor){
		boolean doNotify = false;
		StringBuffer message= new StringBuffer();
		message.append("Greetings ")
		.append(cust.getCustomerName())
		.append(",\n")
		.append("Wishing you A very Happy ");
		if(BIRTHDAY.equals(wishFor)){
			message.append("Birthday");			
			doNotify=true;
		}else if(ANNIVERSARY.equals(wishFor)){
			message.append("Anniversary");			
			doNotify=true;
		}
		message.append(",\n")
		.append("from the entire OSBPL family.\n")
		.append("Have a great day ahead!\n");
		logger.debug(message.toString());
		if(doNotify){
			try{
				MessageUtility.sendSMS(cust.getContactNo().toString(), message.toString());
			}catch(Exception e){
				logger.error("Error while wishing for cust: "+cust.getCustomerName());
				logger.error(e.getMessage());
			}
		}
	}
	
	private static final String ANNIVERSARY = "anniversary";
	private static final String BIRTHDAY = "birthday";
}
