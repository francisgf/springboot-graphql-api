package com.giuliosmtech.products.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.giuliosmtech.products.dto.ProductRequest;
import com.giuliosmtech.products.dto.ProductResponse;
import com.giuliosmtech.products.entity.Product;
import com.giuliosmtech.products.enums.ProductStatus;
import com.giuliosmtech.products.exceptions.ProductAlreadyExistError;
import com.giuliosmtech.products.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	
	@Override
	public List<ProductResponse> getAll() {
		
		List<Product> products = this.productRepository.findAll();
		
		return  products.stream()
				.map(this::toResponse)
				.toList();
		
	}

	@Override
	public Optional<ProductResponse> getById(Long id) {
		
		Optional<Product> product = productRepository.findById(id);
		
		 return product.isPresent() ?
		           product.stream()
		                  .map(this::toResponse)
		                  .findFirst() :
		           Optional.empty();
		 
	}

	@Override
	public List<ProductResponse> getByName(String name) {
		
		List<Product> products =  productRepository.findByName(name);
		
		return products.stream()
				.map(this::toResponse)
				.toList();
				
	}

    @Transactional
	@Override
	public ProductResponse create(ProductRequest productRequest) {
		
		// Check if product with same name already exists
		if (!productRepository.findByName(productRequest.name()).isEmpty()) {
			throw new ProductAlreadyExistError("Product with same name already exists");
		}
				
		Product product =  productRepository.save(toEntity(productRequest));
		return toResponse(product);
		
	}

	
	@Transactional
	@Override
	public ProductResponse update(Long id, ProductRequest productRequest) {
	
		return productRepository.findById(id)
				.map(product -> {
				
					product.setName(productRequest.name());
					product.setDescription(productRequest.description());
					product.setPrice(productRequest.price());
					product.setStatus(productRequest.status());
					product.setStock(productRequest.stock());
					
					return productRepository.save(product);
					
				})
				.map(this::toResponse)
				.orElseThrow(()-> new RuntimeException("Product not found"));
				
	}

	@Override
	public List<ProductResponse> searchProductsByTerm(String term) {
		
		List<Product> products = productRepository.findByNameContainingIgnoreCase(term);
		
		return products.stream()
				.map(this::toResponse)
				.toList();
				
	}

	@Override
	public void delete(Long id) {
		
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		
		product.setStatus(ProductStatus.DELETED);
		productRepository.save(product);
		
	}


	@Override
	public List<ProductResponse> getAllActive() {

List<Product> products = productRepository.findAllByStatus(ProductStatus.ACTIVE);
		
		return products.stream()
				.map(this::toResponse)
				.toList();
	}
	
	@Override
	public List<ProductResponse> getByStatus(ProductStatus status) {

		List<Product> products = productRepository.findAllByStatus(status);
		
		return products.stream()
				.map(this::toResponse)
				.toList();
	}
	
	private ProductResponse toResponse(Product productEntity){
		
			return ProductResponse.builder()
					.id(productEntity.getId())
					.name(productEntity.getName())
					.description(productEntity.getDescription())
					.price(productEntity.getPrice())
					.stock(productEntity.getStock())
					.status(productEntity.getStatus())
					.createdAt(productEntity.getCreatedAt())
					.updatedAt(productEntity.getUpdatedAt())
					.build();
	}
	
	
	private Product toEntity(ProductRequest productRequest){
				
			return Product.builder()
					.name(productRequest.name())
					.description(productRequest.description())
					.price(productRequest.price())
					.stock(productRequest.stock())
					.status(productRequest.status() != null ? productRequest.status() : ProductStatus.ACTIVE)
					.build();						
			}


	
}
