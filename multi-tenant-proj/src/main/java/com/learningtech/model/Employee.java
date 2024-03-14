package com.learningtech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "Employee")
@Data
@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMP_ID")
	private Long id;
	
	@Column(name = "TENANT_ID") 
	private Long tenantId;
	
	@Column(name = "EMP_NAME")
	private String ename;
	
	@Column(name = "EMP_SALARY")
	private Double salary;

	@Column(name = "DEPT_NAME")
	private String depatment;

}
