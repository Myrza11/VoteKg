package org.example.votekg.controller;

import jakarta.validation.Valid;
import org.example.votekg.config.RevokedTokenService;
import org.example.votekg.dto.LoginDTO;
import org.example.votekg.dto.OfficialUserRegisterDTO;
import org.example.votekg.dto.VoterUserRegisterDTO;
import org.example.votekg.model.User;
import org.example.votekg.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RevokedTokenService revokedTokenService;

    @PostMapping(value = "/register/voterUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerVoter(@ModelAttribute @Valid VoterUserRegisterDTO dto) {
        authService.registerVoterUser(dto);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/register/officialUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerOrganisation(@ModelAttribute @Valid OfficialUserRegisterDTO officialUser) {
        authService.registerOrganisation(officialUser);
        return ResponseEntity.ok("Регистрация успешна. Проверьте email.");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        return ResponseEntity.ok(Collections.singletonMap("token", authService.authenticate(login.getEmail(), login.getPassword())));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        revokedTokenService.revokeToken(jwt);
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return authService.getUserByEmail(email);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return authService.getUserById(id);
    }
}
