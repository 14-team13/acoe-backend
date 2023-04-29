package com.aluminium.acoe.app.cafe.service.implementation;

import com.aluminium.acoe.app.cafe.Converter.CafeConverter;
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

    private final CafeConverter converter;

    @Override
    public List<CafeDto> searchList(Long areaCd, Long trdStateCd) {
        List<Cafe> result;

        if(trdStateCd == null){
            result = cafeRepository.findByAreaCd(areaCd);
        } else {
            result = cafeRepository.findByAreaCdAndTrdStateCd(areaCd, trdStateCd);
        }

        return result.stream().map(e -> converter.convertToGeneric(e, CafeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CafeDto> searchListByKeyword(String keyword) {
        return cafeRepository.findByCafeNmContains(keyword).stream()
            .filter(e -> e.getTrdStateCd() == 1L)
            .map(e -> converter.convertToGeneric(e, CafeDto.class)).collect(Collectors.toList());
    }

    @Override
    public CafeDto getCafe(Long cafeId) {
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
        }

        cafeDto.setMenuList(menuDtos);
        return cafeDto;
    }
}
