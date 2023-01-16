package com.rlj.payroll;

public class EmployeeNotFoundException extends RuntimeException {

	EmployeeNotFoundException(Long msg) {
		super("Employee not found: " + msg);
	}

}
