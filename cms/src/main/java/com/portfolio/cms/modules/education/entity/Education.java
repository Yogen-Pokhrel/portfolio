package com.portfolio.cms.modules.education.entity;


import com.portfolio.core.common.BaseEntity;
import com.portfolio.core.common.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Education extends BaseEntity implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "user Id cannot be null")
    @Column(nullable = false)
    private UUID userId;

    @Column(length = 100)
    private String university;

    @Column(length = 100)
    private String degree;

    @Column(length = 100)
    private String fieldOfStudy;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 20)
    private String grade;
    private boolean isGraduated;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

}
