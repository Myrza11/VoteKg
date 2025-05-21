package org.example.votekg.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ElectionCandidateDTO {
    private String name;
    private String description;
    @NotNull
    private MultipartFile image;
}
