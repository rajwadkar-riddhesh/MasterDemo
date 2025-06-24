package com.example.demomaster.Mapper;

import com.example.demomaster.dto.CityCreateDTO;
import com.example.demomaster.dto.CityDTO;
import com.example.demomaster.entity.CityEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityEntity toEntity(CityCreateDTO cityCreateDTO);

    CityDTO toDTO(CityEntity cityEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCityFromDto(CityCreateDTO cityCreateDTO, @MappingTarget CityEntity cityEntity);
}
