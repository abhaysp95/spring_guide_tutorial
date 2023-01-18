package com.rlj.spring_rest_hal;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting extends RepresentationModel<Greeting> {

	private final String content;

	@JsonCreator
	public Greeting(@JsonProperty String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

}
