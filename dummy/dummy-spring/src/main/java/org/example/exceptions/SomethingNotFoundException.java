package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SomethingNotFoundException extends RuntimeException {

	private String message;
	private int code;

}
