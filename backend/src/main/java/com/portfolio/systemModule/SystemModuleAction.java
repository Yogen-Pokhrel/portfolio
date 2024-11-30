package com.portfolio.systemModule;

import com.portfolio.common.Permission;
import lombok.Getter;

@Getter
public enum SystemModuleAction implements Permission {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    READ("read");

    private final String action;
    private final String domain;
    SystemModuleAction(String action) {
        this.action = action;
        this.domain = "SystemModuleController";
    }
}
