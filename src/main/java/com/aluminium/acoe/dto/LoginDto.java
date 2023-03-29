package com.aluminium.acoe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 로그인 DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    @Schema(description = "사용자명", example = "acoe")
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    @Schema(description = "비밀번호", example = "")
    private String password;
}