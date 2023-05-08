package com.aluminium.acoe.app.main.controller;

import com.aluminium.acoe.app.main.converter.CafeConverter;
import com.aluminium.acoe.app.main.resource.CafeResource;
import com.aluminium.acoe.app.main.service.CafeService;
import com.aluminium.acoe.app.main.dto.CafeDto;
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
@RequestMapping("/main/cafe")
public class CafeController {

    private final CafeService cafeService;

    private final CafeConverter cafeConverter;


    /**
     * 메인 영업중 카페 목록 조회 API(마커용)
     */
    @GetMapping("/cafes/{areaCd}")
    @Operation(summary = "메인 영업중 카페목록 조회", description  = "메인화면에서 영업중인 카페 목록을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CafeResource.class))))
    })
    @Parameter(name = "areaCd", description = "지역코드(서대문구: 3120000)", in = ParameterIn.PATH)
    public List<CafeResource> searchList(@PathVariable("areaCd") Long areaCd){
        List<CafeDto> cafeDtos = cafeService.searchDtoList(areaCd, 1L);

        // convert to resource
        return cafeDtos.stream().map(cafeConverter::convertDtoToResource)
                .collect(Collectors.toList());
    }

    /**
     * 메인 카페 키워드 목록 조회 API(목록용)
     */
    @GetMapping("/cafe-keyword/{keyword}")
    @Operation(summary = "메인 키워드로 카페목록 조회", description  = "메인화면에서 키워드로 카페 목록을 조회한다.", responses = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CafeResource.class)))
    })
    @Parameter(name = "keyWord", description = "검색 키워드", in = ParameterIn.PATH)
    public List<CafeResource> searchListByKeyword(@PathVariable("keyword") String keyword){
        List<CafeDto> cafeDtos = cafeService.searchDtoListByKeyword(keyword);

        // convert to resource
        return cafeDtos.stream().map(cafeConverter::convertDtoToResource)
                .collect(Collectors.toList());
    }

    /**
     * 메인 카페 상세 조회 API
     */
    @GetMapping("/{cafeId}")
    @Operation(summary = "메인 카페 정보 상세 조회", description  = "메인화면에서 카페 상세 정보를 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CafeResource.class)))
    })
    @Parameter(name = "cafeId", description = "카페ID", in = ParameterIn.PATH)
    public CafeResource getCafe(@PathVariable("cafeId") Long cafeId){
        return cafeConverter.convertDtoToResource(cafeService.getCafeDto(cafeId));
    }
}