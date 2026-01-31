package com.giuliosmtech.products.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giuliosmtech.products.dto.ProductRequest;
import com.giuliosmtech.products.dto.ProductResponse;
import com.giuliosmtech.products.enums.ProductStatus;
import com.giuliosmtech.products.exceptions.InvalidProductStatusException;
import com.giuliosmtech.products.service.ProductService;

@Slf4j
@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Products", description = "API for managing products")
public class ProductController {
	
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping
	@Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data")
	})
	public ResponseEntity<ProductResponse>create (@Valid @RequestBody ProductRequest productRequest) {
		
		ProductResponse productResponse = productService.create(productRequest);
	
		return ResponseEntity.ok(productResponse);
		
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update a product", description = "Updates an existing product by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "404", description = "Product not found")
	})
	public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
		
		try {
			
		  ProductStatus.valueOf(productRequest.status().name());

			ProductResponse productResponse = productService.update(id, productRequest);
			return ResponseEntity.ok(productResponse);
		
		 } catch (IllegalArgumentException e) {
			 log.error("Invalid ProductStatus value");
	            throw new InvalidProductStatusException("Invalid ProductStatus value", e.getCause());
	     } catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a product", description = "Soft deletes a product by setting its deleted flag to true")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Product deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Product not found")
	})
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		
		try {
			productService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@GetMapping("/active")
	@Operation(summary = "Get all active products", description = "Retrieves a list of products with ACTIVE status")
	@ApiResponse(responseCode = "200", description = "List of active products")
	public ResponseEntity<List<ProductResponse>> getActiveProducts() {
		
		List<ProductResponse> products = productService.getAllActive();
		
		return ResponseEntity.ok(products);
		
	}
		
	@GetMapping("/{id}")
	@Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product found"),
			@ApiResponse(responseCode = "404", description = "Product not found")
	})
	public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
		
		return productService.getById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/name/{name}")
	@Operation(summary = "Get products by name", description = "Retrieves products that match the exact name")
	@ApiResponse(responseCode = "200", description = "List of products retrieved")
	public ResponseEntity<List<ProductResponse>> getByName(@PathVariable String name) {
		
		List<ProductResponse> products = productService.getByName(name);
		
		return ResponseEntity.ok(products);
		
	}
	
	
	@GetMapping("/search")
	@Operation(summary = "Search products by term", description = "Searches products whose name contains the term (case-insensitive)")
	@ApiResponse(responseCode = "200", description = "List of matching products")
	public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String term) {
		
		List<ProductResponse> products = productService.searchProductsByTerm(term);
		
		return ResponseEntity.ok(products);
		
	}

}
