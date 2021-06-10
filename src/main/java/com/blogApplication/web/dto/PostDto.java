package com.blogApplication.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostDto {
    @NotEmpty( message = "title cannot be null")
    private String title;
    @NotEmpty( message = "content cannot be null")
    private String content;

    private MultipartFile imageFile;
}

