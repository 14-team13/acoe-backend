package com.aluminium.acoe.sys.api.controller.user;

import com.aluminium.acoe.sys.api.entity.user.User;
import com.aluminium.acoe.sys.api.service.UserService;
import com.aluminium.acoe.sys.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        User user = userService.getUser(principal.getUsername());
        return ApiResponse.success("user", user);
    }
}
