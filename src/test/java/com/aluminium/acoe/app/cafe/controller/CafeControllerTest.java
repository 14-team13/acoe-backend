package com.aluminium.acoe.app.cafe.controller;

import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.service.CafeService;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @WebMvcTest
 * - JPA 기능은 동작하지 않는다.
 * - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에만 집중할 수 있는 어노테이션
 * - @Controller, @ControllerAdvice 사용 가능
 * - 단, @Service, @Repository등은 사용할 수 없다.
 * */
@WebMvcTest(controllers= CafeController.class)
class CafeControllerTest {
    @Autowired
    /**
     * 웹 API 테스트할 때 사용
     * 스프링 MVC 테스트의 시작점
     * HTTP GET,POST 등에 대해 API 테스트 가능
     * */
    private MockMvc mockMvc;

    /*
     * 가짜 bean으로 올림.
     * */
    @MockBean
    private CafeService cafeService;

    protected MediaType mediaType =
        new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private static final String CAFE_NM = "mockCafe";

    @Test
    @WithMockUser
    void testSearchCafeList() throws Exception{
        CafeDto cafeDto = CafeDto.builder()
            .cafeId(11111L)
            .cafeNm(CAFE_NM)
            .x(123L)
            .y(456L)
            .areaCd(3120000L)
            .trdStateCd(1L)
            .build();
        // given
        given(cafeService.searchList(any()))
            .willReturn(Arrays.asList(cafeDto));
        // when then
        mockMvc.perform(get("/cafe/cafes/{areaCd}", 3120000).param("areaCd", String.valueOf(3120000)).contentType(mediaType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].cafeNm", is(CAFE_NM)));
    }

}