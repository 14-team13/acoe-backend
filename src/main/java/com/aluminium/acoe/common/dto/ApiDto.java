package com.aluminium.acoe.common.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * A DTO for the {@link com.aluminium.acoe.common.entity.baseEntity} entity
 */
@Data
public class ApiDto implements Serializable {

    private String url;
    private String method;

    private String contentType;

}