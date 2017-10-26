package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.persistance.entity.Customer;
import com.contetial.realEstate.persistance.worker.WorkUnit;
import com.contetial.realEstate.utility.exception.ServiceException;

public class CustomerService extends GenericService{
	
	public CustomerService(){
		super(CustomerService.class);
	}
	
	public Customer findCustById(Long custId){
		Customer cust = (Customer) findById(Customer.class, custId);		
		return cust;
	}
	
	@SuppressWarnings("unchecked")
	public List<Customer> findCustomers(Long appUserId, Customer cust){
		List<Customer> customers = workUnit.executeCustomQuery(
				getCustomerQuery(appUserId, cust), Customer.class, null);		
		
		return customers;
	}
	
	
	
	
	public boolean addCustomer(Customer cust) throws ServiceException{
		cust.setStatus("new");		
		fetchReferer(cust);		
		addEntity(cust);
		return true;
	}
	
	public boolean updateCustomer(Customer cust) throws ServiceException{		
		Customer origCust = (Customer) workUnit.find(
				Customer.class, cust.getCustomerId().longValue());
		fetchReferer(cust);
		copyCustBean(cust, origCust);		
		updateEntity(origCust);
		return true;
	}

	private void fetchReferer(Customer cust) throws ServiceException {
		if(null!= cust.getReferedBy()){
			AppUserService userService = new AppUserService();
			AppUser origUser = null;
			if(null!=cust.getReferedBy().getUserId()){
				origUser = userService.findAppUserById(
						cust.getReferedBy().getUserId());
			}else if(null!=cust.getReferedBy().getUserName()){
				origUser = userService.findByName(
						cust.getReferedBy().getUserName());
			}else{
				throw new ServiceException("Refered by app user (name or ID) requied");
			}
			cust.setReferedBy(origUser);
		}else{
			throw new ServiceException("Refered by app user requied");
		}
	}
	
	public Customer updateCustomerWithParentTransaction(
			WorkUnit parentWorkUnit, Customer cust){		
		Customer origCust = (Customer) parentWorkUnit.find(
				Customer.class, cust.getCustomerId().longValue());		
		copyCustBean(cust, origCust);		
		return (Customer)parentWorkUnit.saveOrUpdate(origCust);		 
	}

	private void copyCustBean(Customer cust, Customer origCust) {
		if(null!=cust.getCustomerName()&&!cust.getCustomerName().isEmpty()){
			origCust.setCustomerName(cust.getCustomerName());
		}
		if(null!=cust.getAddress()&&!cust.getAddress().isEmpty()){
			origCust.setAddress(cust.getAddress());
		}
		if(null!=cust.getContactNo()&&cust.getContactNo()>0){
			origCust.setContactNo(cust.getContactNo());
		}
		if(null!=cust.getEmail()&&!cust.getEmail().isEmpty()){
			origCust.setEmail(cust.getEmail());
		}
		if(null!=cust.getStatus()&&!cust.getStatus().isEmpty()){
			origCust.setStatus(cust.getStatus());
		}
		if(null!=cust.getDob()){
			origCust.setDob(cust.getDob());
		}
		if(null!=cust.getDoa()){
			origCust.setDoa(cust.getDoa());
		}
		if(null!=cust.getReferedBy()){
			origCust.setReferedBy(cust.getReferedBy());
		}
	}
	
	public boolean deleteCustomer(List<String> custIds){		
		List<Customer> custs = new ArrayList<Customer>();
		for(String custId: custIds){
			Customer cust = findCustById(Long.valueOf(custId));			
			custs.add(cust);	
		}
		deleteEntity(custs);
		return true;
	}
	
	private String getCustomerQuery(Long userId, Customer cust){
		AppUserService userService = new AppUserService();
		
		StringBuffer sb = new StringBuffer();
		AppUser currentUser = userService.findAppUserById(userId);
		String currentUserRole = currentUser.getUserRole();
		sb.append("select * from customer where ");
		if(null!=cust){
			if(null!=cust.getCustomerId()){
				sb.append("customer_id="+cust.getCustomerId()+" and ");
			}if(null!=cust.getCustomerName()&&!cust.getCustomerName().isEmpty()){
				sb.append("customer_name like '%"+cust.getCustomerName()+"%' and ");
			}if(null!=cust.getAddress()&&!cust.getAddress().isEmpty()){
				sb.append("address like '%"+cust.getAddress()+"%' and ");
			}if(null!=cust.getContactNo()){
				sb.append("contactNo="+cust.getContactNo()+" and ");
			}
		}
		if("agent".equals(currentUserRole)){
			sb.append("refered_by = "+ currentUser.getUserId());
		}else if("manager".equals(currentUserRole)){
			List<Long> subordinateIds = userService.getAllSubordinateIds(currentUser);			
			sb.append("refered_by in (");
			sb.append(StringUtils.arrayToCommaDelimitedString(subordinateIds.toArray()));
			sb.append(")");
		}else if("admin".equals(currentUserRole)){
			sb.append("status is not null");
		}else{
			sb.append("status is null");
		}
		sb.append(";");
		return sb.toString();
	}
}
