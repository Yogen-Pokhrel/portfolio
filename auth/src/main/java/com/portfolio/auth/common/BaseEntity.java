package com.portfolio.auth.common;

import com.portfolio.auth.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    private Boolean isDeleted = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedOn;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User addedBy;

    private LocalDateTime updatedOn;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User updatedBy;

    private LocalDateTime deletedOn;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User deletedBy;

    @PrePersist
    protected void onCreate() {
        addedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
    }
}
