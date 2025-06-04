package org.example.ecommerceproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId;

    @Column(length = 128)
    private String email;

    @Column(length = 6)
    private String otp;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "verified")
    private boolean verified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}