package com.example.exception;

public class PersonNotFoundException  extends RuntimeException {

	public PersonNotFoundException(long id) {
		super("No person found for id: " + id);
	}
}