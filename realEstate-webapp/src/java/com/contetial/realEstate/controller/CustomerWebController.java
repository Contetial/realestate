package com.contetial.realEstate.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.contetial.realEstate.controller.transfer.MessageTransfer;
import com.contetial.realEstate.persistance.entity.Customer;
import com.contetial.realEstate.services.CustomerService;
import com.contetial.realEstate.utility.exception.ServiceException;

@RestController
@RequestMapping("/customerService")
public class CustomerWebController {

	@RequestMapping(value="/getCustomer/{custId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	@ResponseBody
	public Customer getCustomerById(
			@PathVariable Long custId){					
		return new CustomerService().findCustById(custId);
	}
	
	@RequestMapping(value="/getCustomersByUser/{appUserId}", 
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"},
			consumes = {"application/json"})
	@ResponseBody
	public List<Customer> getCustomersByUser(			
			@PathVariable Long appUserId, @RequestBody Customer customerBean){
		return new CustomerService().findCustomers(appUserId,customerBean);
	}
	
	@RequestMapping(value="/addCustomer",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public ResponseEntity<MessageTransfer> createCustomer(@RequestBody Customer customer) {
		String responseMessage="Record added successfully";
		try {
			new CustomerService().addCustomer(customer);
		} catch (ServiceException e) {
			responseMessage = e.getMessage();
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(responseMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(responseMessage),HttpStatus.OK);        
    }
	
	@RequestMapping(value="/updateCustomer",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public ResponseEntity<MessageTransfer> updateCustomer(
    		@RequestBody Customer customerBean) {
		String responseMessage="Record added successfully";
		try {
			new CustomerService().updateCustomer(customerBean);
		} catch (ServiceException e) {
			responseMessage = e.getMessage();
			return new ResponseEntity<MessageTransfer>(
					new MessageTransfer(responseMessage),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<MessageTransfer>(
				new MessageTransfer(responseMessage),HttpStatus.OK);       
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/deleteCustomer",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})
    @ResponseBody
    public boolean deleteCustomer(@RequestBody String customerIdsStr) {
    	String[] customerIds = customerIdsStr.split(",");
    	List custIds = Arrays.asList(customerIds);
        return new CustomerService().deleteCustomer(custIds);
    }
}
