package com.giuliosmtech.products.service;

import java.util.List;
import java.util.Optional;

import com.giuliosmtech.products.dto.ProductRequest;
import com.giuliosmtech.products.dto.ProductResponse;
import com.giuliosmtech.products.enums.ProductStatus;

/**
 * Service interface for product operations.
 */
public interface ProductService {

	/**
	 * Returns all products.
	 * @return list of all products
	 */
	List<ProductResponse> getAll();
	
	/**
	 * Returns all active products.
	 * @return list of active products
	 */
	List<ProductResponse> getAllActive();
	
	/**
	 * Returns products by status.
	 * @param status the product status
	 * @return list of products with the given status
	 */
	List<ProductResponse> getByStatus(ProductStatus status);
	
	/**
	 * Returns product by ID.
	 * @param id the product ID
	 * @return optional containing the product if found
	 */
	Optional<ProductResponse> getById(Long id);

	/**
	 * Returns products by name.
	 * @param name the product name
	 * @return list of products with the given name
	 */
	List<ProductResponse> getByName(String name);

	/**
	 * Creates a new product.
	 * @param productRequest the product request data
	 * @return the created product response
	 */
	ProductResponse create(ProductRequest productRequest);
	
	/**
	 * Updates an existing product.
	 * @param id the product ID
	 * @param productRequest the product request data
	 * @return the updated product response
	 */
	ProductResponse update(Long id, ProductRequest productRequest);
	
	/**
	 * Deletes a product.
	 * @param id the product ID
	 */
	void delete(Long id);
	
	/**
	 * Searches products by term.
	 * @param term the search term
	 * @return list of products matching the search term
	 */
	List<ProductResponse> searchProductsByTerm(String term);

}
