package com.contetial.realEstate.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.contetial.realEstate.persistance.entity.DealSchedule;
import com.contetial.realEstate.utility.communication.MessageUtility;
import com.contetial.realEstate.utility.exception.ServiceException;

public class DealReminderService extends GenericService{

	public DealReminderService() {
		super(DealReminderService.class);		
	}
	
	public void remindCustomers() throws ServiceException{
		try{
			List<DealSchedule> todaysSchedules = findTodaysDealShchedules();			
			for(DealSchedule schedule:todaysSchedules){
				notifyCustomer(schedule);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DealSchedule> findTodaysDealShchedules() throws ServiceException{
		List<DealSchedule> schedules = workUnit.executeCustomQuery(
				SCHEDULES_QUERY, DealSchedule.class, null);
		return schedules;
	}
	
	private void notifyCustomer(DealSchedule schedule){

		StringBuffer message= new StringBuffer();
		message.append("Greetings ")
		.append(schedule.getDeal().getCust().getCustomerName())
		.append(",\n")
		.append("This is a reminder for your meeting today with OSBPL representitive ")
		.append(schedule.getDeal().getDealer().getUserName())
		.append(" at ")
		.append(schedule.getDealFromTimeStr())
		.append(" for ")
		.append(schedule.getScheduleType())
		.append(".\nHave a nice day!\n");
		logger.debug(message.toString());

		try{
			MessageUtility.sendSMS(schedule.getDeal().getCust().getContactNo().toString(), message.toString());
		}catch(Exception e){
			logger.error("Error while wishing for cust: "+schedule.getDeal().getCust().getCustomerName());
			logger.error(e.getMessage());
		}	
	}
	
	private static final String SCHEDULES_QUERY = "select * from deal_schedule where deal_date=STR_TO_DATE('"
											+new Date(Calendar.getInstance().getTimeInMillis())+"','%Y-%m-%d')";
}
