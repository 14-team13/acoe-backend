package com.aluminium.acoe.app.main.service.implementation;

import static com.aluminium.acoe.common.util.ApiUtil.callApi;
import com.aluminium.acoe.app.main.converter.CafeConverter;
import com.aluminium.acoe.app.main.dto.BlogDto;
import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.MenuDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.persistance.CafeRepository;
import com.aluminium.acoe.app.main.persistance.MenuRepository;
import com.aluminium.acoe.common.dto.ApiDto;
import com.aluminium.acoe.common.exception.BusinessInvalidValueException;
import com.aluminium.acoe.app.main.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService {
    private final MenuRepository menuRepository;

    private final CafeRepository cafeRepository;

    private final CafeConverter converter;

    @Value("${token.naver.id}")
    private String clientId;
    @Value("${token.naver.secret}")
    private String clientSecret;

    @Override
    public List<CafeDto> searchDtoList(Long areaCd, Long trdStateCd) {
        List<Cafe> result;

        if(trdStateCd == null){
            result = cafeRepository.findByAreaCd(areaCd);
        } else {
            result = cafeRepository.findByAreaCdAndTrdStateCd(areaCd, trdStateCd);
        }

        return result.stream().map(e -> converter.convertToGeneric(e, CafeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CafeDto> searchDtoListByKeyword(String keyword) {
        return cafeRepository.findByCafeNmContains(keyword).stream()
            .filter(e -> e.getTrdStateCd() == 1L)
            .map(e -> converter.convertToGeneric(e, CafeDto.class)).collect(Collectors.toList());
    }

    @Override
    public CafeDto getCafeDto(Long cafeId) {
        Objects.requireNonNull(cafeId, "조회/수정/삭제시 ID는 필수입니다.");

        // 카페 조회
        CafeDto cafeDto = converter.convertEntityToDto(
                cafeRepository.findById(cafeId).orElseThrow(() -> new BusinessInvalidValueException("유효하지 않은 cafeId : " + cafeId)));

        // 메뉴조회
        List<MenuDto> menuDtos;
        if(cafeDto.getFranchiseDto() == null){
            menuDtos = menuRepository.findByCafe_CafeId(cafeId)
                .stream().map(menu -> converter.convertToGeneric(menu, MenuDto.class))
                .collect(Collectors.toList());
        } else {
            menuDtos = menuRepository.findByFranchise_FranchiseId(cafeDto.getFranchiseDto().getFranchiseId())
                .stream().map(menu -> converter.convertToGeneric(menu, MenuDto.class))
                .collect(Collectors.toList());
            cafeDto.getFranchiseDto().setMenuList(menuDtos);
        }

        cafeDto.setMenuList(menuDtos);
        return cafeDto;
    }

    public List<BlogDto> searchBlogList(String keyword) throws IOException {
        Objects.requireNonNull(keyword, "검색어가 없습니다.");

        List<BlogDto> results = new ArrayList<>();
        ApiDto apiDto = buildApiDto(keyword);
        JSONArray blogList = callApi(apiDto).getJSONArray("items");

        BlogDto blogDto;
        for (int i = 0; i < blogList.length(); i++) {
            blogDto = new BlogDto();
            JSONObject row = blogList.getJSONObject(i);

            // setting values
            blogDto.setLink(Objects.toString(row.get("link"), ""));
            blogDto.setPostDt(Objects.toString(row.get("postdate"), ""));
            blogDto.setDesc(Objects.toString(row.get("description"), ""));
            blogDto.setTitle(Objects.toString(row.get("title"), ""));
            blogDto.setBlogLink(Objects.toString(row.get("bloggerlink"), ""));
            blogDto.setBlogNm(Objects.toString(row.get("bloggername"), ""));

            results.add(blogDto);
        }

        return results;
    }

    private ApiDto buildApiDto(String keyword) {
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        ApiDto apiDto = new ApiDto();
        apiDto.setUrl("https://openapi.naver.com/v1/search/blog.json?display=10&query=" + keyword);
        apiDto.setMethod("GET");
        apiDto.setContentType("application/json");
        apiDto.setRequestHeaders(requestHeaders);
        return apiDto;
    }


}
