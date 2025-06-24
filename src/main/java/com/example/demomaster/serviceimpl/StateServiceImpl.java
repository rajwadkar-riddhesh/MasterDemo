package com.example.demomaster.serviceimpl;

import com.example.demomaster.Mapper.StateMapper;
import com.example.demomaster.dto.StateCreateDTO;
import com.example.demomaster.dto.StateDTO;
import com.example.demomaster.dto.StateSearchDTO;
import com.example.demomaster.entity.StateEntity;
import com.example.demomaster.enums.StateEnum;
import com.example.demomaster.repository.StateRepository;
import com.example.demomaster.service.StateService;
import com.example.demomaster.specification.StateSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime ;

@Service
public class StateServiceImpl implements StateService {

    private StateRepository stateRepository;

    private StateMapper stateMapper;

    public StateServiceImpl(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    public Page<StateDTO> getAllStateDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("stateId").ascending());
        return stateRepository.findAll(pageable).map(stateMapper::toDTO);
    }

    public StateDTO createStateDetails(StateCreateDTO stateCreateDTO) {
        StateEntity stateEntity = stateMapper.toEntity(stateCreateDTO);
        return stateMapper.toDTO(stateRepository.save(stateEntity));
    }

    public Page<StateDTO> searchStateDeatails(StateSearchDTO searchDTO, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        Specification<StateEntity> spec = StateSpecification.StateHelper(searchDTO);

        Page<StateEntity> entities = stateRepository.findAll(spec, pageable);
        return entities.map(stateMapper::toDTO);
    }

    public StateDTO updateStateDetails(Long stateId, StateCreateDTO stateCreateDTO) {
        StateEntity existingState = stateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("State details not found"));

        stateMapper.updateStateFromDto(stateCreateDTO,existingState);
        return stateMapper.toDTO(stateRepository.save(existingState));
    }

    public void deleteStateDetails(Long stateId) {
        StateEntity deleteState = stateRepository.findById(stateId)
                .orElseThrow(()-> new RuntimeException("State details not found"));
        if(deleteState.getStatus() == StateEnum.ACTIVE ){
            stateRepository.delete(deleteState);
        }
    }


    public StateDTO patchStateDetails(Long stateId, StateEnum status) {
       StateEntity patchState = stateRepository.findById(stateId)
            .orElseThrow(()-> new RuntimeException("City details not found"));
        if(patchState.getStatus() != null){
        patchState.setStatus(status);
    }
    StateEntity patchedState = stateRepository.save(patchState);
    return stateMapper.toDTO(patchedState);
}
}
