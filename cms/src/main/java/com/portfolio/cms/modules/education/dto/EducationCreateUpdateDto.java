package com.portfolio.cms.modules.education.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.portfolio.cms.modules.experience.entity.EmploymentType;
import com.portfolio.core.helpers.validators.annotations.DateGreaterThan;
import com.portfolio.core.helpers.validators.annotations.DateGreaterThanToday;
import com.portfolio.core.helpers.validators.annotations.ErrorMessage;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@DateGreaterThan(target = "endDate", compareTo = "startDate", message = "End date must be greater than start date")
public class EducationCreateUpdateDto {

    @NotNull(message = "University name cannot be null")
    private String university;
    private String degree;

    private String fieldOfStudy;
    private int testVal;

    @DateGreaterThanToday
    private LocalDate startDate;
    private LocalDate endDate;

    private String grade;
    private boolean graduated;
    private String description;
    private EmploymentType employmentType;

    @JsonIgnore
    private UUID userId;
}
