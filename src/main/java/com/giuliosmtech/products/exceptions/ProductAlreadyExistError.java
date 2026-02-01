package com.giuliosmtech.products.exceptions;

/**
 * Exception thrown when attempting to create a product that already exists.
 */
public class ProductAlreadyExistError extends RuntimeException {

    public ProductAlreadyExistError(String message) {
        super(message);

    }

    public ProductAlreadyExistError(String message, Throwable cause) {
        super(message, cause);
    }
}