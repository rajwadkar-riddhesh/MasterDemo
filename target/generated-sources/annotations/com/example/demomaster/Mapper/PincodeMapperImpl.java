package com.example.demomaster.Mapper;

import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.dto.PincodeDTO;
import com.example.demomaster.entity.PincodeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T16:15:22+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class PincodeMapperImpl implements PincodeMapper {

    @Override
    public PincodeEntity toEntity(PincodeCreateDTO pincodeCreateDTO) {
        if ( pincodeCreateDTO == null ) {
            return null;
        }

        PincodeEntity pincodeEntity = new PincodeEntity();

        pincodeEntity.setPincode( pincodeCreateDTO.getPincode() );
        pincodeEntity.setCityId( map( pincodeCreateDTO.getCityId() ) );

        return pincodeEntity;
    }

    @Override
    public PincodeDTO toDto(PincodeEntity pincodeEntity) {
        if ( pincodeEntity == null ) {
            return null;
        }

        PincodeDTO pincodeDTO = new PincodeDTO();

        pincodeDTO.setPincodeId( pincodeEntity.getPincodeId() );
        pincodeDTO.setPincode( pincodeEntity.getPincode() );
        pincodeDTO.setCreatedAt( pincodeEntity.getCreatedAt() );
        pincodeDTO.setUpdatedAt( pincodeEntity.getUpdatedAt() );
        pincodeDTO.setDeletedAt( pincodeEntity.getDeletedAt() );
        pincodeDTO.setStatus( pincodeEntity.getStatus() );

        return pincodeDTO;
    }

    @Override
    public void updatePincodeFromDto(PincodeCreateDTO pincodeCreateDTO, PincodeEntity pincodeEntity) {
        if ( pincodeCreateDTO == null ) {
            return;
        }

        if ( pincodeCreateDTO.getPincode() != null ) {
            pincodeEntity.setPincode( pincodeCreateDTO.getPincode() );
        }
        if ( pincodeCreateDTO.getCityId() != null ) {
            pincodeEntity.setCityId( map( pincodeCreateDTO.getCityId() ) );
        }
    }
}
