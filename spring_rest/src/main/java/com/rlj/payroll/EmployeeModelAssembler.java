package com.rlj.payroll;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

	@Override
	public EntityModel<Employee> toModel(Employee employee) {
		return EntityModel.of(employee,
				linkTo(methodOn(EmployeeController.class).all()).withRel("employees"),
				linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel());
	}

}
