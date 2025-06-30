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
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.loadtime.Options;
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

    @Override
    public void importFromExcel(MultipartFile file) throws IOException {

        if (file.isEmpty())throw new IllegalArgumentException("Excel File is Empty");

        String filename = file.getOriginalFilename();
        if (file == null || !filename.toLowerCase().endsWith(".xlsx")) throw new IllegalArgumentException("Only xlsx files are supported");

        Workbook workbook = new XSSFWorkbook((file.getInputStream()));

        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();

        boolean headerSkipped = false;

        List<PincodeEntity> pincode = new ArrayList<>();

        for (Row row : sheet){
            if (!headerSkipped){
                headerSkipped=true;
                continue;
            }

            if (isRowEmpty(row)) continue;

            Cell idCell = row.getCell(0);
            Cell pincodeCell = row.getCell(1);
            Cell cIdCell = row.getCell(2);

            String idText = idCell == null ? "" : formatter.formatCellValue(idCell).trim();
            String pincodeText = pincodeCell == null ? "" : formatter.formatCellValue(pincodeCell).trim();
            String cIdText = cIdCell == null ? "" : formatter.formatCellValue(cIdCell).trim();

            if(pincodeText.isBlank()) continue;

            PincodeEntity pincodeEntity;

            if (!idText.isBlank()){
                try{
                    Long id = Long.valueOf(idText);
                    pincodeEntity = pincodeRepository.findById(id).orElse(new PincodeEntity());
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid Pincode ID format:" + idText);
                }
            }else {
                pincodeEntity = new PincodeEntity();
            }

            pincodeEntity.setPincode(pincodeText);

            if(!cIdText.isBlank()){
                try {
                    Long cId = Long.valueOf(cIdText);
                    CityEntity entity = cityRepository.findById(cId)
                            .orElseThrow(()-> new IllegalArgumentException("No city found"));
                    pincodeEntity.setCityId(entity);
                }catch (NumberFormatException e){
                    throw new IllegalArgumentException("Invalid City ID:" + cIdText);
                }
            }

            pincodeEntity.setCityId(pincodeEntity.getCityId());
            pincode.add(pincodeEntity);
            }

        pincodeRepository.saveAll(pincode);
    }

    @Override
    public void exportToExcel(HttpServletResponse response) throws IOException{
        List<PincodeEntity> pincodes = pincodeRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("pincodes");

        String[] columns = {"PincodeId", "Pincode", "CityId", "CityName"};

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

        int rowIdx = 1;
        for (PincodeEntity pincode : pincodes ){
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(pincode.getPincodeId());
            row.createCell(1).setCellValue(pincode.getPincode());
            row.createCell(2).setCellValue(pincode.getCityId().getCityId());
            row.createCell(3).setCellValue(pincode.getCityId().getCityName());
        }

        response.setContentType("application/vnd.openxmlformats-officialdocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition","attachment; filename=pincodes.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();

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
