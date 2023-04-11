package com.aluminium.acoe.common.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.aluminium.acoe.common.entity.baseEntity} entity
 */
@Data
public class BaseDto implements Serializable {
    private String rmk;
    private String regrId;
    private LocalDateTime regDttm;
    private String modrId;
    private LocalDateTime modDttm;
}