package com.aluminium.acoe.app.cafe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.app.cafe.persistance.CafeRepository;
import com.aluminium.acoe.app.cafe.service.implementation.CafeServiceImpl;
import com.aluminium.acoe.common.converter.CommonConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {
    @InjectMocks
    private CafeServiceImpl cafeService;

    @Mock
    private CafeRepository cafeRepository;

    @Mock
    private CommonConverter commonConverter;

    private static final Long areaCd = 3120000L;
    private static final String cafeNm = "mockCafe";


    @Test
    void searchList() {
        // given
        Cafe cafe = com.aluminium.acoe.app.cafe.entity.Cafe.builder()
            .cafeId(11111L)
            .cafeNm(cafeNm)
            .x(123L)
            .y(456L)
            .areaCd(areaCd)
            .trdStateCd(1L)
            .build();

        CafeDto cafeDto = CafeDto.builder()
            .cafeId(11111L)
            .cafeNm(cafeNm)
            .x(123L)
            .y(456L)
            .areaCd(areaCd)
            .trdStateCd(1L)
            .build();

        given(cafeRepository.findByAreaCdAndTrdStateCd(any(), any())).willReturn(Arrays.asList(cafe));
        given(commonConverter.convertToGeneric(any(), any())).willReturn(cafeDto);

        // when
        List<CafeDto> cafeDtos = cafeService.searchList(areaCd);

        // then
        cafeDtos.forEach(dto -> {
            assertEquals(dto.getCafeNm(), cafeNm);
            assertEquals(dto.getTrdStateCd(), 1L);
        });
    }
}