package com.aluminium.acoe.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "JWT 토큰")
    private String token;
}