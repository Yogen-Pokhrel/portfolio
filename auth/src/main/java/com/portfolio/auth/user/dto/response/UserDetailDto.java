package com.portfolio.auth.user.dto.response;

import com.portfolio.auth.role.dto.RoleResponseDto;
import com.portfolio.auth.user.entity.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
    private List<RoleResponseDto> roles;
    private UserStatus status;
}
