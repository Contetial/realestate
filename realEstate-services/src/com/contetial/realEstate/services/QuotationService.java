package com.contetial.realEstate.services;

import javax.mail.MessagingException;

import com.contetial.realEstate.persistance.entity.Customer;
import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.utility.communication.MailUtility;
import com.contetial.realEstate.utility.exception.ServiceException;

public class QuotationService extends GenericService{

	Property property;
	Deal deal;
	Customer customer;
	String specialNotes;
	
	public QuotationService(Deal deal, String specialNotes){
		super(QuotationService.class);
		this.property=deal.getProperty();
		this.deal=deal;
		this.customer=deal.getCust();
		this.specialNotes=specialNotes;
	}
	
	private String createSubject(){
		return "Quotation for property";
	}
	
	private String getReciever(){
		return customer.getEmail();
	}
	
	private String getSender(){
		return "osbpl@gmail.com";
	}
	
	private String createBody(){
		StringBuffer bodyContent = new StringBuffer();
		bodyContent.append("Greetings "+getReciever()+",\n\n")
		.append("This is in regards with our communication about our Property. Following are the details:\n")
		.append(property.getPropName()+"\n")
		.append(property.getPropAddress()+"\n")
		.append(property.getPropDetails()+"\n")
		.append("\n\n")
		.append(specialNotes+"\n")
		.append("\n\n\n")
		.append("Thank you for your intrest! have a great day ahead.\n")
		.append("\n\nOSBPL Team");
		logger.debug(bodyContent.toString());
		return bodyContent.toString();
	}
	
	public void sendEmail() throws ServiceException{
		MailUtility mail = new MailUtility(
				getSender(),getReciever(),createSubject(),createBody());
		try {
			mail.send();
		} catch (MessagingException e) {
			throw new ServiceException(e);
		}
	}
}
