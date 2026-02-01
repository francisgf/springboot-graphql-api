package com.giuliosmtech.products.dto;

import java.math.BigDecimal;

import com.giuliosmtech.products.enums.ProductStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Data transfer object for product creation or update requests.
 */
@Builder
public record ProductRequest(

		@NotBlank(message = "Name cannot be blank") 
		@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") 
		String name,

		@Size(max = 500, message = "Description must not exceed 500 characters")
		String description,

		@NotNull(message = "Price is required") 
		@DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0") 
		BigDecimal price,
		
		@NotNull(message = "Stock is required")
		@Min(value = 0, message = "Stock must be greater than or equal to 0") 
		Integer stock,
		
		ProductStatus status

) {

}
