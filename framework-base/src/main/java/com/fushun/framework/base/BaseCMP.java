package com.fushun.framework.base;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@MappedSuperclass
@Data
public abstract class BaseCMP implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime(3) DEFAULT NULL comment '创建时间'")
    protected LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime(3) DEFAULT NULL comment '更新时间'")
    protected LocalDateTime updatedAt;

    @PrePersist
    protected void setPreInsert() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void setPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
