package com.contetial.realEstate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.contetial.realEstate.controller.transfer.MessageTransfer;
import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.services.DealReminderService;
import com.contetial.realEstate.services.DealService;
import com.contetial.realEstate.services.QuotationService;
import com.contetial.realEstate.services.WishingService;
import com.contetial.realEstate.utility.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@RestController
@RequestMapping("/commService")
public class SpecialCommWebController {
	
	@Autowired
	DealService dealService;
	
	@RequestMapping(value="/sendQuote", 
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"},
			consumes = {"application/json"})
	@ResponseBody
	public ResponseEntity<MessageTransfer> getCustomersByUser(			
			@RequestBody QuoteBean bean){
		String returnMessage="quotation sent successfully";
		try{
			Deal deal = dealService.findDealById(bean.getDealId());
			QuotationService qService = new QuotationService(deal, bean.getSpecialNotes());
			qService.sendEmail();
		}catch(ServiceException se){
			se.printStackTrace();
			returnMessage="error while sending email";
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(returnMessage),HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(returnMessage),HttpStatus.OK);
	}
	
	@RequestMapping(value="/wishCustomers", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	@ResponseBody
	public ResponseEntity<MessageTransfer> wishCustomers(){
		String returnMessage="wishing service ran successfully";
		try{
			
			new WishingService().wishCustomers();
		}catch(ServiceException se){
			se.printStackTrace();
			returnMessage="error while sending wish sms";
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(returnMessage),HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(returnMessage),HttpStatus.OK);
	}
	
	@RequestMapping(value="/remindCustomers", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	@ResponseBody
	public ResponseEntity<MessageTransfer> remindCustomers(){
		String returnMessage="reminder service ran successfully";
		try{
			
			new DealReminderService().remindCustomers();
		}catch(ServiceException se){
			se.printStackTrace();
			returnMessage="error while sending reminder sms";
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(returnMessage),HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(returnMessage),HttpStatus.OK);
	}

}

@JsonAutoDetect
class QuoteBean{
	private Long dealId;
	private String specialNotes;
	
	public Long getDealId() {
		return dealId;
	}
	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}
	public String getSpecialNotes() {
		return specialNotes;
	}
	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes;
	}
}
