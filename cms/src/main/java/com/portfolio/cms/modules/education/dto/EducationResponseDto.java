package com.portfolio.cms.modules.education.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationResponseDto {
    private Integer id;
    private String university;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private String grade;
    private boolean isGraduated;
    private String description;
}
