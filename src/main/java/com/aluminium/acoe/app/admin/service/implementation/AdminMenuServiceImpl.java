package com.aluminium.acoe.app.admin.service.implementation;

import com.aluminium.acoe.app.admin.enums.MasterType;
import com.aluminium.acoe.app.admin.service.AdminMenuService;
import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.dto.MenuDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.entity.Franchise;
import com.aluminium.acoe.app.main.entity.Menu;
import com.aluminium.acoe.app.main.persistance.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminMenuServiceImpl implements AdminMenuService {

    private final MenuRepository menuRepository;


    /**
     * @param cafeDto 메뉴저장
     */
    @Override
    @Transactional
    public void saveMenus(CafeDto cafeDto, Cafe cafe) {
        // Update
        List<Menu> updatedMenus = menuRepository.findByCafe_CafeId(cafeDto.getCafeId());
        updatedMenus.forEach(menu ->
                cafeDto.getMenuList().stream().filter(updateDto -> menu.getMenuId().equals(updateDto.getMenuId()))
                        .findFirst().ifPresent(menu::update)
        );
        // Create
        List<Menu> createdMenus = cafeDto.getMenuList().stream()
                .filter(dto -> dto.getMenuId() == null)
                .map(dto -> Menu.toEntity(dto, cafe, null))
                .collect(Collectors.toList());

        createdMenus.addAll(updatedMenus);
        menuRepository.saveAll(createdMenus);
    }

    /**
     * @param franchiseDto
     */
    @Override
    @Transactional
    public void saveMenus(FranchiseDto franchiseDto, Franchise franchise) {
        // Update
        List<Menu> updatedMenus = menuRepository.findByFranchise_FranchiseId(franchiseDto.getFranchiseId());
        updatedMenus.forEach(menu ->
                franchiseDto.getMenuList().stream().filter(updateDto -> menu.getMenuId().equals(updateDto.getMenuId()))
                        .findFirst().ifPresent(menu::update)
        );
        // Create
        List<Menu> createdMenus = franchiseDto.getMenuList().stream()
                .filter(dto -> dto.getMenuId() == null)
                .map(dto -> Menu.toEntity(dto, null, franchise))
                .collect(Collectors.toList());

        createdMenus.addAll(updatedMenus);
        menuRepository.saveAll(createdMenus);
    }

    @Override
    @Transactional
    public void createMenus(List<MenuDto> menuDtoList, Cafe cafe) {
        List<Menu> menuList = menuDtoList.stream()
                .map(menuDto -> Menu.toEntity(menuDto, cafe, null))
                .collect(Collectors.toList());
        menuRepository.saveAll(menuList);
    }

    @Override
    @Transactional
    public void createMenus(List<MenuDto> menuDtoList, Franchise franchise) {
        List<Menu> menuList = menuDtoList.stream()
                .map(menuDto -> Menu.toEntity(menuDto, null, franchise))
                .collect(Collectors.toList());
        menuRepository.saveAll(menuList);
    }

    @Override
    @Transactional
    public void deleteMenus(Enum masterType, Long masterId) {
        if(masterType == MasterType.FRANCHISE){
            menuRepository.deleteAllByFranchise_FranchiseId(masterId);
        } else {
            menuRepository.deleteAllByCafe_CafeId(masterId);
        }
    }
}
