package com.aluminium.acoe.app.main.service.implementation;

import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.dto.MenuDto;
import com.aluminium.acoe.app.main.persistance.FranchiseRepository;
import com.aluminium.acoe.app.main.persistance.MenuRepository;
import com.aluminium.acoe.app.main.service.FranchiseService;
import com.aluminium.acoe.common.converter.CommonConverter;
import com.aluminium.acoe.common.exception.BusinessInvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {
    private final FranchiseRepository franchiseRepository;
    private final MenuRepository menuRepository;
    private final CommonConverter commonConverter;

    @Override
    public List<FranchiseDto> searchList(Boolean useYn) {
        return franchiseRepository.findAllByUseYn(useYn).stream()
                .map(entity -> commonConverter.convertToGeneric(entity, FranchiseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FranchiseDto getFranchise(Long franchiseId) {
        Objects.requireNonNull(franchiseId, "조회/수정/삭제시 Id는 필수입니다.");

        // 프랜차이즈 정보 조회
        FranchiseDto franchiseDto = commonConverter.convertToGeneric(franchiseRepository.findById(franchiseId)
            .orElseThrow(() -> new BusinessInvalidValueException("해당 ID에 대한 프랜차이즈 정보가 없습니다.")), FranchiseDto.class);

        // 메뉴 조회
        List<MenuDto> menuDtos = menuRepository.findByFranchise_FranchiseId(franchiseId)
            .stream().map(menu -> commonConverter.convertToGeneric(menu, MenuDto.class))
            .collect(Collectors.toList());

        franchiseDto.setMenuList(menuDtos);

        return franchiseDto;
    }
}
