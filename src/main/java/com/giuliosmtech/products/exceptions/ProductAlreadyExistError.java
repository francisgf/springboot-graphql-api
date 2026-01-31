package com.giuliosmtech.products.exceptions;
public class ProductAlreadyExistError extends RuntimeException {

    public ProductAlreadyExistError(String message) {
        super(message);

    }

    public ProductAlreadyExistError(String message, Throwable cause) {
        super(message, cause);
    }
}