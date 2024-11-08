package com.portfolio.user.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDetailDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String phone;
    private LocalDate addedOn;
    private SimpleUserDto addedBy;
}
