package com.aluminium.acoe.app.cafe.service;

import com.aluminium.acoe.app.cafe.dto.CafeDto;

import java.util.List;

public interface CafeService {
    /**
     * 매인 카페 목록 조회(마커용)
     * @param areaCd
     * @return
     */
    List<CafeDto> searchList(Long areaCd);

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
