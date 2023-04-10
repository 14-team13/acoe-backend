package com.aluminium.acoe.app.cafe.dto;

import com.aluminium.acoe.common.dto.BaseDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.aluminium.acoe.app.cafe.entity.Menu} entity
 */
@Data
@Builder
public class MenuDto extends BaseDto {
    private Long menuId;
    private String menuNm;
    private Long price;
}