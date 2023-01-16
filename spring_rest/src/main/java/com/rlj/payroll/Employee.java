package com.rlj.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

	private @Id @GeneratedValue Long id;
	private String name;
	private String firstName;
	private String lastName;
	private String role;

	public Employee() { }

	public Employee(String name, String role) {
		this.setName(name);
		this.role = role;
	}

	public Employee(String firstName, String lastName, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public void setName(String name) {
		this.name = name;
		String[] nameParts = name.split(" ");
		this.firstName = nameParts[0];
		this.lastName = nameParts[1];
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Employee)) {
			return false;
		}

		Employee employee = (Employee) o;
		return Objects.equals(this.id, employee.getId())
			&& this.firstName.equals(employee.getFirstName())
			&& this.lastName.equals(employee.getLastName())
			&& this.role.equals(employee.getRole());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.firstName, this.lastName, this.role);
	}

	@Override
	public String toString() {
		return new StringBuilder("[")
			.append(this.id).append(",")
			.append(this.firstName).append(",")
			.append(this.lastName).append(",")
			.append(this.role).append("]").toString();
	}

}
