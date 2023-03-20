package com.aluminium.acoe.dto;

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
    private String token;
}