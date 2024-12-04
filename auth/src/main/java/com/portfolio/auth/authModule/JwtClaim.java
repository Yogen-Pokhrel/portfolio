package com.portfolio.auth.authModule;

import lombok.Getter;

@Getter
public enum JwtClaim {
    USER_ID("userId"),
    ROLES("roles"),
    EMAIL("sub"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName");

    private final String key;

    JwtClaim(String key) {
        this.key = key;
    }

}
