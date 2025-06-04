package org.example.ecommerceproject.service;

import org.example.ecommerceproject.dto.BuyProductRequest;
import org.example.ecommerceproject.dto.OrderItemList;
import org.example.ecommerceproject.entity.*;
import org.example.ecommerceproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemRepository getOrderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public void buyFromCart(Long userId, Long productId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Cart_Items> cartItems = cartItemsRepository.findByUser(user);
        if (cartItems.isEmpty()){
            throw new RuntimeException("Cart is empty!!!");
        }

        double totalPrice = 0;
        for (Cart_Items cartItem : cartItems){
            totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        for (Cart_Items items : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(items.getProduct());
            orderItem.setQuantity(items.getQuantity());
            orderItem.setPrice(items.getProduct().getPrice());
            getOrderItemRepository.save(orderItem);
        }

        cartItemsRepository.deleteAll(cartItems);
        emailService.emailOrderConfirmation(user.getEmail(),user.getUsername(),
                order.getId().toString(),String.valueOf(totalPrice));
        System.out.println("Order placed successfully");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Order placed successfully");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Order placed successfully");
        System.out.println("Order ID: " + order.getId());

    }


    public void BuyProduct(BuyProductRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double total = request.getQuantity() * product.getPrice();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(total);
        orderRepository.save(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(request.getQuantity());
        orderItemRepository.save(orderItem);

        emailService.emailOrderConfirmation(user.getEmail(), user.getUsername(),
                order.getId().toString(), String.valueOf(total));
    }

    public List<OrderItemList> getAllOrderItems(){
        List<OrderItem> orderItems = getOrderItemRepository.findAll();

        return orderItems.stream()
                .map(item -> new OrderItemList(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());
    }


}
