package com.portfolio.account.user.dto.response;

import lombok.Data;

@Data
public class SimpleUserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
