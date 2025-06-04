package org.example.ecommerceproject.repository;

import org.example.ecommerceproject.entity.Cart_Items;
import org.example.ecommerceproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<Cart_Items, Long> {
    List<Cart_Items> findByUser(User user);
}
