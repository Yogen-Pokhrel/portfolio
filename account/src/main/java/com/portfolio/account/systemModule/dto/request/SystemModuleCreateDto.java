package com.portfolio.auth.systemModule.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SystemModuleCreateDto {

    @NotBlank(message = "Module name cannot be blank")
    private String name;

    private String description;

    @NotBlank(message = "Module slug cannot be blank")
    private String slug;
}
