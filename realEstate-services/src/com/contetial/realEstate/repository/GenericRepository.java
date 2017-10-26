package com.contetial.realEstate.repository;

import java.util.List;

import org.apache.log4j.Logger;

import com.contetial.realEstate.persistance.entity.IEntity;
import com.contetial.realEstate.persistance.worker.WorkUnit;
import com.contetial.realEstate.utility.copy.CopyUtils;

public class GenericRepository {

	public WorkUnit workUnit;
	public final Logger logger;
		
	public GenericRepository(Class<? extends GenericRepository> clazz){
		workUnit = new WorkUnit();
		logger = Logger.getLogger(clazz);
	}
	
	protected IEntity findById(Class<? extends IEntity> clazz, Long id){
		return (IEntity) workUnit.find(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	protected List<? extends IEntity> findEntity(IEntity entity){
		return workUnit.findAll(entity);		
	}
	
	@SuppressWarnings("unchecked")
	protected List<? extends IEntity> findEntityByExactName(IEntity entity){
		return workUnit.findByExactName(entity);		
	}
		
	protected Long addEntity(IEntity entity){
		workUnit.beginTransaction();
		Long generatedId = (Long) workUnit.save(entity);
		workUnit.commitTransaction();
		return generatedId;
	}
	
	protected IEntity updateEntity(IEntity entity){
		workUnit.beginTransaction();
		IEntity returnEntity = (IEntity) workUnit.saveOrUpdate(entity);
		workUnit.commitTransaction();
		return returnEntity;
	}
	
	protected void deleteEntity(List<? extends IEntity> entities){
		workUnit.beginTransaction();
		for(IEntity entity: entities){
			workUnit.delete(entity);	
		}		
		workUnit.commitTransaction();
	}
	
	protected Object copyBean(Object sourceBean, Object destBean){
		CopyUtils.copyBean(sourceBean, destBean);
		return destBean;
	}
}
