package com.portfolio.auth.user;

import com.portfolio.auth.common.Permission;
import lombok.Getter;

@Getter
public enum UserAction implements Permission {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    READ("read");

    private final String action;
    private final String domain;
    UserAction(String action) {
        this.action = action;
        this.domain = "UserController";
    }
}
