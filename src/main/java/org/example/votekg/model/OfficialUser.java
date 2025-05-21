package org.example.votekg.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OfficialUser extends User {
    private String position;
    private String organization;
    private byte[] certificate;
    private final String role = "OFFICIAL_USER";
    private boolean isVerified;

    @Override
    public String getRole() {
        return role;
    }
}
