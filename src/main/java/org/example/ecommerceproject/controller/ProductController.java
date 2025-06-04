package org.example.ecommerceproject.controller;

import jakarta.servlet.http.HttpSession;
import org.example.ecommerceproject.dto.ProductRequest;
import org.example.ecommerceproject.entity.Product;
import org.example.ecommerceproject.entity.Role;
import org.example.ecommerceproject.repository.ProductRepository;
import org.example.ecommerceproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product/add")
    public ResponseEntity<String> addProduct(HttpSession session, @RequestBody ProductRequest productRequest) {
        Role role = (Role) session.getAttribute("role");
        
        if (role == null || !Role.ADMIN.equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                               .body("Only admin can add product");
        }
        return ResponseEntity.ok(productService.addProduct(productRequest));
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<String> updateProductById(HttpSession session, 
                                                  @RequestBody ProductRequest productRequest,
                                                  @PathVariable Long id) {
        Role role = (Role) session.getAttribute("role");

        if (role == null || !Role.ADMIN.equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                               .body("Only admin can update product");
        }

        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                               .body("Product not found with id: " + id);
        }

        return ResponseEntity.ok(productService.updateProductById(productRequest, id));
    }


    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<String> deleteProductById(HttpSession session, @PathVariable Long id) {
        Role role = (Role) session.getAttribute("role");

        if (role == null || !Role.ADMIN.equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                               .body("Only admin can delete product");
        }
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}