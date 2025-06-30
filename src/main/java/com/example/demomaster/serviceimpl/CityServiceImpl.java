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
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    @Override
    public void exportCityDetails(HttpServletResponse response) throws IOException {
        List<CityEntity> cities = cityRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cities");

        String[] columns = {"CityID", "CityName", "StateName", "StateId"};

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columns.length; col++){
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(columns[col]);
            cell.setCellStyle(headerStyle);
        }

        int rowInx = 1;
        for (CityEntity city : cities){
            Row row = sheet.createRow(rowInx++);
            row.createCell(0).setCellValue(city.getCityId());
            row.createCell(1).setCellValue(city.getCityName());
//            String stateId = city.getStateId() != null ? city.getStateId().getStateName() : "N/A";
            row.createCell(2).setCellValue(city.getStateId().getStateName());
            row.createCell(3).setCellValue(city.getStateId().getStateId());
        }

        response.setContentType("application/vnd.openxmlformats-officialdocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition","attachment; filename=cities.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }


    public void importCityDetails(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Excel File is Empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Only .xlsx files are supported");
        }

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();

        boolean headerSkipped = false;

        List<CityEntity> batch = new ArrayList<>();

        for (Row row : sheet) {
            if (!headerSkipped) {
                headerSkipped = true;
                continue;
            }
            if (isRowEmpty(row)) continue;

            Cell idCell = row.getCell(0);
            Cell nameCell = row.getCell(1);
            Cell sIdCell = row.getCell(2);

            String idText = idCell == null ? "" : formatter.formatCellValue(idCell).trim();
            String nameText = nameCell == null ? "" : formatter.formatCellValue(nameCell).trim();
            String sIdText = sIdCell == null ? "" : formatter.formatCellValue(sIdCell).trim();

            if (nameText.isBlank()) continue;

            CityEntity cityEntity;

            if (!idText.isBlank()) {
                try {
                    Long id = Long.valueOf(idText);
                    cityEntity = cityRepository.findById(id).orElse(new CityEntity());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid City ID format: " + idText);
                }
            } else {
                cityEntity = new CityEntity();
            }

            cityEntity.setCityName(nameText);

            if (!sIdText.isBlank()) {
                try {
                    Long sId = Long.valueOf(sIdText);
                    StateEntity state = stateRepository.findById(sId)
                            .orElseThrow(()->new RuntimeException("No State found"));
                    cityEntity.setStateId(state);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("No State ID format: " + sIdText);
                }
            } else {
                cityEntity = new CityEntity();
            }

            cityEntity.setStateId(cityEntity.getStateId());
            batch.add(cityEntity);
        }
        cityRepository.saveAll(batch);
    }

    private boolean isRowEmpty (Row row){
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
    }
}
