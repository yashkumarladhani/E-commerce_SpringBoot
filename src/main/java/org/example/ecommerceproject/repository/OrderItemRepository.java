package org.example.ecommerceproject.repository;

import org.example.ecommerceproject.entity.Order;
import org.example.ecommerceproject.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
