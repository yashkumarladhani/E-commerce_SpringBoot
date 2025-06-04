package org.example.ecommerceproject.controller;

import jakarta.servlet.http.HttpSession;
import org.example.ecommerceproject.dto.OrderItemList;
import org.example.ecommerceproject.entity.OrderItem;
import org.example.ecommerceproject.entity.Role;
import org.example.ecommerceproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderItems/list")
    public ResponseEntity<String> getOrderItems(HttpSession session) {
        Role role = (Role) session.getAttribute("role");
        if (role == null || !Role.ADMIN.equals(role)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");


        }
        List<OrderItemList> items = orderService.getAllOrderItems();
        return ResponseEntity.status(HttpStatus.OK).body(items.toString());
    }
}
