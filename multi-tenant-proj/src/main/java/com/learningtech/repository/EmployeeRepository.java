package com.learningtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learningtech.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
