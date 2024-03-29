package com.aluminium.acoe.app.admin.controller;

import com.aluminium.acoe.app.admin.service.AdminFranchiseService;
import com.aluminium.acoe.app.main.converter.FranchiseConverter;
import com.aluminium.acoe.app.main.resource.FranchiseResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/admin/franchise")
public class AdminFranchiseController {

    private final AdminFranchiseService adminFranchiseService;
    private final FranchiseConverter franchiseConverter;

    /**
     * 프랜차이즈 목록 조회(ADMIN)
     */
    @GetMapping("/franchises")
    @Operation(summary = "관리자 화면에서 프랜차이즈 목록 조회", description  = "모든 프랜차이즈 목록을 조회한다.",
            responses = { @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FranchiseResource.class))))}
    )
    public List<FranchiseResource> searchAdminFranchiseList(){
        return adminFranchiseService.getAllFranchiseDtoList().stream()
                .map(franchiseConverter::convertToResource)
                .collect(Collectors.toList());
    }

    /**
     * 관리자 프랜차이즈 상세 조회(ADMIN)
     */
    @GetMapping("/{franchiseId}")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 조회", description  = "관리자 화면에서 프랜차이즈 상세 정보를 조회한다.(권한필요)",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = FranchiseResource.class)))}
    )
    @Parameter(name = "franchiseId", description = "프랜차이즈ID", in = ParameterIn.PATH)
    public FranchiseResource getFranchise(@PathVariable("franchiseId") Long franchiseId){
        return franchiseConverter.convertToResource(adminFranchiseService.getFranchiseDto(franchiseId));
    }

    /**
     * 관리자 프랜차이즈 상세 등록(ADMIN)
     */
    @PostMapping("/")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 등록", description  = "관리자 화면에서 프랜차이즈 상세 정보를 등록한다.(권한필요)"
        + "Id값은 비워둔 채 등록될 대상 컬럼들의 정보를 Resource에 세팅하여 요청한다.\n"
        + "MenuList에 각 메뉴 정보를 담아서 보낸다. 각 메뉴의 Id는 마찬가지로 비워서 보낸다." 
        + "로고 이미지는 base64 string으로 보낸다.",
            responses = {@ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(type = "number", description = "프랜차이즈 ID")))}
    )
    public Long createFranchise(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody FranchiseResource resource){
        return adminFranchiseService.createFranchise(franchiseConverter.convertToDto(resource));
    }

    /**
     * 관리자 프랜차이즈 상세 수정(ADMIN)
     */
    @PutMapping("/{franchiseId}")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 수정", description  = "관리자 화면에서 프랜차이즈 상세 정보를 수정한다.(권한필요)"
        + "조회시 받은 Id값으로 Reousrce에 세팅하고 수정 대상 컬럼(프랜차이즈명, 할인금액, 로고이미지, 사용여부)들의 정보를 Resource에 세팅하여 요청한다.\n"
        + "MenuList에 각 메뉴 정보를 담아서 보낸다. 각 메뉴의 Id는 신규일경우 비워서 보내고 수정일 경우 조회시 받은 id로 세팅한다."
        + "메뉴는 수정일 경우 메뉴명과 가격만 수정 가능하다."
        + "로고 이미지는 base64 string으로 보낸다.",
            responses = {@ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(type = "number", description = "프랜차이즈 ID")))}
    )
    public Long updateFranchise(@PathVariable("franchiseId") Long franchiseId,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody FranchiseResource resource)
    {
        return adminFranchiseService.updateFranchise(franchiseConverter.convertToDto(resource));
    }

    /**
     * 관리자 프랜차이즈 상세 삭제(ADMIN)
     */
    @DeleteMapping("/{franchiseId}")
    @Operation(summary = "관리자 프랜차이즈 정보 상세 삭제", description  = "관리자 화면에서 프랜차이즈 정보를 삭제한다.(권한필요)",
            responses = {@ApiResponse(responseCode = "200", description = "삭제 성공")}
    )
    @Parameter(name = "franchiseId", description = "프랜차이즈ID", in = ParameterIn.PATH)
    public void deleteFranchise(@PathVariable("franchiseId") Long franchiseId){
        adminFranchiseService.deleteFranchise(franchiseId);
    }
}