package com.giuliosmtech.products.service;

import java.util.List;
import java.util.Optional;

import com.giuliosmtech.products.dto.ProductRequest;
import com.giuliosmtech.products.dto.ProductResponse;
import com.giuliosmtech.products.enums.ProductStatus;

public interface ProductService {

	List<ProductResponse> getAll();
	
	List<ProductResponse> getAllActive();
	
	List<ProductResponse> getByStatus(ProductStatus status);
	
	Optional<ProductResponse> getById(Long id);

	List<ProductResponse> getByName(String name);

	ProductResponse create(ProductRequest productRequest);
	
	ProductResponse update(Long id, ProductRequest productRequest);
	
	void delete(Long id);
	
	List<ProductResponse> searchProductsByTerm(String term);

}
