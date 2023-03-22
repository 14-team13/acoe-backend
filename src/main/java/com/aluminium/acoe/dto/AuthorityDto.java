package com.aluminium.acoe.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {

    @ApiModelProperty(
            value = "권한명",
            name = "authorityName",
            dataType = "String",
            example = "ROLE_USER, ROLE_ADMIN"
    )
    private String authorityName;
}