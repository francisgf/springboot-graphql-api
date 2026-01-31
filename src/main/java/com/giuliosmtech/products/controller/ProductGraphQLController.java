package com.giuliosmtech.products.controller;

import jakarta.validation.Valid;

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
public class ProductGraphQLController {

	
   private final ProductService productService;
    
    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }
    
    
    @QueryMapping(name = "products")
    public List<ProductResponse> products(@Argument ProductStatus status) {
        if (status != null) {
            return productService.getByStatus(status);
        }
        return productService.getAll();
    }
    
    @QueryMapping(name = "activeProducts")
    public List<ProductResponse> activeProducts() {
        return productService.getAllActive();
    }
    
    @QueryMapping(name = "product")
    public ProductResponse product(@Argument(name = "id") Long id) {
        return productService.getById(id).orElse(null);
    }
    
    @QueryMapping(name = "searchProducts")
    public List<ProductResponse> searchProducts(@Argument String name) {
        return productService.searchProductsByTerm(name);
    }
    
    @MutationMapping
    public ProductResponse createProduct(@Valid @Argument ProductRequest input) {
        return productService.create(input);
    }
    
    @MutationMapping
    public ProductResponse updateProduct(@Argument Long id, @Valid @Argument ProductRequest input) {
        return productService.update(id, input);
    }
    
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        productService.delete(id);
        return true;
    }
    
 
}
