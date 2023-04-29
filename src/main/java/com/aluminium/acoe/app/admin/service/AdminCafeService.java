package com.aluminium.acoe.app.admin.service;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import java.util.List;

public interface AdminCafeService {
    /**
     * 카페 목록 조회(ADMIN)
     * @param searchDto
     * @return
     */
    List<CafeDto> searchAdminCafeList(AdminCafeSearchDto searchDto);

    /**
     * @param cafeId
     * @return
     */
    CafeDto getCafe(Long cafeId);

    /**
     * @param dto
     * @return
     */
    Long updateCafe(CafeDto dto);


}