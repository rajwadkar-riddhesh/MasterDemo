package com.example.demomaster.serviceimpl;

import com.example.demomaster.Mapper.CityMapper;
import com.example.demomaster.dto.*;
import com.example.demomaster.entity.CityEntity;
import com.example.demomaster.entity.StateEntity;
import com.example.demomaster.enums.CityEnum;
import com.example.demomaster.repository.CityRepository;
import com.example.demomaster.repository.StateRepository;
import com.example.demomaster.service.CityService;
import com.example.demomaster.specification.CitySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;
    private StateRepository stateRepository;
    private CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.cityMapper = cityMapper;
    }

    public Page<CityDTO> getAllCityDetails(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("cityId").ascending());
        return cityRepository.findAll(pageable).map(cityMapper::toDTO);
    }

    public CityDTO createCityDetails(CityCreateDTO cityCreateDTO) {
        StateEntity stateEntity = stateRepository.findById(cityCreateDTO.getStateId())
                .orElseThrow(()-> new RuntimeException("State not found"));
        CityEntity cityEntity = cityMapper.toEntity(cityCreateDTO);
        cityEntity.setStateId(stateEntity);
        return cityMapper.toDTO(cityRepository.save(cityEntity));
    }

    public Page<CityDTO> searchCityDeatails(CitySearchDTO citySearchDTO, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        Specification<CityEntity> spec = CitySpecification.CityHelper(citySearchDTO);

        Page<CityEntity> entities = cityRepository.findAll(spec, pageable);
        return entities.map(cityMapper::toDTO);
    }

    public CityDTO updateCityDetails(Long cityId,CityCreateDTO cityCreateDTO) {
        StateEntity stateEntity = stateRepository.findById(cityCreateDTO.getStateId())
                .orElseThrow(()->new RuntimeException("StateId not found"));
        CityEntity existingCity = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City details not found"));
        existingCity.setStateId(stateEntity);
        cityMapper.updateCityFromDto(cityCreateDTO, existingCity);
        return cityMapper.toDTO(cityRepository.save(existingCity));
    }

    public void deleteCityDetails(Long cityId) {
        CityEntity deleteCity = cityRepository.findById(cityId)
                .orElseThrow(()-> new RuntimeException("City details not found"));
        if(deleteCity.getStatus() == CityEnum.ACTIVE ){
            cityRepository.delete(deleteCity);
        }
    }

    public CityDTO patchCityDetails(Long cityId, CityEnum status) {
        CityEntity patchCity = cityRepository.findById(cityId)
                .orElseThrow(()-> new RuntimeException("City details not found"));
        if(patchCity.getStatus() != null){
            patchCity.setStatus(status);
        }
        CityEntity patchedCity = cityRepository.save(patchCity);
        return cityMapper.toDTO(patchedCity);
    }
}
