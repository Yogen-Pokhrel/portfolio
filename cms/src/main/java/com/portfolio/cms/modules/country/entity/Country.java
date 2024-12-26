package com.portfolio.cms.modules.country.entity;

import com.portfolio.core.common.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@ToString
public class Country implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Country name cannot be blank")
    @Column(nullable = false, length = 60)
    private String name;

    @NotBlank(message = "Country iso code cannot be blank")
    @Length(min = 2, max = 2, message = "ISO code should be of 2 characters")
    @Column(unique = true, nullable = false, length = 5)
    private String isoCode;

    @Column(length = 20)
    private String shortName;

    @Column(length = 20)
    private String displayName;

    @NotBlank(message = "Phone code cannot be blank")
    @Length(max = 5, message = "Phone code should be less than 5 characters")
    @Column(length = 5)
    private String phoneCode;

    @Column(length = 20)
    private String flag;
}
