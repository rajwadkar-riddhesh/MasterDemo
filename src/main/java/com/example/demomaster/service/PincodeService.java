package com.example.demomaster.service;

import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.dto.PincodeDTO;
import com.example.demomaster.dto.PincodeSearchDTO;
import com.example.demomaster.enums.PincodeEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PincodeService {

    Page<PincodeDTO> getAllPincodeDetails(int page, int size);
    
    PincodeDTO createPincodeDetails(PincodeCreateDTO pincodeCreateDTO);

    PincodeDTO updatePincodeDetails(Long pincodeId, PincodeCreateDTO pincodeCreateDTO);

    Page<PincodeDTO> searchPincodeDetails(PincodeSearchDTO pincodeSearchDTO, int page, int size);

    void deletePincodeDetails(Long pincodeId);

    PincodeDTO patchPincodeDetails(Long pincodeId, PincodeEnum status);
}
