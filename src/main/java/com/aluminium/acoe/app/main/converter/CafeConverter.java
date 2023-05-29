package com.aluminium.acoe.app.main.converter;

import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.app.main.resource.CafeResource;
import com.aluminium.acoe.common.converter.CommonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeConverter extends CommonConverter {

    private final FranchiseConverter franchiseConverter;

    public CafeResource convertDtoToResource(CafeDto cafeDto){
        if(cafeDto == null) return null;

        CafeResource cafeResource = convertToGeneric(cafeDto, CafeResource.class);
        if(cafeDto.getMenuList() != null){
            cafeResource.setMenuList(cafeDto.getMenuList());
        }
        if(cafeDto.getFranchiseDto() != null){
            cafeResource.setFranchise(franchiseConverter.convertToResource(cafeDto.getFranchiseDto()));
        }
        return cafeResource;
    }

    public CafeDto convertEntityToDto(Cafe cafe){
        CafeDto cafeDto = convertToGeneric(cafe, CafeDto.class);
        if(cafe.getFranchise() != null){
            cafeDto.setFranchiseDto(convertToGeneric(cafe.getFranchise(), FranchiseDto.class));
        }
        return cafeDto;
    }

}
