package com.portfolio.auth.role;

import com.portfolio.auth.common.Permission;
import lombok.Getter;

@Getter
public enum RoleAction implements Permission {
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private final String action;
    private final String domain;
    RoleAction(String action){
        this.action = action;
        this.domain = "RoleController";
    }
}