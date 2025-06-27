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

    @Override
    public void exportToExcel(HttpServletResponse response) throws IOException {

            List<StateEntity> states = stateRepository.findAll();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("States");

            String[] columns = {"State ID", "State Name"};
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerStyle);
            }

            int rowIdx = 1;
            for (StateEntity state : states) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(state.getStateId());
                row.createCell(1).setCellValue(state.getStateName());
                row.createCell(1).setCellValue(state.getStateName());
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=states.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();
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

    public void importFromExcel(MultipartFile file) throws IOException{
        if (file.isEmpty()){
            throw new IllegalArgumentException("Excel file is empty");
        }

        String filename = file.getOriginalFilename();
        if(filename == null || !filename.toLowerCase().endsWith(".xlsx")){
            throw new IllegalArgumentException("Only .xlsx files are supported");
        }

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            boolean headerSkipped = false;

            List<StateEntity> batch = new ArrayList<>();

            for (Row row : sheet) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                if (isRowEmpty(row)) continue;

                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);

                String idText = idCell == null ? "" : formatter.formatCellValue(idCell).trim();
                String nameText = nameCell == null ? "" : formatter.formatCellValue(nameCell).trim();

                if (nameText.isBlank()) continue;

                StateEntity entity;

                if (!idText.isBlank()) {
                    try {
                        Long id = Long.valueOf(idText);
                        entity = stateRepository.findById(id).orElse(null);
                        if (entity == null) {
                            entity = new StateEntity();
                            // Do NOT set ID manually if entity is new
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid ID format in Excel row: " + idText);
                    }
                } else {
                    entity = new StateEntity();
                }
                entity.setStateName(nameText);
                batch.add(entity);
            }
            stateRepository.saveAll(batch);
    }
//      public void importToExcel(MultipartFile file){
//
//      }

    private boolean isRowEmpty(Row row) {
        if (row == null){
            return true;
        }
        for (Cell cell : row){
                if (cell != null && cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
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
