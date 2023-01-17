package com.rlj.payroll;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

	@Override
	public EntityModel<Order> toModel(Order order) {
		EntityModel<Order> orderEntity = EntityModel.of(order,
				linkTo(methodOn(OrderController.class).all()).withRel("orders"),
				linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel());

		if (Status.IN_PROGESS == order.getStatus()) {
			orderEntity.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId())).withRel("cancel"));
			orderEntity.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId())).withRel("complete"));
		}

		return orderEntity;
	}

}
