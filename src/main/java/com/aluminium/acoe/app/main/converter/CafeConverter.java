package com.aluminium.acoe.app.main.converter;

import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.resource.CafeResource;
import com.aluminium.acoe.app.main.resource.MenuResource;
import com.aluminium.acoe.common.converter.CommonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CafeConverter extends CommonConverter {

    private final FranchiseConverter franchiseConverter;

    public CafeResource convertDtoToResource(CafeDto cafeDto){
        if(cafeDto == null) return null;

        CafeResource cafeResource = convertToGeneric(cafeDto, CafeResource.class);
        if(cafeDto.getMenuList() != null){
            cafeResource.setMenuList(cafeDto.getMenuList().stream()
                    .map(menuDto -> convertToGeneric(menuDto, MenuResource.class))
                    .collect(Collectors.toList()));
        }
        if(cafeDto.getFranchiseDto() != null){
            cafeResource.setFranchise(franchiseConverter.convertToResource(cafeDto.getFranchiseDto()));
        }
        return cafeResource;
    }

    public CafeDto convertEntityToDto(Cafe cafe){
        FranchiseDto franchiseDto = convertToGeneric(cafe.getFranchise(), FranchiseDto.class);
        CafeDto cafeDto = convertToGeneric(cafe, CafeDto.class);
        cafeDto.setFranchiseDto(franchiseDto);

        return cafeDto;
    }

}
