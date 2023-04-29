package com.aluminium.acoe.app.cafe.Converter;

import com.aluminium.acoe.app.cafe.dto.CafeDto;
import com.aluminium.acoe.app.cafe.dto.FranchiseDto;
import com.aluminium.acoe.app.cafe.entity.Cafe;
import com.aluminium.acoe.common.converter.CommonConverter;

public class CafeConverter extends CommonConverter {

    public CafeDto convertEntityToDto(Cafe cafe){
        FranchiseDto franchiseDto = convertToGeneric(cafe.getFranchise(), FranchiseDto.class);
        CafeDto cafeDto = convertToGeneric(cafe, CafeDto.class);
        cafeDto.setFranchiseDto(franchiseDto);

        return cafeDto;
    }

}
