package com.giuliosmtech.products.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.giuliosmtech.products.enums.ProductStatus;

import lombok.Builder;

@Builder
public record ProductResponse(

		Long id,

		String name,

		String description,

		BigDecimal price,

		Integer stock,
		
		@JsonFormat
		ProductStatus status,
		
		LocalDateTime createdAt,
		
		LocalDateTime updatedAt

) {

}