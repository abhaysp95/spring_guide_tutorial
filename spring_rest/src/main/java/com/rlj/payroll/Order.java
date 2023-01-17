package com.rlj.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {

	private @Id @GeneratedValue long id;
	private String description;
	private Status status;

	Order() { }

	Order(String description, Status status) {
		this.description = description;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Order)) {
			return false;
		}
		Order order = (Order) o;
		return Objects.equals(this.id, order.getId())
			&& Objects.equals(this.description, order.getDescription())
			&& Objects.equals(this.status, order.getStatus());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.description, this.status);
	}

	@Override
	public String toString() {
		return new StringBuilder("[")
			.append(this.id).append(",")
			.append(this.description).append(",")
			.append(this.status).append("]").toString();
	}
}
