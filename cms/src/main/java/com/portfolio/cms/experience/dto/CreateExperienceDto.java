package com.portfolio.cms.experience.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.cms.experience.entity.EmploymentType;
import com.portfolio.cms.experience.entity.LocationType;
import com.portfolio.core.helpers.validators.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateExperienceDto {
    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;
    private String description;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @ValidEnum(required = true, message = "LocationType type must be one of: {values}", enumClass = LocationType.class)
    private LocationType locationType;

    @ValidEnum(required = true, message = "Employment type must be one of: {values}", enumClass = EmploymentType.class)
    private EmploymentType employmentType;

    @NotNull(message = "Start date must be provided")
    private LocalDate startDate;

    private LocalDate endDate;
    private boolean isCurrentJob;

    @JsonIgnore
    private UUID userId;
}
