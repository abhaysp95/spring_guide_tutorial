package com.rlj.payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class EmployeeController {

	// @Autowired
	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;

	public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<EntityModel<Employee>> all() {
		// return this.repository.findAll();
		List<EntityModel<Employee>> employees = this.repository.findAll()
			.stream()
			.map(this.assembler::toModel)
			.collect(Collectors.toList());

		return CollectionModel.of(employees,
				linkTo(methodOn(EmployeeController.class).all()).withSelfRel());

	}

	@PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee newEmployee(@RequestBody Employee employee) {
		return this.repository.save(employee);
	}

	@GetMapping(value = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<Employee> one(@PathVariable Long id) {
		/* return this.repository.findById(id)
			.orElseThrow(() -> new EmployeeNotFoundException(id)); */
		Employee employee = this.repository.findById(id)
			.orElseThrow(() -> new EmployeeNotFoundException(id));

		/* return EntityModel.of(employee,
				linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).all()).withRel("employees")); */
		return this.assembler.toModel(employee);
	}

	@PutMapping(value = "/employees/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee replaceEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		return this.repository.findById(id)
			.map(emp -> {
				emp.setName(employee.getName());
				emp.setRole(employee.getRole());
				return this.repository.save(emp);
			})
			.orElseGet(() -> {
				employee.setId(id);
				return this.repository.save(employee);
			});
	}

	@DeleteMapping(value = "/employees/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		this.repository.deleteById(id);
	}
}
