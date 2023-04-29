package com.aluminium.acoe.app.cafe.dto;

import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.app.cafe.entity.Franchise;
import com.aluminium.acoe.common.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;
import java.util.List;

/**
 * A DTO for the {@link Cafe} entity
 */
@Data
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
    private BigDecimal x;

    @Schema(description = "y좌표")
    private BigDecimal y;

    @Schema(description = "텀블러할인금액")
    private Long discountAmt;

    @Schema(description = "원참조번호(관리번호)")
    private String refNo;

    @Schema(description = "앱주문할인가능여부")
    private Boolean appOrderYn;

    @Schema(description = "키오스크할인가능여부")
    private Boolean kioskYn;

    @Schema(description = "사용여부")
    private Boolean useYn;

    @Schema(description = "프랜차이즈")
    private FranchiseDto franchiseDto;

    @Schema(description = "메뉴목록")
    private List<MenuDto> menuList;

}