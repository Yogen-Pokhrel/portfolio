package com.portfolio.cms.experience.entity;

import lombok.Getter;

@Getter
public enum EmploymentType {
    fullTime("Full-Time"),
    partTime("Part-Time"),
    selfEmployed("Self-employed"),
    freelance("Freelance"),
    contract("Contract"),
    internship("Internship"),
    apprenticeship("Apprenticeship"),
    seasonal("Seasonal");

    private final String value;
    EmploymentType(String type){
        this.value = type;
    }

}
