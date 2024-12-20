package com.portfolio.cms.address;

import com.portfolio.core.common.Permission;
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
