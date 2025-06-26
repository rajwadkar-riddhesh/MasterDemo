package com.example.demomaster.controller;

import com.example.demomaster.dto.StateCreateDTO;
import com.example.demomaster.dto.StateDTO;
import com.example.demomaster.dto.StateSearchDTO;
import com.example.demomaster.enums.StateEnum;
import com.example.demomaster.serviceimpl.StateServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/state")
@Tag(name = "State API", description = "Operations related to state management")
public class StateController {

    @Autowired
    private StateServiceImpl stateService;

    @GetMapping
    @Operation(summary = "Get all state details",
            description = "Retrieve a paginated list of all state details")
    public List<StateDTO> getAllStateDetails(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size){
        return stateService.getAllStateDetails(page,size).getContent();
    }

    @GetMapping("/export")
    @Operation(summary = "Export all state details",
            description = "Exports all state details to excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        stateService.exportToExcel(response);
    }

    @PostMapping
    @Operation(summary = "Create state details",
            description = "Create new state details with the provided data")
    public StateDTO createStateDetails(@RequestBody StateCreateDTO stateCreateDTO){
        return stateService.createStateDetails(stateCreateDTO);
    }

    @PostMapping("/state/search")
    @Operation(summary = "Search state details",
            description = "Search state details based on filter criteria with pagination")
    public List<StateDTO> searchStateDeatails(
            @RequestBody StateSearchDTO searchDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       return stateService.searchStateDeatails(searchDTO, page, size).getContent();
    }

//    @PostMapping("/import")
//    @Operation(summary = "Insert state details",
//            description = "Insert state details from the provided excel sheet")
//    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file){
//        return.state
//    }

    @PutMapping("{stateId}")
    @Operation(summary = "Update state details",
            description = "Update existing state details by state ID")
    public StateDTO updateStateDetails(@PathVariable Long stateId, @RequestBody StateCreateDTO stateCreateDTO){
        return stateService.updateStateDetails(stateId,stateCreateDTO);
    }

    @DeleteMapping("{stateId}")
    @Operation(summary = "Delete state details",
            description = "Delete state details by state ID")
    public void deleteStateDetails(@PathVariable Long stateId){
        stateService.deleteStateDetails(stateId);
    }

    @PatchMapping("/{stateId}")
    @Operation(summary = "Patch state status",
            description = "Update state status by state ID")
    public StateDTO patchCityDetails(@PathVariable Long stateId, @RequestParam StateEnum status){
        return stateService.patchStateDetails(stateId, status);
    }
}
