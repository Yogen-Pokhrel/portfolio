package com.portfolio.user;

import com.portfolio.common.Permission;
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
