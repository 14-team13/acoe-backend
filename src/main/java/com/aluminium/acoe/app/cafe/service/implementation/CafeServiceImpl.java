package com.aluminium.acoe.app.cafe.service.implementation;

import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.app.cafe.persistance.CafeRepository;
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

    private final CafeRepository cafeRepository;

    private final CommonConverter commonConverter;

    @Override
    public List<CafeDto> searchList(Long areaCd) {
        List<Cafe> result = cafeRepository.findByAreaCdAndTrdStateCd(areaCd, 1L);

        return result.stream().map(e -> commonConverter.convertToGeneric(e, CafeDto.class)).collect(Collectors.toList());
    }
}
