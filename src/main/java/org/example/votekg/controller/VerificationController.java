package org.example.votekg.controller;

import org.example.votekg.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerificationController {

    @Autowired
    private EmailService emailService;


    @GetMapping
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok("Email подтвержден, теперь вы можете войти    n/n   " + emailService.verifyEmail(token));

    }

    @GetMapping("/exists")
    public boolean isEmailExist(@RequestParam String email) {
        return emailService.isEmailExist(email);
    }
}
