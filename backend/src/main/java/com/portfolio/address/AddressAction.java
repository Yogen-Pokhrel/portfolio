package com.portfolio.address;

import com.portfolio.common.Permission;
import lombok.Getter;

@Getter
public enum AddressAction implements Permission {
    CREATE("createAddress"),
    READ("readAddress"),
    UPDATE("updateAddress"),
    DELETE("deleteAddress");

    private final String action;
    private final String domain;

    AddressAction(String action){
        this.action = action;
        this.domain = "AddressController";
    }
}
