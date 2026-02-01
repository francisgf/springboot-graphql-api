package com.giuliosmtech.products.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.giuliosmtech.products.enums.ProductStatus;

import lombok.Builder;

/**
 * Data transfer object for product response data.
 */
@Builder
public record ProductResponse(

		Long id,

		String name,

		String description,

		BigDecimal price,

		Integer stock,
		
		ProductStatus status,
		
		LocalDateTime createdAt,
		
		LocalDateTime updatedAt

) {

}