package org.fasttrackit.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.ecommerce.domain.Product;
import org.fasttrackit.ecommerce.exception.ResourceNotFoundException;
import org.fasttrackit.ecommerce.repository.ProductRepository;
import org.fasttrackit.ecommerce.transfer.CreateProductRequest;
import org.fasttrackit.ecommerce.transfer.GetProductsRequest;
import org.fasttrackit.ecommerce.transfer.UpdateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);


    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(CreateProductRequest request) {
        LOGGER.info("Creating product {}", request);

        Product product = objectMapper.convertValue(request, Product.class);


        return productRepository.save(product);
    }

    public Product getProduct(long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving product {}", id);


        return productRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product " + id + " does not exist."));
    }

    public Product updateProduct(long id, UpdateProductRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating product {} with {}", id, request);

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);
    }

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving products {}", request);

        if (request.getPartialName() != null &&
                request.getMinimumQuantity() != null) {
            return productRepository.findByNameContainingAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinimumQuantity(),
                    pageable);
        } else if (request.getPartialName() != null) {
            return productRepository.findByNameContaining(
                    request.getPartialName(), pageable);
        }

        return productRepository.findAll(pageable);
    }
}
