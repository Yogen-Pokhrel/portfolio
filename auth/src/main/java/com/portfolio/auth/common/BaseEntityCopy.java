package com.portfolio.auth.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntityCopy {

    private Boolean isDeleted = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedOn;

    @Column(nullable = false)
    private UUID addedBy;

    private LocalDateTime updatedOn;
    private UUID updatedBy;

    private LocalDateTime deletedOn;
    private UUID deletedBy;

    @PrePersist
    protected void onCreate() {
        addedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
    }
}
