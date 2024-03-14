package com.learningtech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learningtech.model.Employee;
import com.learningtech.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;
	
	public Employee saveEmployee(String tenantId,Employee emp) {
		int nenent_id = 0;
		if(tenantId != null) {
			nenent_id = tenantId.equals("tenant_1")? 1:2; 
		}
		emp.setTenantId(Long.valueOf(nenent_id));
		emp = repo.save(emp);
		return emp;
	}
	
	public Employee updateEmployee(Employee emp) {
		Employee employee =  getEmployee(emp.getId());
		if(employee!=null) {
			emp = repo.save(emp);
			return emp;
		}
		return null;
	}
	
	public List<Employee> getAll() {
		return repo.findAll();
	}
	
	public String deleteEmployee(Long id) {
		Employee employee =  getEmployee(id);
		if(employee!=null) {
			repo.deleteById(id);
			return id +" Got deleted";
		}
		return id +" id not available to deleted";
	}
	
	public Employee getEmployeeById(Long id) {
		Employee employee =  getEmployee(id);
		if(employee!=null) {
			return employee;
		}
		return null;
	}
	
	private Employee getEmployee(Long empId) {
		Optional<Employee> opt = repo.findById(empId);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

}
