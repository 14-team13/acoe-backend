package com.aluminium.acoe.sys.api.controller.admin;

import com.aluminium.acoe.sys.api.dto.UserSearchDto;
import com.aluminium.acoe.sys.api.entity.user.User;
import com.aluminium.acoe.sys.api.service.AdminUserService;
import com.aluminium.acoe.sys.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 유저 목록 조회 (ADMIN)
     */
    @GetMapping("/users")
    @Operation(summary = "관리자 화면에서 유저 목록 조회", description  = "전체 유저 정보를 반환한다.",
            responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")})
    @Parameter(name = "UserSearchDto", description = "User 검색 객체", in = ParameterIn.DEFAULT)
    @Parameter(name = "Pageable", description = "페이지네이션 객체 (sort : userId)", in = ParameterIn.DEFAULT)
    public ApiResponse getUserList(UserSearchDto userSearchDto, @PageableDefault(sort = "userId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> userList = adminUserService.getUserList(userSearchDto, pageable);
        return ApiResponse.success("user", userList);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "유저 정보 변경", description  = "관리자 화면에서 유저 정보를 수정한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")})
    @Parameter(name = "userId", description = "User ID", in = ParameterIn.PATH)
    public void updateUser(@PathVariable("userId") String userId,
                                      @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody User user) {
        user.setUserId(userId);
        adminUserService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "유저 정보 삭제", description  = "관리자 화면에서 유저 정보를 삭제한다.",
            responses = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공")}
    )
    @Parameter(name = "userId", description = "User ID", in = ParameterIn.PATH)
    public void deleteUser(@PathVariable("userId") String userId){
        adminUserService.deleteUser(userId);
    }
}