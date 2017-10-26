package com.contetial.realEstate.persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="deal")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
property="dealId",scope=Deal.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Deal implements Serializable,IEntity, Comparable<Deal> {

	private static final long serialVersionUID = 5514883215111418962L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="deal_id")	
	private Long dealId;
	
	@ManyToOne	
	@JoinColumn(name="user_id")
	@JsonIgnore
	private AppUser dealer;
	
	@ManyToOne	
	@JoinColumn(name="customer_id")
	@JsonIgnore
	private Customer cust;
	
	@Column(name="deal_status")	
	private String dealStatus;
	
	@OneToOne(mappedBy = "deal")
	private DealSchedule schedule;
	
	@ManyToOne
	@JoinColumn(name="property_id")
	@JsonIgnore
	private Property property;
	
	public Long getDealId() {
		return dealId;
	}
	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}
	public AppUser getDealer() {
		return dealer;
	}
	public void setDealer(AppUser dealer) {
		this.dealer = dealer;
	}
	public Customer getCust() {
		return cust;
	}
	public void setCust(Customer cust) {
		this.cust = cust;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public DealSchedule getSchedule() {
		return schedule;
	}
	public void setSchedule(DealSchedule schedule) {
		this.schedule = schedule;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	
	public int compareTo(Deal d) { 
		return this.dealId.compareTo(d.dealId); 
	}
	
	public Long getDealerId() {
		return dealer.getUserId();
	}
	public void setDealerId(Long dealerId) {
		this.dealer = new AppUser();
		dealer.setUserId(dealerId);
	}
	public Long getCustId() {
		return cust.getCustomerId();
	}
	public void setCustId(Long custId) {
		this.cust = new Customer();
		cust.setCustomerId(custId);
	}
	public Long getPropertyId() {
		return property.getPropertyId();
	}
	public void setPropertyId(Long propertyId) {
		this.property = new Property();
		property.setPropertyId(propertyId);
	}
	
	public String getDealerName() {
		return dealer.getUserName();
	}
	public void setDealerName(String dealerName) {
		if(null==dealer){
			this.dealer = new AppUser();
		}
		dealer.setUserName(dealerName);
	}
	public String getCustName() {
		return cust.getCustomerName();
	}
	public void setCustName(String custName) {
		if(null==cust){
			this.cust = new Customer();
		}
		cust.setCustomerName(custName);
	}
	public String getPropertyName() {
		return property.getPropName();
	}
	public void setPropertyName(String propertyName) {
		if(null==property){
			this.property = new Property();
		}
		property.setPropName(propertyName);
	}
	
	@Override 
	public int hashCode(){
		final int prime =3;
		int hash = 35 * prime +((null!=this.dealStatus)?this.dealStatus.hashCode():0);
		hash = 35 * hash + ((null!=this.dealId)?this.dealId.intValue():0); 
        return hash;
    }

    @Override 
    public boolean equals(Object ob){
         boolean equals = ob == this;
         if (!equals && ob != null 
        		 && ob.getClass() == this.getClass()){
              final Deal other = (Deal)ob;
              equals = other.dealId.equals(this.dealId);
         }
         return equals;
    }
}
