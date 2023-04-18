package com.aluminium.acoe.app.cafe.service;

import com.aluminium.acoe.app.cafe.dto.CafeDto;

import java.util.List;

public interface CafeService {
    /**
     * 영업 상태에 따른 카페 목록 조회
     * @param areaCd
     * @param trdStateCd - null이면 상태 관계없이 모든 카페 조회 
     * @return
     */
    List<CafeDto> searchList(Long areaCd, Long trdStateCd);

    /**
     * @param cafeId
     * @return
     */
    CafeDto getCafe(Long cafeId);
}
