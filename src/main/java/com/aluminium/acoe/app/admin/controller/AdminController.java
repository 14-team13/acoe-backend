package com.aluminium.acoe.app.admin.controller;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.admin.service.AdminService;
import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.dto.FranchiseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    /**
     * 카페 목록 조회(ADMIN)
     */
    @GetMapping("/cafes")
    @Operation(summary = "관리자 화면에서 카페목록 조회", description  = "검색조건으로 카페 목록을 조회한다.", responses = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CafeDto.class)))
    })
    @Parameter(name = "AdminCafeSearchDto", description = "카페 검색 객체", in = ParameterIn.DEFAULT)
    public List<CafeDto> searchAdminCafeList(@Valid AdminCafeSearchDto searchDto){
        return adminService.searchAdminCafeList(searchDto);
    }

    /**
     * 관리자 카페 상세 조회(ADMIN)
     */
    @GetMapping("/cafe/{cafeId}")
    @Operation(summary = "관리자 카페 정보 상세 조회", description  = "관리자 화면에서 카페 상세 정보를 조회한다.(권한필요)", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CafeDto.class)))
    })
    @Parameter(name = "cafeId", description = "카페ID", in = ParameterIn.PATH)
    public CafeDto getCafe(@PathVariable("cafeId") Long cafeId){
        return adminService.getCafe(cafeId);
    }

    /**
     * 관리자 카페 상세 수정(ADMIN)
     */
    @PutMapping("/cafe")
    @Operation(summary = "관리자 카페 정보 상세 수정", description  = "관리자 화면에서 카페 상세 정보를 수정한다.(권한필요)",
        responses = {@ApiResponse(responseCode = "200", description = "수정 성공")}
    )
    public Long updateCafe(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CafeDto dto){
        return adminService.updateCafe(dto);
    }

    /**
     * 프랜차이즈 목록 조회(ADMIN)
     */
    @GetMapping("/franchises")
    @Operation(summary = "관리자 화면에서 프랜차이즈 목록 조회", description  = "모든 프랜차이즈 목록을 조회한다.", responses = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = FranchiseDto.class)))
    })
    public List<FranchiseDto> searchAdminFranchiseList(){
        return adminService.getAllFranchiseList();
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
        return adminService.getFranchise(franchiseId);
    }
}