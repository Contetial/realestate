package com.contetial.realEstate.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contetial.realEstate.persistance.entity.DealLogBook;
import com.contetial.realEstate.services.DealLogBookService;

@RestController
@RequestMapping("/logBookService")
public class DealLogBookWebController {

	@RequestMapping(value="/getLogBookEntry/{logId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public DealLogBook getLogBookEntry(
			@PathVariable Long logBookId){					
		return new DealLogBookService().findDealLogBookById(logBookId);
	}
	
	@RequestMapping(value="/getLogsByDeal/{dealId}", 
			method = RequestMethod.GET, 
			headers = "Accept=application/json", 
			produces = {"application/json"})
	public List<DealLogBook> getLogsByDeal(			
			@PathVariable Long dealId){
		return new DealLogBookService().findDealLogBooks(dealId);
	}
	
	@RequestMapping(value="/addLogBookEntry",
			method = RequestMethod.POST, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public boolean addLogBookEntry(@RequestBody DealLogBook logBook) {
        return new DealLogBookService().addDealLogBook(logBook);
    }
	
	@RequestMapping(value="/updateLogBookEntry",
			method = RequestMethod.PUT, 
			headers = "Accept=application/json", 
			produces = {"application/json"}, 
			consumes = {"application/json"})
    public boolean updateLogBookEntry(
    		@RequestBody DealLogBook logBookBean) {        
        return new DealLogBookService().updateDealLogBook(logBookBean);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/deleteLogBookEntries",
			method = RequestMethod.DELETE, 
    		headers = "Accept=application/json", 
    		produces = {"application/json"},
    		consumes = {"application/json"})
    public boolean deleteLogBookEntries(@RequestBody String logBookIdsStr) {
    	String[] logBookIds = logBookIdsStr.split(",");
    	List logIds = Arrays.asList(logBookIds);
        return new DealLogBookService().deleteDealLogBook(logIds);
    }
}
