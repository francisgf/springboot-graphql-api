package com.giuliosmtech.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.giuliosmtech.products.entity.Product;
import com.giuliosmtech.products.enums.ProductStatus;

/**
 * Repository interface for Product entity operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Finds products by name containing the term (case-insensitive).
	 * @param name the search term
	 * @return list of matching products
	 */
	List<Product> findByNameContainingIgnoreCase(String name);

	/**
	 * Finds products by exact name.
	 * @param name the product name
	 * @return list of products with the exact name
	 */
	List<Product> findByName(String name);

	/**
	 * Finds all products by status.
	 * @param status the product status
	 * @return list of products with the given status
	 */
	List<Product> findAllByStatus(ProductStatus status);

}
