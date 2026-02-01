package com.giuliosmtech.products.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.giuliosmtech.products.dto.ProductRequest;
import com.giuliosmtech.products.dto.ProductResponse;
import com.giuliosmtech.products.entity.Product;
import com.giuliosmtech.products.enums.ProductStatus;
import com.giuliosmtech.products.exceptions.ProductAlreadyExistError;
import com.giuliosmtech.products.exceptions.ProductNotFoundException;
import com.giuliosmtech.products.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the ProductService interface.
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	
	@Override
	public List<ProductResponse> getAll() {
		log.info("Starting getAll");
		List<Product> products = this.productRepository.findAll();
		List<ProductResponse> responses = products.stream()
				.map(this::toResponse)
				.toList();
		log.info("Completed getAll, retrieved {} products", responses.size());
		return responses;
		
	}

	@Override
	public Optional<ProductResponse> getById(Long id) {
		log.info("Starting getById for id: {}", id);
		
		Optional<Product> product = productRepository.findById(id);
		Optional<ProductResponse> response = product.stream()
		                  .map(this::toResponse)
		                  .findFirst();
		log.info("Completed getById for id: {}, found: {}", id, response.isPresent());
		return response;	 
	}

	@Override
	public List<ProductResponse> getByName(String name) {
		log.info("Starting getByName for name: {}", name);
		
		List<Product> products =  productRepository.findByName(name);
		List<ProductResponse> responses = products.stream()
				.map(this::toResponse)
				.toList();
		log.info("Completed getByName, retrieved {} products", responses.size());
		return responses;			
	}

    @Transactional
	@Override
	public ProductResponse create(ProductRequest productRequest) {
		log.info("Starting create for product: {}", productRequest.name());
		
		if (!productRepository.findByName(productRequest.name()).isEmpty()) {
			log.warn("Product with name {} already exists", productRequest.name());
			throw new ProductAlreadyExistError("Product with same name already exists");
		}	

		Product product =  productRepository.save(toEntity(productRequest));
		log.info("Created product with id: {}", product.getId());
		return toResponse(product);	
	}
	
	@Transactional
	@Override
	public ProductResponse update(Long id, ProductRequest productRequest) {
		log.info("Starting update for id: {}", id);
		ProductResponse response = productRepository.findById(id)
				.map(product -> {				
					product.setName(productRequest.name());
					product.setDescription(productRequest.description());
					product.setPrice(productRequest.price());
					product.setStatus(productRequest.status());
					product.setStock(productRequest.stock());
					
					return productRepository.save(product);
					
				})
				.map(this::toResponse)
				.orElseThrow(()-> {
					log.error("Product not found for update, id: {}", id);
					return new ProductNotFoundException("Product not found");
				});
		log.info("Updated product with id: {}", id);
		return response;			
	}

	@Override
	public List<ProductResponse> searchProductsByTerm(String term) {
		log.info("Starting searchProductsByTerm for term: {}", term);
		List<Product> products = productRepository.findByNameContainingIgnoreCase(term);
		List<ProductResponse> responses = products.stream()
				.map(this::toResponse)
				.toList();
		log.info("Completed searchProductsByTerm, found {} products", responses.size());
		return responses;			
	}

	@Override
	public void delete(Long id) {
		log.info("Starting delete for id: {}", id);
		Product product = productRepository.findById(id)
				.orElseThrow(() -> {
					log.error("Product not found for delete, id: {}", id);
					return new ProductNotFoundException("Product not found");
				});
		
		product.setStatus(ProductStatus.DELETED);
		productRepository.save(product);
		log.info("Deleted product with id: {}", id);	
	}

	@Override
	public List<ProductResponse> getAllActive() {
		
		log.info("Starting getAllActive");
        
		List<Product> products = productRepository.findAllByStatus(ProductStatus.ACTIVE);
		List<ProductResponse> responses = products.stream()
				.map(this::toResponse)
				.toList();
		log.info("Completed getAllActive, retrieved {} products", responses.size());
		return responses;
	}
	
	@Override
	public List<ProductResponse> getByStatus(ProductStatus status) {
		log.info("Starting getByStatus for status: {}", status);
		List<Product> products = productRepository.findAllByStatus(status);
		List<ProductResponse> responses = products.stream()
				.map(this::toResponse)
				.toList();
		log.info("Completed getByStatus, retrieved {} products", responses.size());
		return responses;
	}
	
	/**
	 * Converts a Product entity to ProductResponse DTO.
	 * @param productEntity the product entity
	 * @return the product response DTO
	 */
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
	
	/**
	 * Converts a ProductRequest DTO to Product entity.
	 * @param productRequest the product request DTO
	 * @return the product entity
	 */	
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
