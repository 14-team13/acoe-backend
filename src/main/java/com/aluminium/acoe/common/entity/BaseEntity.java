package com.aluminium.acoe.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {
    @Column(nullable = true, length = 4000)
    private String rmk;
    @CreatedBy
    @Column(nullable = true, length = 30, updatable = false)
    private String regrId;
    @CreatedDate
    @Column(nullable = true, updatable = false)
    private LocalDateTime regDttm;
    @CreatedBy
    @LastModifiedBy
    @Column(length = 30)
    private String modrId;
    @CreatedDate
    @LastModifiedDate
    @Column
    private LocalDateTime modDttm;
}
