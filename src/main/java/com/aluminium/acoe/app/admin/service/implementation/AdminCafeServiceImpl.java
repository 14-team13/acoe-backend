package com.aluminium.acoe.app.admin.service.implementation;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.admin.service.AdminCafeService;
import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.persistance.CafeRepository;
import com.aluminium.acoe.app.main.service.CafeService;
import com.aluminium.acoe.app.main.service.FranchiseService;
import com.aluminium.acoe.common.converter.CommonConverter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminCafeServiceImpl implements AdminCafeService {
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
    public CafeDto getCafe(Long cafeId) {
        return cafeService.getCafe(cafeId);
    }

    @Override
    @Transactional
    public Long updateCafe(CafeDto dto) {
        Objects.requireNonNull(dto.getCafeId(), "조회/수정/삭제시 Id는 필수입니다.");
        CafeDto cafeDto = cafeService.getCafe(dto.getCafeId());

        // 메뉴 수정
        if(cafeDto.getFranchiseDto() != null){

        }
    }
}
