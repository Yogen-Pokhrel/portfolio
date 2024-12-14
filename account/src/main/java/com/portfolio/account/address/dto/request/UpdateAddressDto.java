package com.portfolio.account.address.dto.request;

import lombok.Data;

@Data
public class UpdateAddressDto {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
