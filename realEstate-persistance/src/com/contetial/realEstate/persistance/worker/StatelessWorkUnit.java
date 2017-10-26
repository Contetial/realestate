package com.contetial.realEstate.persistance.worker;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import com.contetial.realEstate.persistance.connector.HibernateConnection;
import com.contetial.realEstate.persistance.connector.HibernateStatelessConnection;
import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.persistance.entity.DealLogBook;
import com.contetial.realEstate.persistance.entity.DealSchedule;
import com.contetial.realEstate.persistance.entity.Gallery;
import com.contetial.realEstate.persistance.entity.ImageDetails;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.persistance.entity.VideoDetails;
import com.contetial.realEstate.persistance.exception.DataAccessLayerException;

public class StatelessWorkUnit {

	private StatelessSession session;
	private Transaction tx;
	private StatelessCriteriaHelper crHelper;

	public StatelessWorkUnit() {
		session = HibernateStatelessConnection.getSession();
		crHelper = new StatelessCriteriaHelper();
	}

	public Object saveOrUpdate(Object obj) {
		Object entity=null;
		try {
			//session.setCacheMode(CacheMode.IGNORE);
			session.update(obj);
		} catch (HibernateException e) {
			handleException(e);
		}
		return entity; 
	}

	public Object save(Object obj) {
		Object generatedRowId = null;
		try {
			//session.setCacheMode(CacheMode.IGNORE);
			generatedRowId = session.insert(obj);
		} catch (HibernateException e) {
			handleException(e);
		} 
		return generatedRowId;
	}

	public void delete(Object obj) {
		try {
			//session.setCacheMode(CacheMode.IGNORE);
			session.delete(obj);
		} catch (HibernateException e) {
			handleException(e);
		} 
	}

	/**
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object find(Class<?> clazz, Long id) {
		Object obj = null;
		try {
			//session.setCacheMode(CacheMode.IGNORE);
			obj = session.get(clazz, id);            
		} catch (HibernateException e) {
			handleException(e);
		} 
		session.refresh(obj);
		return obj;
	}

	@SuppressWarnings("rawtypes")
	public List findAll(Object obj) {
		List objects = null;
		try {
			//session.setCacheMode(CacheMode.IGNORE);
			//session.evict(obj);
			if(obj instanceof AppUser){
				AppUser app=(AppUser)obj;
				Criteria cr = crHelper.getAppUserCriteria(session, app);
				objects = cr.list();				
			}else if(obj instanceof Property){
				Property prop = (Property)obj;
				Criteria cr = crHelper.getPropertyCriteria(session, prop);
				objects = cr.list();
			}else if(obj instanceof Deal){
				Deal deal = (Deal)obj;
				Criteria cr = crHelper.getDealCriteria(session, deal);
				objects = cr.list();
			}else if(obj instanceof DealLogBook){
				DealLogBook logBook = (DealLogBook)obj;
				Criteria cr = crHelper.getDealLogBookCriteria(session, logBook);
				objects = cr.list();
			}else if(obj instanceof DealSchedule){
				DealSchedule schedule = (DealSchedule)obj;
				Criteria cr = crHelper.getDealScheduleCriteria(session, schedule);
				objects = cr.list();
			}else if(obj instanceof Gallery){
				Gallery gal = (Gallery)obj;
				Criteria cr = crHelper.getGalleryCriteria(session, gal);
				objects = cr.list();
			}else if(obj instanceof ImageDetails){
				ImageDetails iDet = (ImageDetails)obj;
				Criteria cr = crHelper.getImageDetailsCriteria(session, iDet);
				objects = cr.list();
			}else if(obj instanceof VideoDetails){
				VideoDetails vDet = (VideoDetails)obj;
				Criteria cr = crHelper.getVideoDetailsCriteria(session, vDet);
				objects = cr.list();
			}
		} catch (HibernateException e) {
			handleException(e);
		} 
		//session.refresh(objects);
		return objects;
	}
	
	@SuppressWarnings("rawtypes")
	public List findByExactName(Object entity){
		List entities = null;
		try {
			//session.setCacheMode(CacheMode.IGNORE);
			//session.evict(entity);			
			if(entity instanceof AppUser){
				AppUser app=(AppUser)entity;
				Criteria cr = crHelper.getAppUserByExactNameCriteria(session, app);
				entities = cr.list();
			} 
		}catch (HibernateException e) {
				handleException(e);
		} 
		return entities;
	}
	
	@SuppressWarnings("rawtypes")
	public List executeCustomQuery(String sql, Class<?> clazz, Map<String, Object> params){
		List results = null;
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(clazz);
		if(null!=params && !params.isEmpty()){
			for(String key:params.keySet()){
				query.setParameter(key, params.get(key));
			}			
		}
		results = query.list();
		return results;
	}

	/**
	 * @param e
	 * @throws DataAccessLayerException
	 */
	public void handleException(HibernateException e) throws DataAccessLayerException {
		HibernateConnection.rollback(tx);
		//if(!session.isConnected()){
		session.close();
			session = HibernateStatelessConnection.getSession();
			System.out.println("Session was invalidated. Attempting to restore session: ");
			throw new DataAccessLayerException(e);		
	}

	public void beginTransaction() throws HibernateException {
		tx = session.beginTransaction();
	}
	public void commitTransaction() throws HibernateException{
		try {
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			//HibernateConnection.close(session);
		}

	}	
}
