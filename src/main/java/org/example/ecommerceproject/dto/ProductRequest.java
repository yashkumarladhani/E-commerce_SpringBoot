package org.example.ecommerceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;
    private String description;
    private double price;
    private int stock;

}
