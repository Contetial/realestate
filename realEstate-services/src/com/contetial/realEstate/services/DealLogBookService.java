package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import com.contetial.realEstate.persistance.entity.DealLogBook;

public class DealLogBookService extends GenericService{
	
	public DealLogBookService(){
		super(DealLogBookService.class);
	}
	
	public DealLogBook findDealLogBookById(Long logId){
		DealLogBook logBook = (DealLogBook) findById(DealLogBook.class, logId);		
		return logBook;
	}
	
	@SuppressWarnings("unchecked")
	public List<DealLogBook> findDealLogBooks(Long dealId){
		DealLogBook logBook = new DealLogBook();
		logBook.setDealId(dealId);
		List<DealLogBook> dealLogBooks = (List<DealLogBook>) findEntity(logBook);
		
		return dealLogBooks;
	}
	
	public boolean addDealLogBook(DealLogBook logBook){		
		addEntity(logBook);
		return true;
	}
	
	public boolean updateDealLogBook(DealLogBook logBook){		
		DealLogBook origlogBook = (DealLogBook) workUnit.find(
				DealLogBook.class, logBook.getLogBookId().longValue());		
		updateEntity((DealLogBook) copyBean(logBook, origlogBook));
		return true;
	}
	
	public boolean deleteDealLogBook(List<String> logBookIds){		
		List<DealLogBook> logbooks = new ArrayList<DealLogBook>();
		for(String logBookId: logBookIds){
			DealLogBook logBook = new DealLogBook();
			logBook.setLogBookId(Long.valueOf(logBookId));
			logbooks.add(logBook);	
		}
		deleteEntity(logbooks);
		return true;
	}	    
}
