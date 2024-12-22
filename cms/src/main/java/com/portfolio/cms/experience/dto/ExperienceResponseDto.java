package com.portfolio.cms.experience.dto;

import com.portfolio.cms.experience.entity.EmploymentType;
import com.portfolio.cms.experience.entity.LocationType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceResponseDto {
    private String jobTitle;
    private String company;
    private String description;
    private String location;
    private LocationType locationType;
    private EmploymentType employmentType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrentJob;
}