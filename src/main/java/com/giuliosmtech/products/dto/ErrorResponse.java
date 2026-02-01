package com.giuliosmtech.products.dto;

import java.util.Map;

/**
 * Represents an error response with a message and field-specific errors.
 */
public record ErrorResponse(
		String message, 
		Map<String, String> fieldErrors) {
}