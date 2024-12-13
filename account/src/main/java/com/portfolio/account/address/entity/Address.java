package com.portfolio.account.address.entity;

import com.portfolio.account.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
