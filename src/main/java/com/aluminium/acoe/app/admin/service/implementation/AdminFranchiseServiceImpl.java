package com.aluminium.acoe.app.admin.service.implementation;

import com.aluminium.acoe.app.admin.service.AdminFranchiseService;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.dto.MenuDto;
import com.aluminium.acoe.app.main.entity.Franchise;
import com.aluminium.acoe.app.main.entity.Menu;
import com.aluminium.acoe.app.main.persistance.FranchiseRepository;
import com.aluminium.acoe.app.main.persistance.MenuRepository;
import com.aluminium.acoe.app.main.service.FranchiseService;
import com.aluminium.acoe.common.exception.BusinessInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminFranchiseServiceImpl implements AdminFranchiseService {
    private final FranchiseService franchiseService;
    private final FranchiseRepository franchiseRepository;
    private final MenuRepository menuRepository;

    @Override
    public List<FranchiseDto> getAllFranchiseList() {
        return franchiseService.searchList(null);
    }

    @Override
    public FranchiseDto getFranchise(Long franchiseId) {
        return franchiseService.getFranchise(franchiseId);
    }

    @Override
    @Transactional
    public Long createFranchise(FranchiseDto dto) {
        Franchise franchise = franchiseRepository.save(Franchise.toEntity(dto));

        if(dto.getMenuList() != null){
            List<Menu> menuList = dto.getMenuList().stream()
                    .map(menuDto -> Menu.toEntity(menuDto, null, franchise))
                    .collect(Collectors.toList());
            menuRepository.saveAll(menuList);
        }

        return franchise.getFranchiseId();
    }

    @Override
    @Transactional
    public Long updateFranchise(FranchiseDto masterDto) {
        Objects.requireNonNull(masterDto.getFranchiseId(), "조회/수정/삭제시 Id는 필수입니다.");

        // 프랜차이즈 수정
        Franchise franchise = franchiseRepository.findById(masterDto.getFranchiseId())
                .orElseThrow(() -> new BusinessInvalidValueException("해당 ID에 대한 프랜차이즈 정보가 없습니다."));
        franchise.update(masterDto);
        franchiseRepository.save(franchise);

        // 메뉴 등록/수정
        // Update
        List<Menu> updatedMenus = menuRepository.findByFranchise_FranchiseId(masterDto.getFranchiseId());
        updatedMenus.forEach(menu ->
                masterDto.getMenuList().stream().filter(updateDto -> menu.getMenuId().equals(updateDto.getMenuId()))
                        .findFirst().ifPresent(matched -> menu.update(matched))
        );
        // Create
        List
        masterDto.getMenuList().stream().filter(menuDto -> menuDto.getMenuId() == null)
                        .

        menuRepository.saveAll(updatedMenus);

    }

    @Override
    @Transactional
    public void deleteFranchise(Long franchiseId) {
        Objects.requireNonNull(franchiseId, "조회/수정/삭제시 Id는 필수입니다.");
        // 메뉴삭제
        menuRepository.deleteAllByFranchise_FranchiseId(franchiseId);
        menuRepository.deleteAll();

        // 프랜차이즈삭제
        franchiseRepository.deleteById(franchiseId);
    }

}
