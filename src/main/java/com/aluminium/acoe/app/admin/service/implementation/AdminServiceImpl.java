package com.aluminium.acoe.app.admin.service.implementation;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.admin.service.AdminService;
import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.dto.FranchiseDto;
import com.aluminium.acoe.app.cafe.persistance.CafeRepository;
import com.aluminium.acoe.app.cafe.service.CafeService;
import com.aluminium.acoe.app.cafe.service.FranchiseService;
import com.aluminium.acoe.common.converter.CommonConverter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CafeService cafeService;
    private final CafeRepository cafeRepository;
    private final FranchiseService franchiseService;
    private final CommonConverter commonConverter;

    @Override
    public List<CafeDto> searchAdminCafeList(AdminCafeSearchDto searchDto) {
        return cafeRepository.searchListByDynamicCond(searchDto)
            .stream().map(e -> commonConverter.convertToGeneric(e, CafeDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<FranchiseDto> getAllFranchiseList() {
        return franchiseService.searchList(null);
    }

    @Override
    public CafeDto getCafe(Long cafeId) {
        return cafeService.getCafe(cafeId);
    }

    @Override
    public FranchiseDto getFranchise(Long franchiseId) {
        return franchiseService.getFranchise(franchiseId);
    }
}
