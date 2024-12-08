package com.portfolio.auth.blogs.entity;

import com.portfolio.auth.common.BaseEntity;
import com.portfolio.auth.common.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Blog extends BaseEntity implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;
}
