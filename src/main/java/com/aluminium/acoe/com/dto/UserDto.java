package com.aluminium.acoe.com.dto;

import com.aluminium.acoe.com.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 회원 가입 정보 DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(
            value = "ID",
            name = "username",
            dataType = "String",
            example = "acoe"
    )
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    @ApiModelProperty(
            value = "PW",
            name = "password",
            dataType = "String"
    )
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(
            value = "별명",
            name = "nickname",
            dataType = "String",
            example = "acoe"
    )
    private String nickname;

    @ApiModelProperty(
            value = "권한",
            name = "authorities",
            dataType = "Set<AuthorityDto>",
            example = "ROLE_USER, ROLE_ADMIN"
    )
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