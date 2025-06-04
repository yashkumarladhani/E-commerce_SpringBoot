package org.example.ecommerceproject.controller;

import jakarta.servlet.http.HttpSession;
import org.example.ecommerceproject.dto.LoginRequest;
import org.example.ecommerceproject.dto.NewOtpRequest;
import org.example.ecommerceproject.dto.SignupRequest;
import org.example.ecommerceproject.dto.VerifyOtp;
import org.example.ecommerceproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String Signup(@RequestBody SignupRequest request){
        return userService.Signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {
        String loginResponse = userService.login(request, session);

        if (loginResponse.equals("Invalid username or password") || loginResponse.equals("No valid OTP found")){
            return ResponseEntity.status(401).body(loginResponse);
        }else {
            return ResponseEntity.ok(loginResponse);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtp verifyOtp){
        return ResponseEntity.ok(userService.verifyOtp(verifyOtp));

    }


    @PostMapping("/otp-resend")
    public String otpResend(@RequestBody NewOtpRequest request){
        return userService.otpResend(request);
    }
}
