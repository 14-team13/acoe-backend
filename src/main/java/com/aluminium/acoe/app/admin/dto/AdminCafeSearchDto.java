package com.aluminium.acoe.app.admin.dto;

import com.aluminium.acoe.common.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A DTO for the Cafe entity
 */
@Data
public class AdminCafeSearchDto extends BaseDto {
    @Schema(description = "카페명")
    private String cafeNm;

    @Schema(description = "도로명주소")
    private String roadAddr;

    @Schema(description = "텀블러할인금액")
    private Long discountAmt;

    @Schema(description = "메뉴등록여부")
    private Boolean menuYn;

}