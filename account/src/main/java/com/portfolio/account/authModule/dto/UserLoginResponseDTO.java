package com.portfolio.auth.authModule.dto;

import com.portfolio.auth.role.dto.RoleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private List<RoleResponseDto> roles;
}
