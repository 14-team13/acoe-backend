package com.aluminium.acoe.app.admin.controller;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.admin.resource.AdminCafeSearchResource;
import com.aluminium.acoe.app.admin.resource.AdminCafeUpdateResource;
import com.aluminium.acoe.app.admin.service.AdminCafeService;
import com.aluminium.acoe.app.main.converter.CafeConverter;
import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.resource.CafeResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/admin/cafe")
public class AdminCafeController {

    private final AdminCafeService adminCafeService;
    private final CafeConverter cafeConverter;

    /**
     * 카페 목록 조회(ADMIN)
     */
    @GetMapping("/cafes")
    @Operation(summary = "관리자 화면에서 카페목록 조회",
            description  = "검색조건으로 카페 목록을 조회한다.",
            responses = { @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CafeResource.class))))}
    )
    public Page<CafeResource> searchAdminCafeList(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody AdminCafeSearchResource searchResource){
        Page<CafeDto> cafeDtos = adminCafeService.searchAdminCafeDtoPage(cafeConverter.convertToGeneric(searchResource, AdminCafeSearchDto.class), searchResource.getPageInfo());

        // convert to resource
        return cafeDtos.map(cafeConverter::convertDtoToResource);
    }

    /**
     * 관리자 카페 상세 조회(ADMIN)
     */
    @GetMapping("/{cafeId}")
    @Operation(summary = "관리자 카페 정보 상세 조회",
            description  = "관리자 화면에서 카페 상세 정보를 조회한다.(권한필요)",
            responses = { @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CafeResource.class)))}
    )
    @Parameter(name = "cafeId", description = "카페ID", in = ParameterIn.PATH)
    public CafeResource getCafe(@PathVariable("cafeId") Long cafeId){
        return cafeConverter.convertDtoToResource(adminCafeService.getCafeDto(cafeId));
    }

    /**
     * 관리자 카페 상세 수정(ADMIN)
     */
    @PutMapping("/{cafeId}")
    @Operation(summary = "관리자 카페 정보 상세 수정", description  = "관리자 화면에서 카페 상세 정보를 수정한다.(권한필요)"
        + "조회시 받았던 Id을 Resource에 세팅하고 수정 대상 컬럼들(카페이름, 주소, 할인금액, 앱오더여부, 키오스크여부, 사용여부, 프랜차이즈, 좌표)의 정보를 Resource에 세팅하여 요청한다.\n"
        + "프랜차이즈 카페인 경우 Reousrce의 franchise 객체에 franchiseId를 담아서 던진다."
        + "프랜차이즈일 경우 메뉴는 등록하지 못하며 프랜차이즈가 아닐경우 MenuList의 각 메뉴 정보를 담아서 보낸다. 각 메뉴의 Id는 신규일때느 마찬가지로 비워서 보내나 수정일때는 조회시 받았던 ID로 세팅한다."
        + "메뉴는 수정일 경우 메뉴명과 가격만 수정 가능하다.",
            responses = {@ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(type = "number", description = "카페 ID")))}
    )
    public Long updateCafe(@PathVariable("cafeId") Long cafeId,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody AdminCafeUpdateResource updateResource)
    {
        return adminCafeService.updateCafe(cafeConverter.convertToGeneric(updateResource, CafeDto.class));
    }


    /**
     * 관리자 카페 상세 등록(ADMIN)
     */
    @PostMapping("")
    @Operation(summary = "관리자 카페 정보 상세 등록", description  = "관리자 화면에서 카페 상세 정보를 등록한다.(권한필요)"
        + "Id값은 비워둔 채 등록될 대상 컬럼들의 정보를 Resource에 세팅하여 요청한다.\n"
        + "프랜차이즈인 경우 Reousrce의 franchise 객체에 franchiseId를 담아서 던진다."
        + "프랜차이즈일 경우 메뉴는 등록하지 못하며 프랜차이즈가 아닐경우 MenuList의 각 메뉴 정보를 담아서 보낸다. 각 메뉴의 Id는 마찬가지로 비워서 보낸다.",
            responses = {@ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(type = "number", description = "카페 ID")))}
    )
    public Long createCafe(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody AdminCafeUpdateResource updateResource){

        return adminCafeService.createCafe(cafeConverter.convertToGeneric(updateResource, CafeDto.class));
    }

    /**
     * 관리자 카페 상세 삭제(ADMIN)
     */
    @DeleteMapping("/{cafeId}")
    @Operation(summary = "관리자 카페 정보 상세 삭제", description  = "관리자 화면에서 카페 정보를 삭제한다.(권한필요)",
            responses = {@ApiResponse(responseCode = "200", description = "삭제 성공")}
    )
    @Parameter(name = "cafeId", description = "카페ID", in = ParameterIn.PATH)
    public void deleteCafe(@PathVariable("cafeId") Long cafeId){
        adminCafeService.deleteCafe(cafeId);
    }
}