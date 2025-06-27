package com.example.demomaster.service;

import com.example.demomaster.dto.CityCreateDTO;
import com.example.demomaster.dto.CityDTO;
import com.example.demomaster.dto.CitySearchDTO;
import com.example.demomaster.enums.CityEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CityService {

    public Page<CityDTO> getAllCityDetails(int page, int size);

    public CityDTO createCityDetails(CityCreateDTO cityCreateDTO);

    public Page<CityDTO> searchCityDeatails(CitySearchDTO citySearchDTO, int page, int size);

    public CityDTO updateCityDetails(Long cityId,CityCreateDTO cityCreateDTO);

    public void deleteCityDetails(Long cityId);

    public CityDTO patchCityDetails(Long cityId, CityEnum status);

    void exportCityDetails(HttpServletResponse response) throws IOException;

    void importCityDetails(MultipartFile file) throws IOException;
}
