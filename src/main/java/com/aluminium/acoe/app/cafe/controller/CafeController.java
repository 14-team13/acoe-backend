package com.aluminium.acoe.app.cafe.controller;

import com.aluminium.acoe.app.cafe.service.CafeService;
import com.aluminium.acoe.app.cafe.dto.CafeDto;
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
@RequestMapping("/cafe")
public class CafeController {

    private final CafeService cafeService;

    /**
     * 메인 영업중 카페 목록 조회 API
     */
    @GetMapping("/cafes/{areaCd}")
    @Operation(summary = "메인 영업중 카페목록 조회", description  = "메인화면에서 영업중인 카페 목록을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CafeDto.class)))
    })
    @Parameter(name = "areaCd", description = "지역코드(서대문구: 3120000)", in = ParameterIn.PATH)
    public List<CafeDto> searchList(@PathVariable("areaCd") Long areaCd){
        return cafeService.searchList(areaCd, 1L);
    }

    /**
     * 메인 카페 상세 조회 API
     */
    @GetMapping("/cafe/{cafeId}")
    @Operation(summary = "메인 카페 정보 상세 조회", description  = "메인화면에서 카페 상세 정보를 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CafeDto.class)))
    })
    @Parameter(name = "cafeId", description = "카페ID", in = ParameterIn.PATH)
    public CafeDto getCafe(@PathVariable("cafeId") Long cafeId){
        return cafeService.getCafe(cafeId);
    }
}