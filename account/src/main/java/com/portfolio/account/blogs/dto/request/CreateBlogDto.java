package com.portfolio.account.blogs.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBlogDto {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
}
