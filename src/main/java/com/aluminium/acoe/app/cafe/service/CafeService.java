package com.aluminium.acoe.app.cafe.service;

import com.aluminium.acoe.app.cafe.dto.CafeDto;

import java.util.List;

public interface CafeService {
    /**
     * 카페 목록 조회
     * @param areaCd
     * @return
     */
    List<CafeDto> searchList(Long areaCd);
}
