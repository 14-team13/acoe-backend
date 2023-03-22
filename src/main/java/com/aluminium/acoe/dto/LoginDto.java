package com.aluminium.acoe.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(
            value = "ID",
            name = "username",
            dataType = "String",
            example = "acoe"
    )
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    @ApiModelProperty(
            value = "PW",
            name = "password",
            dataType = "String"
    )
    private String password;
}