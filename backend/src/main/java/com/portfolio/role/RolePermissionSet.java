package com.portfolio.role;

import com.portfolio.common.PermissionSet;

public enum RolePermissionSet implements PermissionSet {
    CREATE("createRole"),
    READ("readRole"),
    UPDATE("updateRole"),
    DELETE("deleteRole");

    private final String permission;
    RolePermissionSet(String permission){
        this.permission = permission;
    }

    public String get() {
        return this.permission;
    }
}
