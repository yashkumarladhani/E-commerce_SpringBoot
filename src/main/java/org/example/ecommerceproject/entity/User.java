package org.example.ecommerceproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Otp> otps = new ArrayList<>();
}