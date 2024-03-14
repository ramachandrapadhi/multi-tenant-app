package com.learningtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.learningtech.model.Employee;
import com.learningtech.service.EmployeeService;


@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@GetMapping("/get")
	public ResponseEntity<String> get() {
		return new ResponseEntity<>("Hi", HttpStatus.OK);	
	}

	@PostMapping("/save")
	public ResponseEntity<Employee> createStudent(@RequestHeader("X-TenantID") String tenantId, @RequestBody Employee emp) {
		Employee resp = service.saveEmployee(tenantId,emp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<Employee> updateStudent(@RequestBody Employee emp) {
		Employee resp = service.updateEmployee(emp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee resp = service.getEmployeeById(id);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<Employee>> getAll() {
		List<Employee> resp = service.getAll();
		
		return new ResponseEntity<>(resp, HttpStatus.OK);	
	}
	
	@DeleteMapping("/delete/{stdId}")
	public ResponseEntity<String> deleteStudentById(@PathVariable Long stdId) {
		String resp = service.deleteEmployee(stdId);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
