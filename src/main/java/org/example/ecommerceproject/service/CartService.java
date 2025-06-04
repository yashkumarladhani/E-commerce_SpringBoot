package org.example.ecommerceproject.service;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceproject.dto.CartRequest;
import org.example.ecommerceproject.entity.Cart_Items;
import org.example.ecommerceproject.entity.Product;
import org.example.ecommerceproject.entity.User;
import org.example.ecommerceproject.repository.CartItemsRepository;
import org.example.ecommerceproject.repository.ProductRepository;
import org.example.ecommerceproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;


    public void addToCart(CartRequest request){

     User user = userRepository.findById(request
             .getUserId()).orElseThrow(() ->
             new IllegalArgumentException("User not found"));

        if (!productRepository.existsById(request.getProductId())){
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() ->
            new IllegalArgumentException("Product not found"));

        if (product.getStock() < request.getQuantity()){
            throw new IllegalArgumentException("Stock is not enough");
        }

        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        Cart_Items cartItem = new Cart_Items();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());


        cartItemsRepository.save(cartItem);

    }

    public List<CartRequest> getCartItems(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("User not found"));

        List<Cart_Items> cartItems = cartItemsRepository.findByUser(user);

        return cartItems.stream()
                .map(cartItem -> new CartRequest(
                        cartItem.getUser().getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getId(),
                        cartItem.getQuantity()

                ))
                .toList();


    }
}
