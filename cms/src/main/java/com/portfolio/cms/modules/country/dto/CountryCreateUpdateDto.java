package com.portfolio.cms.modules.country.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CountryCreateUpdateDto {
    @NotBlank(message = "Country name cannot be blank")
    private String name;

    @NotBlank(message = "Country ISO code cannot be blank")
    @Length(min = 2, max = 2, message = "ISO code should be of 2 characters")
    private String isoCode;

    private String shortName;
    private String displayName;

    @NotBlank(message = "Phone code cannot be blank")
    @Length(max = 5, message = "Phone code should be less than 5 characters")
    private String phoneCode;
    private String flag;
}
