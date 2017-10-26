package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import com.contetial.realEstate.persistance.entity.DealSchedule;
import com.contetial.realEstate.persistance.worker.WorkUnit;

public class DealScheduleService extends GenericService{
	
	public DealScheduleService(){
		super(DealScheduleService.class);
	}
	
	public DealSchedule findDealScheduleById(Long scheduleId){
		DealSchedule schedule = (DealSchedule) findById(DealSchedule.class, scheduleId);		
		return schedule;
	}
	
	@SuppressWarnings("unchecked")
	public List<DealSchedule> findDealSchedules(DealSchedule schedule){
		return (List<DealSchedule>) findEntity(schedule);
	}
	
	public boolean addDealSchedule(DealSchedule schedule){		
		addEntity(schedule);
		return true;
	}
	
	public boolean updateDealSchedule(DealSchedule schedule){		
		DealSchedule origSchedule = (DealSchedule) workUnit.find(
						DealSchedule.class, schedule.getDealScheduleId());		
		updateEntity((DealSchedule) copyBean(schedule, origSchedule));
		return true;
	}
	
	public boolean mergeDealSchedule(DealSchedule schedule){		
		updateEntity(schedule);
		return true;
	}
	
	public DealSchedule mergeDealScheduleWithParentTransaction(
			WorkUnit parentWorkUnit, DealSchedule schedule){		
		DealSchedule updatedSchedule = (DealSchedule) parentWorkUnit.saveOrUpdate(schedule);
		return updatedSchedule;
	}
	
	public boolean deleteDealSchedule(List<String> scheduleIdsStr){		
		List<DealSchedule> schedules = new ArrayList<DealSchedule>();
		for(String scheduleId: scheduleIdsStr){
			DealSchedule schedule = new DealSchedule();
			schedule.setDealScheduleId(Long.valueOf(scheduleId));
			schedules.add(schedule);	
		}
		deleteEntity(schedules);
		return true;
	}

}
