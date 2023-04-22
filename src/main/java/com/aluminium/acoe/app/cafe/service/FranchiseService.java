package com.aluminium.acoe.app.cafe.service;

import com.aluminium.acoe.app.cafe.dto.FranchiseDto;

import java.util.List;

public interface FranchiseService {
    /**
     * 사용 여부에 따른 프랜차이즈 목록 조회
     */
    List<FranchiseDto> searchList(Boolean useYn);
}
