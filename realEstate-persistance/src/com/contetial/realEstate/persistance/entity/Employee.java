package com.contetial.realEstate.persistance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee implements Serializable,IEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5677234387745569203L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="emp_id")
	private Long empId;
	
	@Column(name="emp_manager_id")
	private Long managerId;
	
	@Column(name="emp_name")
	private String empName;
	
	@Column(name="emp_role")
	private String empRole;
	
	@Column(name="emp_address")
	private String empAddress;
	
	@Column(name="emp_dob")
	private Date empDOB;
	
	@Column(name="emp_bg")
	private String empBG;
	
	@Column(name="emp_contact")
	private Long empContact;
	
	private Double salary;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public Date getEmpDOB() {
		return empDOB;
	}

	public void setEmpDOB(Date empDOB) {
		this.empDOB = empDOB;
	}

	public String getEmpBG() {
		return empBG;
	}

	public void setEmpBG(String empBG) {
		this.empBG = empBG;
	}

	public Long getEmpContact() {
		return empContact;
	}

	public void setEmpContact(Long empContact) {
		this.empContact = empContact;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	
}
