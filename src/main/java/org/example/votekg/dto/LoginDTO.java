package org.example.votekg.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;
    // геттеры/сеттеры
}
