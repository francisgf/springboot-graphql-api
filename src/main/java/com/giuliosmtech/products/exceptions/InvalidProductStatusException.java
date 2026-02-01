package com.giuliosmtech.products.exceptions;

/**
 * Exception thrown when an invalid product status is encountered.
 */
public class InvalidProductStatusException extends RuntimeException {

	public InvalidProductStatusException(String message) {
		super(message);

	}

	public InvalidProductStatusException(String message, Throwable cause) {
		super(message, cause);
	}
}
