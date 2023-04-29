package com.aluminium.acoe.app.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.MenuDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.entity.Menu;
import com.aluminium.acoe.app.main.persistance.CafeRepository;
import com.aluminium.acoe.app.main.persistance.MenuRepository;
import com.aluminium.acoe.app.main.service.implementation.CafeServiceImpl;
import com.aluminium.acoe.common.converter.CommonConverter;
import java.math.BigDecimal;
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
        Cafe cafe = com.aluminium.acoe.app.main.entity.Cafe.builder()
            .cafeId(11111L)
            .cafeNm(CAFE_NM)
            .x(BigDecimal.valueOf(123L))
            .y(BigDecimal.valueOf(456L))
            .areaCd(AREA_CD)
            .trdStateCd(1L)
            .build();

        CafeDto cafeDto = new CafeDto();
        cafeDto.setCafeId(11111L);
        cafeDto.setCafeNm(CAFE_NM);
        cafeDto.setX(BigDecimal.valueOf(123L));
        cafeDto.setY(BigDecimal.valueOf(456L));
        cafeDto.setAreaCd(AREA_CD);
        cafeDto.setTrdStateCd(1L);

        given(cafeRepository.findByAreaCdAndTrdStateCd(any(), any())).willReturn(Arrays.asList(cafe));
        given(commonConverter.convertToGeneric(any(), any())).willReturn(cafeDto);

        // when
        List<CafeDto> cafeDtos = cafeService.searchList(AREA_CD, 1L);

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
                .x(BigDecimal.valueOf(123L))
                .y(BigDecimal.valueOf(456L))
                .areaCd(AREA_CD)
                .trdStateCd(1L)
                .build();

        Menu menu = Menu.builder()
                .cafe(cafe)
                .menuId(11111L)
                .menuNm(MENU_NM)
                .price(4300L)
                .build();

        MenuDto menuDto = new MenuDto();
        menuDto.setMenuId(1111L);
        menuDto.setPrice(4300L);
        menuDto.setMenuNm(MENU_NM);

        CafeDto cafeDto = new CafeDto();
        cafeDto.setCafeId(11111L);
        cafeDto.setCafeNm(CAFE_NM);
        cafeDto.setX(BigDecimal.valueOf(123L));
        cafeDto.setY(BigDecimal.valueOf(456L));
        cafeDto.setAreaCd(AREA_CD);
        cafeDto.setTrdStateCd(1L);
        cafeDto.setMenuList(Arrays.asList(menuDto));

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