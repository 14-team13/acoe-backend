package com.aluminium.acoe.app.admin.service;

import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.dto.MenuDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.entity.Franchise;

import java.util.List;

public interface AdminMenuService {
    /**
     * @param cafeDto
     * 메뉴 등록/수정
     */
    void saveMenus(CafeDto cafeDto, Cafe cafe);

    /**
     * @param franchiseDto
     * 메뉴 등록/수정
     */
    void saveMenus(FranchiseDto franchiseDto, Franchise franchise);

    /**
     * @param menuDtoList
     * @param cafe
     */
    void createMenus(List<MenuDto> menuDtoList, Cafe cafe);

    /**
     * @param menuDtoList
     * @param franchise
     */
    void createMenus(List<MenuDto> menuDtoList, Franchise franchise);

    /**
     * @param masterType
     * @param masterId
     */
    void deleteMenus(Enum masterType, Long masterId);


}
