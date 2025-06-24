package com.example.demomaster.Mapper;

import com.example.demomaster.dto.StateCreateDTO;
import com.example.demomaster.dto.StateDTO;
import com.example.demomaster.entity.StateEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-23T18:44:32+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class StateMapperImpl implements StateMapper {

    @Override
    public StateEntity toEntity(StateCreateDTO stateCreateDTO) {
        if ( stateCreateDTO == null ) {
            return null;
        }

        StateEntity stateEntity = new StateEntity();

        stateEntity.setStateName( stateCreateDTO.getStateName() );

        return stateEntity;
    }

    @Override
    public StateDTO toDTO(StateEntity stateEntity) {
        if ( stateEntity == null ) {
            return null;
        }

        StateDTO stateDTO = new StateDTO();

        stateDTO.setStateId( stateEntity.getStateId() );
        stateDTO.setStateName( stateEntity.getStateName() );
        stateDTO.setCreatedAt( stateEntity.getCreatedAt() );
        stateDTO.setUpdatedAt( stateEntity.getUpdatedAt() );
        stateDTO.setStatus( stateEntity.getStatus() );

        return stateDTO;
    }

    @Override
    public void updateStateFromDto(StateCreateDTO stateCreateDTO, StateEntity stateEntity) {
        if ( stateCreateDTO == null ) {
            return;
        }

        if ( stateCreateDTO.getStateName() != null ) {
            stateEntity.setStateName( stateCreateDTO.getStateName() );
        }
    }
}
