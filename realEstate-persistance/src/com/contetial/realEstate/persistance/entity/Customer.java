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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
property="customerId",scope=Customer.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer implements Serializable,IEntity {

	private static final long serialVersionUID = 6599002411985812033L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id")
	private Long customerId;

	@Column(name="address")
	private String address;

	@Column(name="customer_name")
	private String customerName;

	@Column(name="contactNo")
	private Long contactNo;

	@Column(name="dob")
	private Date dob;

	@Column(name="doa")
	private Date doa;

	@Column(name="status")
	private String status;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "refered_by", nullable = false)
	private AppUser referedBy;

	/**
	 * 
	 */
	public Customer() {}


	/**
	 * @param customerName
	 * @param address
	 */
	public Customer(String customerName, String address) {
		super();
		this.address = address;
		this.customerName = customerName;

	}

	/**
	 * @param customerId
	 * @param customerName
	 * @param address
	 */
	public Customer(Long customerId, String customerName, String address,
			String status,Long contactNo,Date dob,Date doa, AppUser referedBy) {
		super();
		this.customerId = customerId;
		this.address = address;
		this.customerName = customerName;
		this.contactNo = contactNo;
		this.status = status;
		this.dob = dob;
		this.doa=doa;
		this.referedBy=referedBy;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public Long getContactNo() {
		return contactNo;
	}


	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	public Date getDoa() {
		return doa;
	}


	public void setDoa(Date doa) {
		this.doa = doa;
	}


	public String getDobStr() {
		String dobStr = "";
		if(null!=getDob()){
			dobStr=new SimpleDateFormat("dd-MM-yyyy").format(getDob());
		}
		return dobStr;
	}


	public void setDobStr(String dobStr) throws ParseException {
		//this.dobStr = dobStr;
		if(null!=dobStr && !dobStr.isEmpty()){
			setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr));
		}
	}


	public String getDoaStr() {		
		String doaStr = "";
		if(null!=getDoa()){
			doaStr=new SimpleDateFormat("dd-MM-yyyy").format(getDoa());
		}
		return doaStr;
	}


	public void setDoaStr(String doaStr) throws ParseException {
		if(null!=doaStr && !doaStr.isEmpty()){
			setDoa(new SimpleDateFormat("yyyy-MM-dd").parse(doaStr));
		}
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public AppUser getReferedBy() {
		return referedBy;
	}


	public void setReferedBy(AppUser referedBy) {
		this.referedBy = referedBy;
	}
}