package com.aluminium.acoe.controller;

import com.aluminium.acoe.dto.UserDto;
import com.aluminium.acoe.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원 가입 API
     */
    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "Username, Password를 입력 받고 DB에 저장 후 토큰 반환", responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = UserDto.class)))
    })
    @Parameter(name = "userDto", description = "회원 정보 객체", in = ParameterIn.DEFAULT)
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    /**
     * SecurityContext에 저장된 접속 계정 정보 확인 API (All User)
     */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "접속한 회원 정보 반환", description="접속한 회원의 정보를 반환", responses = {
            @ApiResponse(responseCode = "200", description = "회원 정보 반환 성공", content = @Content(schema = @Schema(implementation = UserDto.class)))
    })
    public ResponseEntity<UserDto> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    /**
     * 특정 계정 정보 확인 API (Admin)
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "특정 회원 정보 반환", description="username의 회원의 정보를 반환")
    @Parameter(name = "username", description = "회원 PK", in = ParameterIn.PATH)
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}
