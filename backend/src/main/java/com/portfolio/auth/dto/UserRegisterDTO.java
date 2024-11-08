package com.portfolio.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.helpers.validators.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDTO {
    @NotBlank(message = "First name is mandatory")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must be less than or equal to 50 characters")
    private String lastName;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    private String email;

    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).{6,60}$", message = "Enter a valid password. The password must have at least one lowercase letter, one uppercase letter, one number, and one special character, with a minimum length of 6 characters and a maximum of 60 characters.")
    private String password;
}
