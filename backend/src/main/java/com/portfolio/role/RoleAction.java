package com.portfolio.role;

import com.portfolio.common.Permission;
import lombok.Getter;

@Getter
public enum RolePermissionSet implements Permission {
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private final String action;
    private final String domain;
    RolePermissionSet(String action){
        this.action = action;
        this.domain = RoleController.class.getSimpleName();
    }

}
