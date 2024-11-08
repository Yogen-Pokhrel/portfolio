package com.portfolio.role;

public enum RolePermissionSet {
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
