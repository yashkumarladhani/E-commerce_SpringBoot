package org.example.ecommerceproject;

import org.example.ecommerceproject.entity.Otp;
import org.example.ecommerceproject.entity.Role;
import org.example.ecommerceproject.entity.User;
import org.example.ecommerceproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceProjectApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CommandLineRunner setAdmin(){
        return args -> {
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                User user = new User();
                user.setUsername("Admin");
                user.setEmail("admin@example.com");
                user.setPassword("admin");
                user.setRole(Role.ADMIN);
                userRepository.save(user);

                Otp otp = new Otp();
                otp.setEmail("admin@example.com");
                otp.setOtp("111111");
                otp.setVerified(true);
                otp.setExpiryDate(null);
                otp.setUser(user);
                user.getOtps().add(otp);

                userRepository.save(user);

                System.out.println("Admin created successfully");
            }
        };
    }

}
