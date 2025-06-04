package org.example.ecommerceproject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void JavaMailSender(String email,String otp){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("yashrajladhani9@gmail.com");
            helper.setTo(email);
            helper.setSubject("Verification OTP");
            helper.setText("Your OTP Verification Code: " + otp, true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            System.out.println("Error in sending email" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void emailOrderConfirmation(String email, String username, String orderNumber, String totalPrice) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yashrajladhani9@gmail.com");
        message.setTo(email);
        message.setSubject("Order Confirmation");
        message.setText("Dear " + username + ",\n" +
                "Your order with id " + orderNumber + " has been placed successfully.\n" +
                "Total Price: " + totalPrice + "\n" +
                "Thank you for shopping with us!");
        javaMailSender.send(message);

    }
}
