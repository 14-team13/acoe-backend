package com.aluminium.acoe.app.cafe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.dto.MenuDto;
import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.app.cafe.entity.Menu;
import com.aluminium.acoe.app.cafe.persistance.CafeRepository;
import com.aluminium.acoe.app.cafe.persistance.MenuRepository;
import com.aluminium.acoe.app.cafe.service.implementation.CafeServiceImpl;
import com.aluminium.acoe.common.converter.CommonConverter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {
    @InjectMocks
    private CafeServiceImpl cafeService;

    @Mock
    private CafeRepository cafeRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private CommonConverter commonConverter;

    private static final Long AREA_CD = 3120000L;
    private static final String CAFE_NM = "mockCafe";
    private static final String MENU_NM = "아메리카노";


    @Test
    void searchListTest() {
        // given
        Cafe cafe = com.aluminium.acoe.app.cafe.entity.Cafe.builder()
            .cafeId(11111L)
            .cafeNm(CAFE_NM)
            .x(123L)
            .y(456L)
            .areaCd(AREA_CD)
            .trdStateCd(1L)
            .build();

        CafeDto cafeDto = CafeDto.builder()
            .cafeId(11111L)
            .cafeNm(CAFE_NM)
            .x(123L)
            .y(456L)
            .areaCd(AREA_CD)
            .trdStateCd(1L)
            .build();

        given(cafeRepository.findByAreaCdAndTrdStateCd(any(), any())).willReturn(Arrays.asList(cafe));
        given(commonConverter.convertToGeneric(any(), any())).willReturn(cafeDto);

        // when
        List<CafeDto> cafeDtos = cafeService.searchList(AREA_CD);

        // then
        cafeDtos.forEach(dto -> {
            assertEquals(dto.getCafeNm(), CAFE_NM);
            assertEquals(dto.getTrdStateCd(), 1L);
        });
    }

    @Test
    void getCafeTest() {
        // given
        Cafe cafe = Cafe.builder()
                .cafeId(11111L)
                .cafeNm(CAFE_NM)
                .x(123L)
                .y(456L)
                .areaCd(AREA_CD)
                .trdStateCd(1L)
                .build();

        Menu menu = Menu.builder()
                .cafe(cafe)
                .menuId(11111L)
                .menuNm(MENU_NM)
                .price(4300L)
                .build();

        MenuDto menuDto = MenuDto.builder()
                .menuId(11111L)
                .menuNm(MENU_NM)
                .price(4300L)
                .build();

        CafeDto cafeDto = CafeDto.builder()
                .cafeId(11111L)
                .cafeNm(CAFE_NM)
                .x(123L)
                .y(456L)
                .areaCd(AREA_CD)
                .trdStateCd(1L)
                .menuList(Arrays.asList(menuDto))
                .build();

        given(cafeRepository.findById(any())).willReturn(Optional.of(cafe));
        given(commonConverter.convertToGeneric(any(), eq(CafeDto.class))).willReturn(cafeDto);
        given(menuRepository.findByCafe_CafeId(any())).willReturn(Arrays.asList(menu));
        given(commonConverter.convertToGeneric(any(), eq(MenuDto.class))).willReturn(menuDto);

        // when
        CafeDto resultDto = cafeService.getCafe(11111L);

        // then
        assertEquals(resultDto.getCafeNm(), CAFE_NM);
        resultDto.getMenuList().forEach(dto-> {
            assertEquals(dto.getMenuNm(), MENU_NM);
            assertEquals(dto.getPrice(),4300L);
        });

    }
}