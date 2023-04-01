package com.aluminium.acoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 회원 가입 정보 DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @NotNull
    @Size(min = 3, max = 50)
    @Schema(description = "사용자명", example = "acoe")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    @Schema(description = "비밀번호", example = "")
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    @Schema(description = "닉네임", example = "ACOE_USER")
    private String nickname;
}