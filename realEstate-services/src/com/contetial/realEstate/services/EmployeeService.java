package com.contetial.realEstate.services;

import java.util.ArrayList;
import java.util.List;

import com.contetial.realEstate.persistance.entity.Employee;

public class EmployeeService  extends GenericService{

	public EmployeeService(){
		super(EmployeeService.class);
	}
	
	public Employee findEmpById(Long empId){
		Employee emp = (Employee) findById(Employee.class, empId);		
		return (Employee)copyBean(emp, new Employee());
	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> findEmployees(Employee emp){
		List<Employee> employees = (List<Employee>) findEntity(emp);
		/*List<Employee> empBeans = new ArrayList<Employee>();
		for(Employee oneEmp:employees){
			empBeans.add((Employee)copyBean(oneEmp, new Employee()));
		}*/
		return employees;
	}
	
	public boolean addEmployee(Employee emp){		
		addEntity((Employee) copyBean(emp, new Employee()));
		return true;
	}
	
	public boolean updateEmployee(Employee emp){		
		Employee origEmp = (Employee) workUnit.find(
				Employee.class, emp.getEmpId().longValue());		
		updateEntity((Employee) copyBean(emp, origEmp));
		return true;
	}
	
	public boolean deleteEmployee(List<Employee> empBeans){
		List<Employee> emps = new ArrayList<Employee>();
		for(Employee empBean: empBeans){
			emps.add((Employee) copyBean(empBean, new Employee()));	
		}
		deleteEntity(emps);
		return true;
	}
}
