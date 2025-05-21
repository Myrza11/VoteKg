package org.example.votekg.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VoterUser extends User {
    private final String role = "VOTER_USER";

    @Override
    public String getRole() {
        return role;
    }
}
