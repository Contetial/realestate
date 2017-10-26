package com.contetial.realEstate.persistance.worker;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.contetial.realEstate.persistance.entity.AppUser;
import com.contetial.realEstate.persistance.entity.Deal;
import com.contetial.realEstate.persistance.entity.DealLogBook;
import com.contetial.realEstate.persistance.entity.DealSchedule;
import com.contetial.realEstate.persistance.entity.Gallery;
import com.contetial.realEstate.persistance.entity.ImageDetails;
import com.contetial.realEstate.persistance.entity.Property;
import com.contetial.realEstate.persistance.entity.VideoDetails;

public class StatelessCriteriaHelper {

	protected Criteria getAppUserCriteria(StatelessSession session, AppUser app) {
		Criteria cr = session.createCriteria(AppUser.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=app.getUserName()){
			criteria.add(Restrictions.like("userName", app.getUserName()));
		}if(null!=app.getAddress()){
			criteria.add(Restrictions.like("address", app.getAddress()));
		}if(null!=app.getUserRole()){
			criteria.add(Restrictions.eq("userRole", app.getUserRole()));
		}if(null!=app.getManager()&&null!=app.getManager().getUserId()){
			criteria.add(Restrictions.eq("managerId", app.getManager().getUserId()));
		}
		criteria.add(Restrictions.isNotNull("userId"));
		
		setLocalCriteria(cr, criteria);
		return cr;
	}
	
	protected Criteria getAppUserByExactNameCriteria(StatelessSession session, AppUser app) {
		Criteria cr = session.createCriteria(AppUser.class);
		cr.add(Restrictions.eq("userName", app.getUserName()));
		return cr;
	}

	protected Criteria getPropertyCriteria(StatelessSession session, Property prop) {		
		Criteria cr = session.createCriteria(Property.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=prop.getPropertyId()){
			criteria.add(Restrictions.eq("propertyId", prop.getPropertyId()));			
		}if(null!=prop.getPropName()){
			criteria.add(Restrictions.like("propName", prop.getPropName()));
		}if(null!=prop.getPropAddress()){
			criteria.add(Restrictions.like("propAddress", prop.getPropAddress()));
		}if(null!=prop.getPropDetails()){
			criteria.add(Restrictions.like("propDetails", prop.getPropDetails()));
		}if(null!=prop.getOnGoingDeals()&& !prop.getOnGoingDeals().isEmpty()){
			criteria.add(Restrictions.in("onGoingDeals", prop.getOnGoingDeals()));
		}
		criteria.add(Restrictions.isNotNull("propStatus"));
		
		setLocalCriteria(cr, criteria);
		return cr;
	}

	protected Criteria getDealLogBookCriteria(StatelessSession session, DealLogBook logBook) {
		Criteria cr = session.createCriteria(DealLogBook.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=logBook.getLogBookId()){
			criteria.add(Restrictions.eq("logBookId", logBook.getLogBookId()));
		}if(null!=logBook.getDealId()){
			criteria.add(Restrictions.eq("dealId", logBook.getDealId()));
		}
		setLocalCriteria(cr, criteria);
		return cr;
	}

	

	protected Criteria getDealCriteria(StatelessSession session, Deal deal) {
		Criteria cr = session.createCriteria(Deal.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=deal.getDealId()){
			criteria.add(Restrictions.eq("dealId", deal.getDealId()));			
		}if(null!=deal.getDealer()){
			criteria.add(Restrictions.eq("dealer", deal.getDealer()));
		}if(null!=deal.getCust()){
			criteria.add(Restrictions.eq("cust", deal.getCust()));
		}if(null!=deal.getProperty()&& null!=deal.getProperty().getPropertyId()){
			criteria.add(Restrictions.eq("property", deal.getProperty()));
		}
		criteria.add(Restrictions.isNotNull("dealStatus"));
		
		setLocalCriteria(cr, criteria);
		return cr;		
	}

	protected Criteria getDealScheduleCriteria(StatelessSession session, DealSchedule schedule) {
		Criteria cr = session.createCriteria(DealSchedule.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=schedule.getDeal()){
			criteria.add(Restrictions.eq("deal", schedule.getDeal()));			
		}
		if(null!=schedule.getDealScheduleId()){
			criteria.add(Restrictions.eq("dealScheduleId", schedule.getDealScheduleId()));			
		}
		if(null!=schedule.getDealDate()){
			criteria.add(Restrictions.eq("dealDate", schedule.getDealDate()));			
		}
		if(null!=schedule.getDealFromTime()){
			criteria.add(Restrictions.eq("dealFromTime", schedule.getDealFromTime()));			
		}
		if(null!=schedule.getDealTillTime()){
			criteria.add(Restrictions.eq("dealTillTime", schedule.getDealTillTime()));			
		}		
		setLocalCriteria(cr, criteria);
		return cr;
	}
	
	protected Criteria getImageDetailsCriteria(StatelessSession session,ImageDetails details) {
		Criteria cr = session.createCriteria(ImageDetails.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=details.getImageid()){
			criteria.add(Restrictions.eq("imageid", details.getImageid()));			
		}if(null!=details.getImagename()){
			criteria.add(Restrictions.like("imagename", details.getImagename()));
		}if(null!=details.getImagepath()){
			criteria.add(Restrictions.like("imagepath", details.getImagepath()));
		}if(null!=details.getGallery()&& null!=details.getGallery().getGalleryid()){
			criteria.add(Restrictions.eq("gallery", details.getGallery()));
		}		
		setLocalCriteria(cr, criteria);		
		return cr;
	}
	
	protected Criteria getVideoDetailsCriteria(StatelessSession session,VideoDetails details) {
		Criteria cr = session.createCriteria(ImageDetails.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=details.getVideoid()){
			criteria.add(Restrictions.eq("videoid", details.getVideoid()));			
		}if(null!=details.getVideoname()){
			criteria.add(Restrictions.like("videoname", details.getVideoname()));
		}if(null!=details.getVideopath()){
			criteria.add(Restrictions.like("videopath", details.getVideopath()));
		}if(null!=details.getGallery()&& null!=details.getGallery().getGalleryid()){
			criteria.add(Restrictions.eq("gallery", details.getGallery()));
		}
		setLocalCriteria(cr, criteria);
		return cr;
	}

	public Criteria getGalleryCriteria(StatelessSession session, Gallery gal) {
		Criteria cr = session.createCriteria(Gallery.class);
		Set<Criterion> criteria =new HashSet<Criterion>();
		if(null!=gal.getProperty() && null!=gal.getProperty().getPropertyId()){
			criteria.add(Restrictions.eq("property", gal.getProperty()));
		}
		setLocalCriteria(cr, criteria);
		return cr;
	}
	
	private void setLocalCriteria(Criteria cr, Set<Criterion> criteria) {
		Criterion[] criterion = new Criterion[criteria.size()];
		int i=0;
		for(Criterion ctr:criteria){
			criterion[i]=ctr;
			i++;
		}
		
		cr.add(Restrictions.or(criterion))
			.setCacheable(false);
			//.setCacheMode(CacheMode.REFRESH);
	}
}
