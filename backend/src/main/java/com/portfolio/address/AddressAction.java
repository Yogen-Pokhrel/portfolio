package com.portfolio.address;

import com.portfolio.common.PermissionSet;
import lombok.Getter;

@Getter
public enum AddressPermissionSet  implements PermissionSet {
    CREATE("createAddress"),
    READ("readAddress"),
    UPDATE("updateAddress"),
    DELETE("deleteAddress");

    private final String action;
    private final String domain;

    AddressPermissionSet(String action){
        this.action = action;
        this.domain = "AddressController";
    }
}
