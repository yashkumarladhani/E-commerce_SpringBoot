package org.example.ecommerceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemList {

    private Long order_id;
    private String productName;
    private int quantity;
    private Double productPrice;

}
