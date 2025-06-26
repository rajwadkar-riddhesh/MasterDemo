package com.example.demomaster.Mapper;

import com.example.demomaster.dto.CityCreateDTO;
import com.example.demomaster.dto.CityDTO;
import com.example.demomaster.entity.CityEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T16:15:22+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class CityMapperImpl implements CityMapper {

    @Override
    public CityEntity toEntity(CityCreateDTO cityCreateDTO) {
        if ( cityCreateDTO == null ) {
            return null;
        }

        CityEntity cityEntity = new CityEntity();

        cityEntity.setCityName( cityCreateDTO.getCityName() );
        cityEntity.setStateId( map( cityCreateDTO.getStateId() ) );

        return cityEntity;
    }

    @Override
    public CityDTO toDTO(CityEntity cityEntity) {
        if ( cityEntity == null ) {
            return null;
        }

        CityDTO cityDTO = new CityDTO();

        cityDTO.setCityId( cityEntity.getCityId() );
        cityDTO.setCityName( cityEntity.getCityName() );
        cityDTO.setCreatedAt( cityEntity.getCreatedAt() );
        cityDTO.setUpdatedAt( cityEntity.getUpdatedAt() );
        cityDTO.setStatus( cityEntity.getStatus() );

        return cityDTO;
    }

    @Override
    public void updateCityFromDto(CityCreateDTO cityCreateDTO, CityEntity cityEntity) {
        if ( cityCreateDTO == null ) {
            return;
        }

        if ( cityCreateDTO.getCityName() != null ) {
            cityEntity.setCityName( cityCreateDTO.getCityName() );
        }
        if ( cityCreateDTO.getStateId() != null ) {
            cityEntity.setStateId( map( cityCreateDTO.getStateId() ) );
        }
    }
}
