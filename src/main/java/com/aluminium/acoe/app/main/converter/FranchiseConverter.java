package com.aluminium.acoe.app.main.converter;

import com.aluminium.acoe.app.main.dto.FranchiseDto;
import com.aluminium.acoe.app.main.resource.FranchiseResource;
import com.aluminium.acoe.common.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class FranchiseConverter extends CommonConverter {

    public FranchiseResource convertToResource(FranchiseDto franchiseDto){
        FranchiseResource franchiseResource = convertToGeneric(franchiseDto, FranchiseResource.class);
        franchiseResource.setLogoImg(Base64.getEncoder().encodeToString(franchiseDto.getLogoImg()));

        return franchiseResource;
    }

    public FranchiseDto convertToDto(FranchiseResource franchiseResource){
        FranchiseDto franchiseDto = convertToGeneric(franchiseResource, FranchiseDto.class);
        franchiseDto.setLogoImg(Base64.getDecoder().decode(franchiseResource.getLogoImg()));

        return franchiseDto;
    }

}
