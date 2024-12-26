package com.portfolio.cms.modules.blogs.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogCreateUpdateDto {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
}
