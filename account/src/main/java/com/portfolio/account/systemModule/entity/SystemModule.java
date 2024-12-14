package com.portfolio.auth.systemModule.entity;

import com.portfolio.core.common.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemModule implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Module name cannot be null")
    private String name;

    @NotBlank(message = "Slug cannot be null")
    @Column(unique = true)
    private String slug;

    private String description;
}
