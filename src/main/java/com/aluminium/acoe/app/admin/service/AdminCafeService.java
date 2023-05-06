package com.aluminium.acoe.app.admin.service;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.main.dto.CafeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface AdminCafeService {
    /**
     * 카페 목록 조회(ADMIN)
     * @param searchDto
     * @return
     */
    Page<CafeDto> searchAdminCafeDtoPage(AdminCafeSearchDto searchDto, Pageable pageable);

    /**
     * @param cafeId
     * @return
     */
    CafeDto getCafeDto(Long cafeId);

    /**
     * @param dto
     * @return
     */
    Long updateCafe(CafeDto dto);

    /**
     * @param dto
     * @return
     */
    Long createCafe(CafeDto dto);

    /**
     * @param cafeId
     */
    void deleteCafe(Long cafeId);


}
