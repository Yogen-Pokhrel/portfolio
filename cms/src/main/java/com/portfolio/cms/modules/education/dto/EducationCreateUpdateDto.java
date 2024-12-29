package com.portfolio.cms.modules.education.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.cms.modules.experience.entity.EmploymentType;
import com.portfolio.core.helpers.validators.annotations.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@DateGreaterThan(target = "endDate", compareTo = "startDate", message = "End date must be greater than start date")
@RequiredIf(target = {"endDate"}, dependsOn = "graduated", requiredWhenValue = "true")
public class EducationCreateUpdateDto {

    @NotEmpty(message = "University name cannot be empty")
    private String university;

    @NotEmpty
    private String degree;

    private String fieldOfStudy;

    @PastDate
    private LocalDate startDate;
    private LocalDate endDate;

    private String grade;
    private boolean graduated;
    private String description;
    private EmploymentType employmentType;

    @JsonIgnore
    private UUID userId;
}
