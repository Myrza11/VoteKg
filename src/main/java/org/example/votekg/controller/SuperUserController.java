package org.example.votekg.controller;

import lombok.Data;
import org.example.votekg.model.OfficialUser;
import org.example.votekg.service.SuperUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/superuser")
public class SuperUserController {

    @Autowired
    private SuperUserService superUserService;

    @GetMapping("/official-users")
    public List<OfficialUser> getAllOfficialUsers() {
        return superUserService.officialUsers();
    }

    @GetMapping("/official-users/verified")
    public List<OfficialUser> getVerifiedOfficialUsers() {
        return superUserService.getVerifiedUsers();
    }

    @GetMapping("/official-users/unverified")
    public List<OfficialUser> getUnverifiedOfficialUsers() {
        return superUserService.getUnverifiedUsers();
    }

    @PostMapping("/verify/{userId}")
    public ResponseEntity<String> verifyOfficialUser(@PathVariable Long userId) {
        superUserService.makeOfficialUser(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been verified as an official user.");
    }
}
