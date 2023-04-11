package com.aluminium.acoe.sys.dto;

import com.aluminium.acoe.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 계정 정보 DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

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

    @Schema(description = "권한")
    private Set<AuthorityDto> authorities;

    public static UserDto from(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorities(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}