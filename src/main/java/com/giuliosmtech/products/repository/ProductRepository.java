package com.giuliosmtech.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.giuliosmtech.products.entity.Product;
import com.giuliosmtech.products.enums.ProductStatus;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameContainingIgnoreCase(String name);

	List<Product> findByName(String name);

	List<Product> findAllByStatus(ProductStatus status);
	// Optional<Product> findByIdActive(Long id);

}
