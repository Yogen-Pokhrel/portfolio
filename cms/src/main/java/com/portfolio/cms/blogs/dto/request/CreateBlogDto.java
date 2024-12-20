package com.portfolio.cms.blogs.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBlogDto {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
}
