package com.giuliosmtech.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.giuliosmtech.products.dto.ErrorResponse;

import graphql.GraphQLError;

import jakarta.validation.ConstraintViolationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST and GraphQL endpoints.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions for @Valid annotated objects (request bodies/parameters).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
       
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles constraint violation exceptions for @NotNull and other constraints on individual parameters.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse("Invalid input parameters", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles JSON parsing exceptions.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid JSON format: " + ex.getMessage(), Collections.emptyMap());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles runtime exceptions.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Collections.emptyMap());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", Collections.emptyMap());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

   /*custom exceptions business logic */
    /**
     * Handles invalid product status exceptions.
     */
    @ExceptionHandler(InvalidProductStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProductStatusException(InvalidProductStatusException ex){
    	
    	String errorMessage = ex.getMessage();
    	ErrorResponse errorResponse = new ErrorResponse(errorMessage, Collections.emptyMap());
    	
    	return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST );
    }

    /**
     * Handles product already exists exceptions.
     */
    @ExceptionHandler(ProductAlreadyExistError.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExistError(ProductAlreadyExistError ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Collections.emptyMap());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles product not found exceptions.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Collections.emptyMap());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all exceptions for GraphQL.
     */
    @GraphQlExceptionHandler
    public GraphQLError handleAllExceptionsForGraphQL(Exception ex) {
        if (ex instanceof ProductAlreadyExistError e) {
            return GraphQLError.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        } else if (ex instanceof InvalidProductStatusException e) {
            return GraphQLError.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        } else if (ex instanceof ProductNotFoundException e) {
            return GraphQLError.newError()
                    .errorType(ErrorType.NOT_FOUND)
                    .message(e.getMessage())
                    .build();
        } else if (ex instanceof ConstraintViolationException e) {
            return GraphQLError.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message("Validation failed")
                    .build();
        } else if (ex instanceof RuntimeException e) {
            return GraphQLError.newError()
                    .errorType(ErrorType.INTERNAL_ERROR)
                    .message("Internal server error")
                    .build();
        } else {
            return GraphQLError.newError()
                    .errorType(ErrorType.INTERNAL_ERROR)
                    .message("Internal server error")
                    .build();
        }
    }
}