package com.contetial.realEstate.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.contetial.realEstate.controller.transfer.MessageTransfer;
import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.services.DealService;
import com.contetial.realEstate.utility.exception.ServiceException;

@RestController
@RequestMapping("/dealService")
public class DealWebController {
	@Autowired
	DealService dealService;
	
	@RequestMapping(value="/getDeal/{dealId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	@ResponseBody
	public Deal getDealById(
			@PathVariable Long dealId){					
		return dealService.findDealById(dealId);
	}
	
	@RequestMapping(value="/getDealsByUser/{appUserId}", 
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"},
			consumes = {"application/json"})
	@ResponseBody
	public List<Deal> getDealsByUser(			
			@PathVariable Long appUserId, @RequestBody Deal deal){
		List<Deal> deals = new ArrayList<Deal>();
		try{
			AppUser dealer = new AppUser();
			dealer.setUserId(appUserId);
			deal.setDealer(dealer);
			deals = dealService.findDeals(deal);
		}catch(ServiceException e){
			System.out.println(e);
			//logger.error(e);
		}
		return deals;
	}
	
	@RequestMapping(value="/addDeal",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<MessageTransfer> createDeal(@RequestBody Deal deal) {
		String responseMessage="Record added successfully";
		try {
			dealService.addDeal(deal);
		} catch (ServiceException e) {
			responseMessage = e.getMessage();
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(responseMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(responseMessage),HttpStatus.OK);        
    }
	
	@RequestMapping(value="/updateDeal",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<MessageTransfer> updateDeal(@RequestBody Deal deal) {
		String responseMessage="Record Updated successfully";
		try {
			dealService.updateDeal(deal);
		} catch (ServiceException e) {
			responseMessage = e.getMessage();
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(responseMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(responseMessage),HttpStatus.OK);        
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/deleteDeal",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})
    @ResponseBody
    public boolean deleteDeal(@RequestBody String dealIdsStr) {
    	String[] dealIds = dealIdsStr.split(",");
    	List dealIdz = Arrays.asList(dealIds);
        return dealService.deleteDeal(dealIdz);
    }
	
}
