package org.example.ecommerceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyCartItemRequest {

    private Long userId;
    private Long productId;

}
