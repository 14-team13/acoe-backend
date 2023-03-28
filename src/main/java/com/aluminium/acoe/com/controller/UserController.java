package com.aluminium.acoe.com.controller;

import com.aluminium.acoe.com.dto.UserDto;
import com.aluminium.acoe.com.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
     * @param userDto
     * @return
     */
    @PostMapping("/signup")
    @ApiOperation(value = "회원 가입", notes = "Username, Password를 입력 받고 DB에 저장 후 토큰 반환")
    @ApiImplicitParam(name = "userDto", dataType = "UserDto", value = "회원 정보 객체")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    /**
     * SecurityContext에 저장된 접속 계정 정보 확인 API (All User)
     * @param request
     * @return
     */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiOperation(value = "접속한 회원 정보 반환", notes="접속한 회원의 정보를 반환")
    public ResponseEntity<UserDto> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    /**
     * 특정 계정 정보 확인 API (Admin)
     * @param username
     * @return
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "특정 회원 정보 반환", notes="{username}의 회원의 정보를 반환")
    @ApiImplicitParam(name = "username", dataType = "String", value = "회원 PK")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}
