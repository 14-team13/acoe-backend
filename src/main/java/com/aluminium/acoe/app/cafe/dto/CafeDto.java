package com.aluminium.acoe.app.cafe.dto;

import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.common.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link Cafe} entity
 */
@Data
@Builder
public class CafeDto extends BaseDto {
    @Schema(description = "카페ID")
    private Long cafeId;

    @Schema(description = "카페명")
    private String cafeNm;

    @Schema(description = "지역코드")
    private Long areaCd;

    @Schema(description = "영업상태코드 1(영업/정상), 3(폐업)")
    private Long trdStateCd;

    @Schema(description = "영업상태상세코드 1(영업), 2(폐업)")
    private Long dtlStateCd;

    @Schema(description = "전화번호")
    private String telNo;

    @Schema(description = "도로명주소")
    private String roadAddr;

    @Schema(description = "도로명우편번호")
    private String roadPostNo;

    @Schema(description = "x좌표")
    private Long x;

    @Schema(description = "y좌표")
    private Long y;

    @Schema(description = "원참조번호(관리번호)")
    private String refNo;

    @Schema(description = "메뉴목록")
    private List<MenuDto> menuList;

}