package com.rlj.payroll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public record Employee(
		@Id @GeneratedValue Long id,
		String name,
		String role) {
}
