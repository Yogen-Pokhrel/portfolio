package com.portfolio.auth.common;

public enum Roles {
    SUPER_ADMIN("SUPER_ADMIN");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
