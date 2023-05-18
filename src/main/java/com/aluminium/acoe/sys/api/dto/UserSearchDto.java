package com.aluminium.acoe.sys.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSearchDto {
    @Schema(description = "ID", example = "")
    private String userId;

    @Schema(description = "이름", example = "")
    private String username;

    @Schema(description = "E-mail", example = "")
    private String email;
}
