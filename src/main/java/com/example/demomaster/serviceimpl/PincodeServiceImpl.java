package com.example.demomaster.serviceimpl;

import com.example.demomaster.Mapper.PincodeMapper;
import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.dto.PincodeDTO;
import com.example.demomaster.dto.PincodeSearchDTO;
import com.example.demomaster.entity.CityEntity;
import com.example.demomaster.entity.PincodeEntity;
import com.example.demomaster.enums.PincodeEnum;
import com.example.demomaster.repository.CityRepository;
import com.example.demomaster.repository.PincodeRepository;
import com.example.demomaster.service.PincodeService;
import com.example.demomaster.specification.PincodeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PincodeServiceImpl implements PincodeService {

    private PincodeRepository pincodeRepository;
    private CityRepository cityRepository;
    private PincodeMapper pincodeMapper;

    public PincodeServiceImpl(PincodeRepository pincodeRepository,CityRepository cityRepository ,PincodeMapper pincodeMapper) {
        this.pincodeRepository = pincodeRepository;
        this.pincodeMapper = pincodeMapper;
        this.cityRepository = cityRepository;
    }

    public Page<PincodeDTO> getAllPincodeDetails(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("pincodeId").ascending());
        return pincodeRepository.findAll(pageable).map(pincodeMapper::toDto);
    }

    public PincodeDTO createPincodeDetails(PincodeCreateDTO pincodeCreateDTO){
        CityEntity cityEntity = cityRepository.findById(pincodeCreateDTO.getCityId())
                .orElseThrow(()-> new RuntimeException("City not found"));
        PincodeEntity pincodeEntity = pincodeMapper.toEntity(pincodeCreateDTO);
        pincodeEntity.setCityId(cityEntity);
        return pincodeMapper.toDto(pincodeRepository.save(pincodeEntity));
    }

    public PincodeDTO updatePincodeDetails(Long pincodeId, PincodeCreateDTO pincodeCreateDTO){
        CityEntity cityEntity =cityRepository.findById(pincodeCreateDTO.getCityId())
                .orElseThrow(()->new RuntimeException("CityId not found"));
        PincodeEntity existingEntity = pincodeRepository.findById(pincodeId)
                .orElseThrow(()->new RuntimeException("Pincode details not found"));
        existingEntity.setCityId(cityEntity);
        pincodeMapper.updatePincodeFromDto(pincodeCreateDTO,existingEntity);
        return pincodeMapper.toDto(pincodeRepository.save(existingEntity));
    }

    public Page<PincodeDTO> searchPincodeDetails(PincodeSearchDTO pincodeSearchDTO, int page, int size){
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdAt").ascending());
        Specification<PincodeEntity> specification = PincodeSpecification.specificationHelper(pincodeSearchDTO);
        Page<PincodeEntity> pincodeEntityPage = pincodeRepository.findAll(specification,pageable);
        return pincodeEntityPage.map(pincodeMapper::toDto);
    }

    public void deletePincodeDetails(Long pincodeId){
        PincodeEntity pincodeEntity = pincodeRepository.findById(pincodeId)
                .orElseThrow(()->new RuntimeException("Pincode details not found"));
        pincodeRepository.delete(pincodeEntity);
    }

    public PincodeDTO patchPincodeDetails(Long pincodeId, PincodeEnum status){
        PincodeEntity patchPincode = pincodeRepository.findById(pincodeId)
                .orElseThrow(()-> new RuntimeException("Pincode details not found"));
            patchPincode.setStatus(status);
        return pincodeMapper.toDto(pincodeRepository.save(patchPincode));
    }
}
