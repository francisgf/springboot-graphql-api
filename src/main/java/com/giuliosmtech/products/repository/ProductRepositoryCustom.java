package com.giuliosmtech.products.repository;

import java.util.List;
import java.util.Optional;

import com.giuliosmtech.products.entity.Product;

public interface ProductRepositoryCustom {

    List<Product> findAllActive();

    Optional<Product> findByIdActive(Long id);
}