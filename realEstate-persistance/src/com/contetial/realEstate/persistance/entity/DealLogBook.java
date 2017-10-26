package com.contetial.realEstate.persistance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="deal_logbook")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
property="logBookId",scope=DealLogBook.class)
public class DealLogBook implements Serializable, IEntity{

	private static final long serialVersionUID = -5371032455769335200L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="logbook_id")
	private Long logBookId;
	
	@Column(name = "deal_id")
	private Long dealId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private AppUser createdBy;
	
	@OrderBy
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="log_entry")
	private String logEntry;
	
	@Column(name="log_status")
	private String logStatus;

	public Long getLogBookId() {
		return logBookId;
	}

	public void setLogBookId(Long logBookId) {
		this.logBookId = logBookId;
	}

	public AppUser getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(AppUser createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLogEntry() {
		return logEntry;
	}

	public void setLogEntry(String logEntry) {
		this.logEntry = logEntry;
	}

	public String getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
		
	public Long getDealId() {
		return dealId;
	}

	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}
	
	public Long getCreatedById() {
		return createdBy.getUserId();
	}

	public void setCreatedById(Long createdById) {
		if(null==this.createdBy){
			createdBy = new AppUser();
		}
		createdBy.setUserId(createdById);
	}
	
	public String getCreatedByName() {
		return createdBy.getUserName();
	}

	public void setCreatedByName(String createdByName) {
		if(null==this.createdBy){
			createdBy = new AppUser();
		}
		createdBy.setUserName(createdByName);
	}
}
