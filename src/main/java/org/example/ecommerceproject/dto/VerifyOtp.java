package org.example.ecommerceproject.dto;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtp {

    private String email;
    private String otp;
}
