package com.aluminium.acoe.app.admin.controller;

import com.aluminium.acoe.app.admin.service.adminFranchiseService;
import com.aluminium.acoe.app.admin.service.AdminFranchiseService;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/admin/franchise")
public class AdminFranchiseController {

    private final AdminFranchiseService adminFranchiseService;

    /**
     * 프랜차이즈 목록 조회(ADMIN)
     */
    @GetMapping("/franchises")
    @Operation(summary = "관리자 화면에서 프랜차이즈 목록 조회", description  = "모든 프랜차이즈 목록을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = FranchiseDto.class)))
    })
    public List<FranchiseDto> searchAdminFranchiseList(){
        return adminFranchiseService.getAllFranchiseList();
    }

    /**
     * 관리자 프랜차이즈 상세 조회(ADMIN)
     */
    @GetMapping("frnachise/{franchiseId}")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 조회", description  = "관리자 화면에서 프랜차이즈 상세 정보를 조회한다.(권한필요)", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = FranchiseDto.class)))
    })
    @Parameter(name = "franchiseId", description = "프랜차이즈ID", in = ParameterIn.PATH)
    public FranchiseDto getFranchise(@PathVariable("franchise") Long franchiseId){
        return adminFranchiseService.getFranchise(franchiseId);
    }

    /**
     * 관리자 프랜차이즈 상세 등록(ADMIN)
     */
    @PostMapping("frnachise")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 수정", description  = "관리자 화면에서 프랜차이즈 상세 정보를 수정한다.(권한필요)",
            responses = {@ApiResponse(responseCode = "200", description = "수정 성공")}
    )
    public Long createFranchise(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody FranchiseDto dto){
        return adminFranchiseService.
    }

    /**
     * 관리자 프랜차이즈 상세 수정(ADMIN)
     */
    @PutMapping("frnachise")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 수정", description  = "관리자 화면에서 프랜차이즈 상세 정보를 수정한다.(권한필요)",
            responses = {@ApiResponse(responseCode = "200", description = "수정 성공")}
    )
    public Long updateFranchise(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody FranchiseDto dto){
        return adminFranchiseService.
    }

    /**
     * 관리자 프랜차이즈 상세 삭제(ADMIN)
     */
    @DeleteMapping("frnachise")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 등록", description  = "관리자 화면에서 프랜차이즈 상세 정보를 등록한다.(권한필요)", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = FranchiseDto.class)))
    })
    @Parameter(name = "franchiseId", description = "프랜차이즈ID", in = ParameterIn.PATH)
    public FranchiseDto deleteFranchise(@PathVariable("franchise") Long franchiseId){
        return adminFranchiseService
    }
}