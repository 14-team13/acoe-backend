package com.aluminium.acoe.app.admin.service.implementation;

import com.aluminium.acoe.app.admin.dto.AdminCafeSearchDto;
import com.aluminium.acoe.app.admin.enums.MasterType;
import com.aluminium.acoe.app.admin.service.AdminCafeService;
import com.aluminium.acoe.app.admin.service.AdminFranchiseService;
import com.aluminium.acoe.app.admin.service.AdminMenuService;
import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.entity.Franchise;
import com.aluminium.acoe.app.main.persistance.CafeRepository;
import com.aluminium.acoe.app.main.service.CafeService;
import com.aluminium.acoe.common.converter.CommonConverter;

import java.util.Objects;

import com.aluminium.acoe.common.exception.BusinessInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminCafeServiceImpl implements AdminCafeService {
    private final CafeService cafeService;
    private final CafeRepository cafeRepository;
    private final AdminFranchiseService adminFranchiseService;
    private final AdminMenuService adminMenuService;
    private final CommonConverter commonConverter;

    @Override
    public Page<CafeDto> searchAdminCafeDtoPage(AdminCafeSearchDto searchDto, Pageable pageable) {
        return cafeRepository.searchListByDynamicCond(searchDto, pageable).map(e -> commonConverter.convertToGeneric(e, CafeDto.class));
    }

    @Override
    public CafeDto getCafeDto(Long cafeId) {
        return cafeService.getCafeDto(cafeId);
    }

    @Override
    @Transactional
    public Long updateCafe(CafeDto masterDto) {
        Objects.requireNonNull(masterDto.getCafeId(), "조회/수정/삭제시 Id는 필수입니다.");
        // 카페 수정
        Cafe cafe = cafeRepository.findById(masterDto.getCafeId())
                .orElseThrow(() -> new BusinessInvalidValueException("해당 ID에 대한 카페 정보가 없습니다."));

        // 프랜차이즈 카페는 메뉴정보 수정 불가
        Franchise franchise = null;
        if(masterDto.getFranchiseDto() != null){
            franchise = adminFranchiseService.getFranchise(masterDto.getFranchiseDto().getFranchiseId());
        } else {
            // 메뉴 수정/등록
            if(masterDto.getMenuList() != null) adminMenuService.saveMenus(masterDto, cafe);
        }

        // 카페 업데이트
        cafe.update(masterDto, franchise);
        cafeRepository.save(cafe);

        return cafe.getCafeId();
    }

    @Override
    @Transactional
    public Long createCafe(CafeDto dto) {
        Cafe cafe = cafeRepository.save(Cafe.toEntity(dto, Franchise.toEntity(dto.getFranchiseDto())));

        if(dto.getMenuList() != null && dto.getFranchiseDto() != null) {
            adminMenuService.createMenus(dto.getMenuList(), cafe);
        }

        return cafe.getCafeId();
    }

    @Override
    @Transactional
    public void deleteCafe(Long cafeId) {
        Objects.requireNonNull(cafeId, "조회/수정/삭제시 Id는 필수입니다.");

        // 메뉴삭제
        adminMenuService.deleteMenus(MasterType.CAFE, cafeId);

        // 카페삭제
        cafeRepository.deleteById(cafeId);
    }
}
