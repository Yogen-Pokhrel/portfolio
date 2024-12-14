package com.portfolio.account.address.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateAddressDto {
    @NotBlank(message = "Street is mandatory")
    private String street;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @Pattern(regexp = "^\\d{5}$", message = "Zipcode must contain exactly 5 digits.")
    private String zipcode;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @Positive(message = "Please provide user id")
    private int userId;
}
