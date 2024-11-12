package com.portfolio.address;

import com.portfolio.common.PermissionSet;

public enum AddressPermissionSet  implements PermissionSet {
    CREATE("createAddress"),
    READ("readAddress"),
    UPDATE("updateAddress"),
    DELETE("deleteAddress");

    private final String permission;
    AddressPermissionSet(String permission){
        this.permission = permission;
    }

    public String get() {
        return this.permission;
    }
}
