package com.aluminium.acoe.app.admin.service;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.dto.FranchiseDto;
import java.util.List;

public interface AdminService {
    /**
     * 카페 목록 조회(ADMIN)
     * @param searchDto
     * @return
     */
    List<CafeDto> searchAdminCafeList(AdminCafeSearchDto searchDto);

    /**
     * @return
     */
    List<FranchiseDto> getAllFranchiseList();

    /**
     * @param cafeId
     * @return
     */
    CafeDto getCafe(Long cafeId);

    /**
     * @param franchiseId
     * @return
     */
    FranchiseDto getFranchise(Long franchiseId);


}
