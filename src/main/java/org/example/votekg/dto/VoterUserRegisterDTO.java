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
public class VoterUserRegisterDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String idPassport;

    @NotNull(message = "Image file must not be null")
    private MultipartFile userImage;
    @NotNull(message = "Image file must not be null")
    private MultipartFile userPassportPhoto;
    @NotNull(message = "Image file must not be null")
    private MultipartFile userUpsidePassportPhoto;

    private District district;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
