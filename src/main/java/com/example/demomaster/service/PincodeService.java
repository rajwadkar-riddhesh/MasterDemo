package com.example.demomaster.service;

import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.dto.PincodeDTO;
import com.example.demomaster.dto.PincodeSearchDTO;
import com.example.demomaster.enums.PincodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PincodeService {

    Page<PincodeDTO> getAllPincodeDetails(int page, int size);
    
    PincodeDTO createPincodeDetails(PincodeCreateDTO pincodeCreateDTO);

    PincodeDTO updatePincodeDetails(Long pincodeId, PincodeCreateDTO pincodeCreateDTO);

    Page<PincodeDTO> searchPincodeDetails(PincodeSearchDTO pincodeSearchDTO, int page, int size);

    void deletePincodeDetails(Long pincodeId);

    PincodeDTO patchPincodeDetails(Long pincodeId, PincodeEnum status);

    void importFromExcel(MultipartFile file) throws IOException;

    void exportToExcel(HttpServletResponse response) throws IOException;
}
