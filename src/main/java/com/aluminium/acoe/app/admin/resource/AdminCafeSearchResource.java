package com.aluminium.acoe.app.admin.resource;

import com.aluminium.acoe.common.resource.PageResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Search Paging Resource
 */
@Data
public class AdminCafeSearchResource extends PageResource {
    @Schema(description = "카페명")
    private String cafeNm;

    @Schema(description = "도로명주소")
    private String roadAddr;

    @Schema(description = "텀블러할인금액")
    private Long discountAmt;

    @Schema(description = "메뉴등록여부")
    private Boolean menuYn;
}