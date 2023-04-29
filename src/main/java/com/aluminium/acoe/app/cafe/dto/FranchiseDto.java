package com.aluminium.acoe.app.cafe.dto;

import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.common.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;


/**
 * A DTO for the {@link Cafe} entity
 */
@Data
public class FranchiseDto extends BaseDto {
    @Schema(description = "프랜차이즈ID")
    private Long franchiseId;

    @Schema(description = "프랜차이즈명")
    private String franchiseNm;

    @Schema(description = "할인금액")
    private Long discountAmt;

    @Schema(description = "로고이미지(Base64)")
    private String logoImg;

    @Schema(description = "사용여부")
    private Boolean useYn;

    @Schema(description = "메뉴목록")
    private List<MenuDto> menuList;

}