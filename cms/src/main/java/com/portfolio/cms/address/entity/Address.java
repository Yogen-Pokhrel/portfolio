package com.portfolio.cms.address.entity;

import com.portfolio.core.common.Identifiable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Entity
public class Address implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    @Column(unique=true)
    private UUID userId;
}
