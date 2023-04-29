package com.aluminium.acoe.app.main.service;

import com.aluminium.acoe.app.main.dto.FranchiseDto;

import java.util.List;

public interface FranchiseService {
    /**
     * 사용 여부에 따른 프랜차이즈 목록 조회
     */
    List<FranchiseDto> searchList(Boolean useYn);

    /**
     * 프랜차이즈 상세 조회
     * @param franchiseId 
     * @return FranchiseDto
     */
    FranchiseDto getFranchise(Long franchiseId);
}
