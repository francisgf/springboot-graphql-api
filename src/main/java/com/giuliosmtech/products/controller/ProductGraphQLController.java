package com.giuliosmtech.products.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.giuliosmtech.products.dto.ProductRequest;
import com.giuliosmtech.products.dto.ProductResponse;
import com.giuliosmtech.products.enums.ProductStatus;
import com.giuliosmtech.products.service.ProductService;


@Controller
/**
 * GraphQL controller for handling product-related queries and mutations.
 */
public class ProductGraphQLController {

	
   private final ProductService productService;
    
    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }
    
    
    /**
     * Retrieves products filtered by status (required).
     * @param status product status filter (required)
     * @return list of products
     */
    @QueryMapping(name = "products")
    public List<ProductResponse> products(@Argument @NotNull ProductStatus status) {
        return productService.getByStatus(status);
    }
    
    /**
     * Retrieves all active products.
     * @return list of active products
     */
    @QueryMapping(name = "activeProducts")
    public List<ProductResponse> activeProducts() {
        return productService.getAllActive();
    }
    
    /**
     * Retrieves a product by ID.
     * @param id the product ID
     * @return the product or null if not found
     */
    @QueryMapping(name = "product")
    public ProductResponse product(@Argument(name = "id") @NotNull Long id) {
        return productService.getById(id).orElse(null);
    }
    
    /**
     * Searches products by name.
     * @param name the search term
     * @return list of matching products
     */
    @QueryMapping(name = "searchProducts")
    public List<ProductResponse> searchProducts(@Argument @NotNull String name) {
        return productService.searchProductsByTerm(name);
    }
    
    /**
     * Creates a new product.
     * @param input the product request data
     * @return the created product
     */
    @MutationMapping
    public ProductResponse createProduct(@Valid @Argument ProductRequest input) {
        return productService.create(input);
    }
    
    /**
     * Updates an existing product.
     * @param id the product ID
     * @param input the product request data
     * @return the updated product
     */
    @MutationMapping
    public ProductResponse updateProduct(@Argument @NotNull Long id, @Valid @Argument ProductRequest input) {
        return productService.update(id, input);
    }
    
    /**
     * Deletes a product.
     * @param id the product ID
     * @return true if deleted successfully
     */
    @MutationMapping
    public Boolean deleteProduct(@Argument @NotNull Long id) {
        productService.delete(id);
        return true;
    }
    
 
}
