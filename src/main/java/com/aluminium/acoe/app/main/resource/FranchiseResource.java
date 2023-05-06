package com.aluminium.acoe.app.main.resource;
import com.aluminium.acoe.common.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * A Resource for the Franchise entity
 */
@Data
public class FranchiseResource extends BaseDto {
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
    private List<MenuResource> menuList;

}