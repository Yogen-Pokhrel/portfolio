package com.portfolio.auth.common;

import com.portfolio.auth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    private Boolean isDeleted = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedOn;
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
