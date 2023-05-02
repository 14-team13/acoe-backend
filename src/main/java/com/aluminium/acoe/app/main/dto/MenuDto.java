package com.aluminium.acoe.app.main.dto;

import com.aluminium.acoe.common.dto.BaseDto;
import lombok.Data;

/**
 * A DTO for the {@link com.aluminium.acoe.app.main.entity.Menu} entity
 */
@Data
public class MenuDto extends BaseDto {
    private Long menuId;
    private String menuNm;
    private Long price;
}