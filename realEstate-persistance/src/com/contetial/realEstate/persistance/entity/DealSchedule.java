package com.contetial.realEstate.persistance.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="deal_schedule")
public class DealSchedule implements Serializable, IEntity{

	private static final long serialVersionUID = 6625554311428521484L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="deal_schedule_id")	
	private Long dealScheduleId;
	
	@OneToOne
	@JoinColumn(name = "deal_id", nullable = false)
	private Deal deal;
	
	@Column(name="deal_date")
	private Date dealDate;
	
	@Column(name="deal_from")
	private Date dealFromTime;
	
	@Column(name="deal_to")
	private Date dealTillTime;
	
	@Column(name="schedule_type")
	private String scheduleType;
	
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}	
	public Long getDealScheduleId() {
		return dealScheduleId;
	}
	public void setDealScheduleId(Long dealScheduleId) {
		this.dealScheduleId = dealScheduleId;
	}
	public Date getDealFromTime() {
		return dealFromTime;
	}
	public void setDealFromTime(Date dealFromTime) {
		this.dealFromTime = dealFromTime;
	}
	public Date getDealTillTime() {
		return dealTillTime;
	}
	public void setDealTillTime(Date dealTillTime) {
		this.dealTillTime = dealTillTime;
	}
	public Deal getDeal() {
		return deal;
	}
	public void setDeal(Deal deal) {
		this.deal = deal;
	}
	
	public String getDealDateStr() {
		if(null!=getDealDate()){
			String dealDateStr = new SimpleDateFormat("yyyy-MM-dd").format(getDealDate());
			return dealDateStr;
		}else{
			return "";
		}
	}
	public void setDealDateStr(String dealDateStr) throws ParseException {
		setDealDate(new SimpleDateFormat("yyyy-MM-dd").parse(dealDateStr));		
	}
	
	public String getDealFromTimeStr() {
		if(null!=getDealDate()){
			String dealFromTimeStr = new SimpleDateFormat("HH:mm:ss").format(getDealFromTime());
			return dealFromTimeStr;
		}else{
			return "";
		}
	}
	public void setDealFromTimeStr(String dealFromTimeStr) throws ParseException {
		setDealFromTime(new SimpleDateFormat("HH:mm:ss").parse(dealFromTimeStr));		
	}
	
	public String getDealTillTimeStr() {
		String dealTillTimeStr = new SimpleDateFormat("HH:mm:ss").format(getDealTillTime());
		return dealTillTimeStr;
	}
	public void setDealTillTimeStr(String dealTillTimeStr) throws ParseException {
		setDealTillTime(new SimpleDateFormat("HH:mm:ss").parse(dealTillTimeStr));
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
}
