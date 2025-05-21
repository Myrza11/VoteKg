package org.example.votekg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.votekg.model.District;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Data
public class OfficialUserRegisterDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String idPassword;

    @NotNull(message = "Image file must not be null")
    private MultipartFile userImage;

    @NotNull(message = "Image file must not be null")
    private MultipartFile userPassportPhoto;

    @NotNull(message = "Image file must not be null")
    private MultipartFile userUpsidePassportPhoto;
    @NotBlank
    private String organization;
    @NotBlank
    private String position;
    @NotNull(message = "Image file must not be null")
    private MultipartFile certificate;
    @NotBlank
    private District district;
    @NotBlank
    private LocalDate dateOfBirth;
}
