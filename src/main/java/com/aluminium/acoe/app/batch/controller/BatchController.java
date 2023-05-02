package com.aluminium.acoe.app.batch.controller;

import com.aluminium.acoe.app.batch.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/batch")
public class BatchController {

    private final BatchService batchService;

    /**
     * 카페정보조회 일배치
     */
    @GetMapping("/execute-cafe")
    @Operation(summary = "카페정보조회 일배치", description = "카페정보조회를 조회하여 DB에 저장한다.",
        responses = {@ApiResponse(responseCode = "200", description = "배치 성공")})
    public void executeCafeBatch() {
        batchService.executeCafeBatch();
    }
}