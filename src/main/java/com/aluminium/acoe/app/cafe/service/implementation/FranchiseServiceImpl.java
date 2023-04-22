package com.aluminium.acoe.app.cafe.service.implementation;

import com.aluminium.acoe.app.cafe.dto.FranchiseDto;
import com.aluminium.acoe.app.cafe.persistance.FranchiseRepository;
import com.aluminium.acoe.app.cafe.service.FranchiseService;
import com.aluminium.acoe.common.converter.CommonConverter;
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
public class FranchiseServiceImpl implements FranchiseService {
    private final FranchiseRepository franchiseRepository;
    private final CommonConverter commonConverter;

    @Override
    public List<FranchiseDto> searchList(Boolean useYn) {
        if(useYn == null) useYn = false;
        return franchiseRepository.findAllByUseYn(useYn).stream()
                .map(entity -> commonConverter.convertToGeneric(entity, FranchiseDto.class))
                .collect(Collectors.toList());
    }
}
