package org.example.ecommerceproject.service;

import org.example.ecommerceproject.dto.ProductRequest;
import org.example.ecommerceproject.entity.Product;
import org.example.ecommerceproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String addProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        productRepository.save(product);
        return "Product added successfully";
    }


public String updateProductById(ProductRequest productRequest, Long id) {
    if (productRequest == null) {
        throw new IllegalArgumentException("Product request cannot be null");
    }
    
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product with id %d not found", id));
    }
    
    // Validate input
    if (productRequest.getPrice() < 0) {
        throw new IllegalArgumentException("Price cannot be negative");
    }
    if (productRequest.getStock() < 0) {
        throw new IllegalArgumentException("Stock cannot be negative");
    }
    
    Product product = optionalProduct.get();
    product.setName(productRequest.getName());
    product.setDescription(productRequest.getDescription());
    product.setPrice(productRequest.getPrice());
    product.setStock(productRequest.getStock());
    
    productRepository.save(product);
    return "Product updated successfully";
}

    public String deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product with id %d not found", id));
        }
        productRepository.delete(product);
        return "Product deleted successfully";

    }
}