package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.persistance.entity.Customer;
import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.persistance.entity.DealSchedule;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.utility.communication.MessageUtility;
import com.contetial.realEstate.utility.exception.ServiceException;

public class DealService extends GenericService {

	//@Autowired
	//PropertyService propertyService;
	
	public DealService(){
		super(DealService.class);
	}
	
	public Deal findDealById(Long dealId){
		Deal deal = (Deal) findById(Deal.class, dealId);		
		return deal;
	}
	
	@SuppressWarnings("unchecked")
	public List<Deal> findDeals(Deal deal) throws ServiceException{
		List<Deal> deals = workUnit.executeCustomQuery(
							getDealsQuery(deal), Deal.class, null);		
		return deals;
	}
	
	private String getDealsQuery(Deal deal) {
		StringBuffer dealsQuery = new StringBuffer();
		
		AppUserService userService = new AppUserService();		
		AppUser currentUser = userService.findAppUserById(deal.getDealer().getUserId());
		String currentUserRole = currentUser.getUserRole();
		
		dealsQuery.append("select * from deal where ");
		if(null!=deal){
			if(null!=deal.getCust() && null!=deal.getCust().getCustomerId()){
				dealsQuery.append("customer_id="+deal.getCust().getCustomerId()+" and ");
			}if(null!=deal.getProperty()&& null!=deal.getProperty().getPropertyId()){
				dealsQuery.append("property_id="+deal.getProperty().getPropertyId()+" and ");
			}
		}
		if("agent".equals(currentUserRole)){
			dealsQuery.append("user_id = "+ currentUser.getUserId());
		}else if("manager".equals(currentUserRole)){
			List<Long> subordinateIds = userService.getAllSubordinateIds(currentUser);			
			dealsQuery.append("user_id in (");
			dealsQuery.append(StringUtils.arrayToCommaDelimitedString(subordinateIds.toArray()));
			dealsQuery.append(")");
		}else if("admin".equals(currentUserRole)){
			dealsQuery.append("deal_status is not null");
		}else{
			dealsQuery.append("deal_status is null");
		}
		dealsQuery.append(";");
		return dealsQuery.toString();
	}

	public int addDeal(Deal deal) throws ServiceException{
		if(validSchedule(deal)){
			try{
			workUnit.beginTransaction();
			updateCustomerStatus(deal);
			Deal newDeal = addDealInfo(deal);
			updatePropertyStatus(newDeal);			
			updateDealSchedule(newDeal);
			workUnit.commitTransaction();
			notifyUser(newDeal,"new",
					deal.getSchedule(),deal.getProperty());
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new ServiceException("Problem in creating deal");
			}
			return 0;
		}else{
			throw new ServiceException("Meeting already exists for the selected schedule");
		}
	}
	
	public int updateDeal(Deal deal) throws ServiceException{
		if(validSchedule(deal)){
			//Updating customer info is not required at this point.
			try{
				updatePropertyStatus(deal);
				updateDealSchedule(deal);
				Deal origDeal = (Deal) workUnit.find(
						Deal.class, deal.getDealId().longValue());	
				updateEntity((Deal) copyBean(deal, origDeal));
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new ServiceException("Problem in updating deal");
			}
			return 0;
		}else{
			throw new ServiceException("Meeting already exists for the selected schedule");
		}		
	}
	
	public boolean deleteDeal(List<Deal> dealBeans){
		List<Deal> deals = new ArrayList<Deal>();
		for(Deal dealBean: dealBeans){
			deals.add((Deal) copyBean(dealBean, new Deal()));	
		}
		deleteEntity(deals);
		return true;
	}

	private void updateCustomerStatus(Deal deal) {
		Customer cust = deal.getCust();
		cust.setStatus("engaged");
		deal.setCust(new CustomerService().updateCustomerWithParentTransaction(workUnit,cust));
	}
	
	private void updatePropertyStatus(Deal deal) {
		PropertyService propertyService = new PropertyService();
		//Find with same transaction
		Property prop = propertyService.findPropByIdWithParentTransaction(
								workUnit, deal.getProperty().getPropertyId());
		prop.setPropStatus("in-deal");
		//prop.getOnGoingDeals().add(deal);
		propertyService.updatePropertyWithParentTransaction(workUnit, prop);
		deal.setProperty(prop);// This step is required specially for Many to Many relations.
	}

	private void updateDealSchedule(Deal deal) {
		DealSchedule schedule=deal.getSchedule();
		schedule.setDeal(deal);
		new DealScheduleService()
			.mergeDealScheduleWithParentTransaction(workUnit, schedule);		
	}
	
	private Deal addDealInfo(Deal deal) {
		if(null==deal.getDealStatus()||deal.getDealStatus().isEmpty()){
			deal.setDealStatus("new");
		}
		Long id =(Long)workUnit.save(deal);
		deal.setDealId(id);
		return deal;
	}
	
	private boolean validSchedule(Deal deal) {
		boolean isValid = true;
		
		return isValid;
	}
	
	private void notifyUser(Deal deal, String activity, DealSchedule schedule, Property prop){
		if("new".equals(activity)){
			StringBuffer message= new StringBuffer();
			message.append("Hello ")
			.append(deal.getCust().getCustomerName())
			.append(", Greetings from OSBPL. Your ")
			.append(schedule.getScheduleType())
			.append(" appointment has been scheduled with:")
			.append(deal.getDealer().getUserName())
			.append(". M: ")
			.append(deal.getDealer().getContactNo())
			.append("\n")
			.append("Date:")
			.append(schedule.getDealDateStr())
			.append(".\n")
			.append("Time :")
			.append(schedule.getDealFromTimeStr())
			.append(" to ")
			.append(schedule.getDealTillTimeStr())
			.append("\n Wish you a pleasent day!");
			MessageUtility.sendSMS(deal.getCust().getContactNo().toString(), message.toString());
		}
	}
}
