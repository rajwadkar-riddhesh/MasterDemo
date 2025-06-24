package com.example.demomaster.Mapper;

import com.example.demomaster.dto.StateCreateDTO;
import com.example.demomaster.dto.StateDTO;
import com.example.demomaster.entity.StateEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface StateMapper {

    StateEntity toEntity(StateCreateDTO stateCreateDTO);

    StateDTO toDTO(StateEntity stateEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStateFromDto(StateCreateDTO stateCreateDTO, @MappingTarget StateEntity stateEntity);
}
