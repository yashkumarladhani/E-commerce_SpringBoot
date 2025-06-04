package org.example.ecommerceproject.controller;

import org.example.ecommerceproject.dto.BuyCartItemRequest;
import org.example.ecommerceproject.dto.BuyProductRequest;
import org.example.ecommerceproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/buy/cart")
    public ResponseEntity<String> buyFromCart(@RequestBody BuyCartItemRequest request){
        orderService.buyFromCart(request.getUserId(), request.getProductId());
        return ResponseEntity.ok("Order placed successfully");
    }

    @PostMapping("/buy/product")
    public ResponseEntity<String> BuyNow(@RequestBody BuyProductRequest request){
        orderService.BuyProduct(request);
        return ResponseEntity.ok("Order placed successfully");
    }
}
