package org.example.votekg.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDTO {
    private String title;
    private String content;
    private MultipartFile image;
}
