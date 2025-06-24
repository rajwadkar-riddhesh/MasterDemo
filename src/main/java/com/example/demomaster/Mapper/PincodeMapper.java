package com.example.demomaster.Mapper;

import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.dto.PincodeDTO;
import com.example.demomaster.entity.PincodeEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PincodeMapper {

    PincodeEntity toEntity(PincodeCreateDTO pincodeCreateDTO);

    PincodeDTO toDto(PincodeEntity pincodeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePincodeFromDto(PincodeCreateDTO pincodeCreateDTO, @MappingTarget PincodeEntity pincodeEntity);
}
