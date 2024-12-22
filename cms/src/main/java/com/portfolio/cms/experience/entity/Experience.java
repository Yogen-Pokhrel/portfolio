package com.portfolio.cms.experience.entity;

import com.portfolio.core.common.BaseEntity;
import com.portfolio.core.common.Identifiable;
import com.portfolio.core.helpers.validators.ValidEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Experience extends BaseEntity implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "user Id cannot be null")
    private UUID userId;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;
    private String description;

    @NotNull(message = "Start date must be provided")
    private LocalDate startDate;

    private LocalDate endDate;
    private boolean isCurrentJob;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Enumerated(EnumType.STRING)
    @ValidEnum(required = true, message = "Employment type must be one of: {values}", enumClass = EmploymentType.class)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @ValidEnum(required = true, message = "LocationType type must be one of: {values}", enumClass = LocationType.class)
    private LocationType locationType;

}
