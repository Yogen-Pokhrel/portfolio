package com.portfolio.auth.common;

public interface Identifiable<PrimaryKey> {
    void setId(PrimaryKey id);
}