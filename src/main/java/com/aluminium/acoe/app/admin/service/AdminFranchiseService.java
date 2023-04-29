package com.aluminium.acoe.app.admin.service;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;

import java.util.List;

public interface AdminFranchiseService {

    /**
     * @return
     */
    List<FranchiseDto> getAllFranchiseList();


    /**
     * @param franchiseId
     * @return
     */
    FranchiseDto getFranchise(Long franchiseId);

    /**
     * @param dto
     * @return
     */
    Long createFranchise(FranchiseDto dto);

    /**
     * @param dto
     * @return
     */
    Long updateFranchise(FranchiseDto dto);

    /**
     * @param franchiseId
     */
    void deleteFranchise(Long franchiseId);


}
