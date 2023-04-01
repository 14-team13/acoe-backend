package com.aluminium.acoe.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {

    @Schema(description = "권한명", example = "ROLE_USER")
    private String authorityName;
}