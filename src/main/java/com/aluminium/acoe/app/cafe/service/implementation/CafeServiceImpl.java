package com.aluminium.acoe.app.cafe.service.implementation;

import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.dto.MenuDto;
import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.app.cafe.persistance.CafeRepository;
import com.aluminium.acoe.app.cafe.persistance.MenuRepository;
import com.aluminium.acoe.common.converter.CommonConverter;
import com.aluminium.acoe.common.exception.BusinessInvalidValueException;
import com.aluminium.acoe.app.cafe.service.CafeService;
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
public class CafeServiceImpl implements CafeService {
    private final MenuRepository menuRepository;

    private final CafeRepository cafeRepository;

    private final CommonConverter commonConverter;

    @Override
    public List<CafeDto> searchList(Long areaCd) {
        List<Cafe> result = cafeRepository.findByAreaCdAndTrdStateCd(areaCd, 1L);

        return result.stream().map(e -> commonConverter.convertToGeneric(e, CafeDto.class)).collect(Collectors.toList());
    }

    @Override
    public CafeDto getCafe(Long cafeId) {
        // 카페 조회
        CafeDto cafeDto = commonConverter.convertToGeneric(
                cafeRepository.findById(cafeId).orElseThrow(() -> new BusinessInvalidValueException("유효하지 않은 cafeId : " + cafeId))
                , CafeDto.class);

        // 메뉴 조회
        List<MenuDto> menuDtos = menuRepository.findByCafe_CafeId(cafeId)
                .stream().map(menu -> commonConverter.convertToGeneric(menu, MenuDto.class))
                .collect(Collectors.toList());

        cafeDto.setMenuList(menuDtos);
        return cafeDto;
    }
}
