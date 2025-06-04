package org.example.ecommerceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartRequest {

    private Long userId;
    private String productName;
    private Long productId;
    private int quantity;

}
