package com.example.demomaster.service;

import com.example.demomaster.dto.StateCreateDTO;
import com.example.demomaster.dto.StateDTO;
import com.example.demomaster.enums.StateEnum;
import org.springframework.data.domain.Page;

public interface StateService {
    public Page<StateDTO> getAllStateDetails(int page, int size);
    public StateDTO createStateDetails(StateCreateDTO stateCreateDTO);
    public StateDTO updateStateDetails(Long stateId, StateCreateDTO stateCreateDTO);
    public void deleteStateDetails(Long stateId);
    public StateDTO patchStateDetails(Long stateId, StateEnum status);
}
