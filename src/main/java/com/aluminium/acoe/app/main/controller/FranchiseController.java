package com.aluminium.acoe.app.main.controller;

import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/main/franchise")
public class FranchiseController {
    private final FranchiseService franchiseService;

    /**
     * 프랜차이즈 목록 조회
     */
    @GetMapping("/franchises")
    @Operation(summary = "프랜차이즈 목록 조회", description  = "메인화면에서 프랜차이즈 목록을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = FranchiseDto.class)))
    })
    public List<FranchiseDto> searchList(){
        return franchiseService.searchDtoList(true);
    }


}
