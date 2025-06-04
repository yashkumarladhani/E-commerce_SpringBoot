package org.example.ecommerceproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceproject.dto.CartRequest;
import org.example.ecommerceproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/items")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest request){
        cartService.addToCart(request);
        return ResponseEntity.ok("Added to cart successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartItems(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}
