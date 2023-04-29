package com.aluminium.acoe.app.main.converter;

import com.aluminium.acoe.app.main.dto.CafeDto;
import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.entity.Cafe;
import com.aluminium.acoe.common.converter.CommonConverter;
import org.springframework.stereotype.Component;

@Component
public class CafeConverter extends CommonConverter {

    public CafeDto convertEntityToDto(Cafe cafe){
        FranchiseDto franchiseDto = convertToGeneric(cafe.getFranchise(), FranchiseDto.class);
        CafeDto cafeDto = convertToGeneric(cafe, CafeDto.class);
        cafeDto.setFranchiseDto(franchiseDto);

        return cafeDto;
    }

}
