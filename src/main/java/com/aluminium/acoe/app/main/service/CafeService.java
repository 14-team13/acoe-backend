package com.aluminium.acoe.app.main.service;

import com.aluminium.acoe.app.main.dto.CafeDto;

import java.util.List;

public interface CafeService {
    /**
     * 매인 카페 목록 조회(마커용)
     * @param areaCd
     * @param trdStateCd - null이면 상태 관계없이 모든 카페 조회
     * @return
     */
    List<CafeDto> searchList(Long areaCd, Long trdStateCd);

    /**
     * 메인 카페 목록 조회(목록용)
     * @param keyword
     * @return
     */
    List<CafeDto> searchListByKeyword(String keyword);

    /**
     * @param cafeId
     * @return
     */
    CafeDto getCafe(Long cafeId);
}
