package com.example.demomaster.Mapper;

import com.example.demomaster.dto.CityCreateDTO;
import com.example.demomaster.dto.CityDTO;
import com.example.demomaster.entity.CityEntity;
import com.example.demomaster.entity.StateEntity;
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

    default StateEntity map(Long stateId) {
        if (stateId == null) return null;
        StateEntity state = new StateEntity();
        state.setStateId(stateId);
        return state;
    }
}
