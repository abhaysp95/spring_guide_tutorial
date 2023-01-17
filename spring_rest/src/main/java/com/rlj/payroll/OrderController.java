package com.rlj.payroll;

import java.awt.MediaTracker;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class OrderController {

	private final OrderRepository repository;
	private final OrderModelAssembler assembler;

	public OrderController(OrderRepository repository, OrderModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}


	@GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<EntityModel<Order>> all() {
		List<EntityModel<Order>> orders = this.repository.findAll()
			.stream()
			.map(this.assembler::toModel)
			.collect(Collectors.toList());

		return CollectionModel.of(orders,
				linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}

	@GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<Order> one(@PathVariable Long id) {
		Order order = this.repository.findById(id)
			.orElseThrow(() -> new OrderNotFoundException(id));

		return this.assembler.toModel(order);
	}

	@PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newOrder(@RequestBody Order order) {
		order.setStatus(Status.IN_PROGESS);
		Order newOrder = this.repository.save(order);

		return ResponseEntity
			.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
			.body(assembler.toModel(newOrder));
	}

	@DeleteMapping(value = "/orders/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
		Order order = this.repository.findById(id)
			.orElseThrow(() -> new OrderNotFoundException(id));

		if (Status.IN_PROGESS == order.getStatus()) {
			order.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(this.assembler.toModel(this.repository.save(order)));
		}

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.body(Problem.create()
					.withTitle("Method not allowed")
					.withDetail("Can't cancel order in " + order.getStatus() + " status"));
	}

	@PutMapping(value = "/orders/{id}/complete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> completeOrder(@PathVariable Long id) {
		Order order = this.repository.findById(id)
			.orElseThrow(() -> new OrderNotFoundException(id));

		if (Status.IN_PROGESS == order.getStatus()) {
			order.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(this.assembler.toModel(this.repository.save(order)));
		}

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.body(Problem.create()
					.withTitle("Method not allowed")
					.withDetail("Can't complete order in " + order.getStatus() + " status"));
	}

}
