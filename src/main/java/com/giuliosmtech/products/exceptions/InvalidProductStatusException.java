package com.giuliosmtech.products.exceptions;

public class InvalidProductStatusException extends RuntimeException {

	public InvalidProductStatusException(String message) {
		super(message);

	}

	public InvalidProductStatusException(String message, Throwable cause) {
		super(message, cause);
	}
}
