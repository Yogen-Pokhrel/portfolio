package com.portfolio.cms.address.dto.response;

import lombok.Data;

@Data
public class AddressResponseDto {
    private int id;

    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
