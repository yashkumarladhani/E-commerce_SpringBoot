package org.example.ecommerceproject.service;


import jakarta.servlet.http.HttpSession;
import org.example.ecommerceproject.dto.LoginRequest;
import org.example.ecommerceproject.dto.NewOtpRequest;
import org.example.ecommerceproject.dto.SignupRequest;
import org.example.ecommerceproject.dto.VerifyOtp;
import org.example.ecommerceproject.entity.Otp;
import org.example.ecommerceproject.entity.Role;
import org.example.ecommerceproject.entity.User;
import org.example.ecommerceproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public String Signup(SignupRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Username already exists";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.CUSTOMER);

        String otpCode = String.format("%06d", (int) (Math.random() * 999999));
        Otp otp = new Otp();
        otp.setOtp(otpCode);
        otp.setEmail(request.getEmail());
        otp.setExpiryDate(LocalDateTime.now().plusMinutes(3));
        otp.setVerified(false);
        otp.setUser(user);

        List<Otp> otps = new ArrayList<>();
        otps.add(otp);
        user.setOtps(otps);

        userRepository.save(user);
        emailService.JavaMailSender(request.getEmail(),otpCode);
        return "User registered successfully";



    }


   public String verifyOtp(VerifyOtp verifyOtp){
        Optional<User> userOtp = userRepository.findByEmail(verifyOtp.getEmail());
         if (userOtp.isEmpty()){
             return "User not found";
         }

         User user = userOtp.get();

         for (Otp otpCode : user.getOtps()){
             if(!otpCode.isVerified() && otpCode.getOtp().equals(verifyOtp.getOtp())){
                 if (otpCode.getExpiryDate().isAfter(LocalDateTime.now().plusMinutes(3))){
                     return "Otp expired";
                 }else {
                     otpCode.setVerified(true);
                     userRepository.save(user);
                     return "Otp verified successfully";
                 }

             }

         }

         return "Invalid OTP";
    }



    public String login(LoginRequest request, HttpSession session){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null || !user.getPassword().equals(request.getPassword())){
            return "Invalid username or password";

        }

        session.setAttribute("role", user.getRole());
        session.setAttribute("username", user.getUsername());

        if (user.getRole() == Role.ADMIN){
            return "Admin login successful";
        }

        boolean hasValidOtp = user.getOtps().stream()
                .anyMatch(Otp::isVerified);

        if (hasValidOtp){
            return "Login Successful";
        }else {

        return "No valid OTP found";
        }
    }


    public String otpResend(NewOtpRequest request){

        Optional<User> userOtp = userRepository.findByEmail(request.getEmail());
        if (userOtp.isEmpty()){
            return "User not found";
        }

        User user = userOtp.get();
        String otpCode = String.format("%06d", (int) (Math.random() * 999999));
        Otp otp = new Otp();
        otp.setOtp(otpCode);
        otp.setEmail(request.getEmail());
        otp.setExpiryDate(LocalDateTime.now().plusMinutes(3));
        otp.setVerified(false);
        otp.setUser(user);

        user.getOtps().add(otp);
        userRepository.save(user);
        emailService.JavaMailSender(request.getEmail(),otpCode);
        return "Otp resent successfully";
    }

}
