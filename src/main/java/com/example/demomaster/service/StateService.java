package com.example.demomaster.service;

import com.example.demomaster.dto.StateCreateDTO;
import com.example.demomaster.dto.StateDTO;
import com.example.demomaster.enums.StateEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StateService {
    public Page<StateDTO> getAllStateDetails(int page, int size);
    public StateDTO createStateDetails(StateCreateDTO stateCreateDTO);
    public StateDTO updateStateDetails(Long stateId, StateCreateDTO stateCreateDTO);
    public void deleteStateDetails(Long stateId);
    public StateDTO patchStateDetails(Long stateId, StateEnum status);

    void exportToExcel(HttpServletResponse response) throws IOException;

    void importFromExcel(MultipartFile file) throws IOException;
}
