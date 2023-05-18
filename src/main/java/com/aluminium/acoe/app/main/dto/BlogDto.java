package com.aluminium.acoe.app.main.dto;

import com.aluminium.acoe.common.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class BlogDto extends BaseDto {
    @Schema(description = "제목")
    private String title;

    @Schema(description = "링크")
    private String link;

    @Schema(description = "설명")
    private String desc;

    @Schema(description = "블로그명")
    private String blogNm;

    @Schema(description = "블로그링크")
    private String blogLink;

    @Schema(description = "포스팅날짜")
    private String postDt;
}