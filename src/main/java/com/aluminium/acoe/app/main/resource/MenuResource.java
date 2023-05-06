package com.aluminium.acoe.app.main.resource;
import com.aluminium.acoe.common.dto.BaseDto;
import lombok.Data;

/**
 * A Resource for the Menu entity
 */
@Data
public class MenuResource extends BaseDto {
    private Long menuId;
    private String menuNm;
    private Long price;
}