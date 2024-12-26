package com.portfolio.cms.modules.experience.entity;

import lombok.Getter;

@Getter
public enum LocationType {
    onSite("On-site"),
    hybrid("Hybrid"),
    remote("Remote");

    private final String value;
    LocationType(String name) {
        this.value = name;
    }
}
