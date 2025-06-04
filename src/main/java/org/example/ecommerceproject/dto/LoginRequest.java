package org.example.ecommerceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Fallback;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;
}
