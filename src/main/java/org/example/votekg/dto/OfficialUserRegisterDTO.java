package org.example.votekg.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.votekg.model.District;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Data
public class OfficialUserRegisterDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String idPassword;

    private MultipartFile userImage;

    private MultipartFile userPassportPhoto;

    private MultipartFile userUpsidePassportPhoto;
    private String organization;
    private String position;
    private MultipartFile certificate;
    private District district;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
