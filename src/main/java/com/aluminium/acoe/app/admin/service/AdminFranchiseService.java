package com.aluminium.acoe.app.admin.service;

import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.entity.Franchise;

import java.util.List;

public interface AdminFranchiseService {

    /**
     * @return
     */
    List<FranchiseDto> getAllFranchiseDtoList();

    /**
     * @param franchiseId
     * @return
     */
    FranchiseDto getFranchiseDto(Long franchiseId);

    /**
     * @param franchiseId
     * @return
     */
    Franchise getFranchise(Long franchiseId);

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
