package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
@AllArgsConstructor
@Getter
public class Grade {

	@ManyToOne
	private Subject subject;

	private Double value;

	protected Grade() {
		// Required by Hibernate
	}
}