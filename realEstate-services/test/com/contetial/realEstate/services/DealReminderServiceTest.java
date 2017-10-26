package com.contetial.realEstate.services;

import org.junit.Test;

import com.contetial.realEstate.persistance.entity.DealSchedule;
import com.contetial.realEstate.utility.exception.ServiceException;

public class DealReminderServiceTest {

	DealReminderService service = new DealReminderService();
	
	@Test
	public void testGetSchedules() throws ServiceException{
		//System.out.println("##### Time right now ####### "+new Date(Calendar.getInstance().getTimeInMillis()));
		for(DealSchedule schedule:service.findTodaysDealShchedules()){			
			System.out.println(">>>>>>>>>>>> In side <<<<<<<<<< "+schedule.getDealDate());
		}
	}
}
