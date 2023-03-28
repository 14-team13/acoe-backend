package com.aluminium.acoe.com.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 토큰 Response DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    @ApiModelProperty(
            value = "JWT 토큰",
            name = "token",
            dataType = "String"
    )
    private String token;
}