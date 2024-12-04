package com.portfolio.auth.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateRoleDto {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    private String title;
}
