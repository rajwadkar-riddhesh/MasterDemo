package com.example.demomaster.controller;

import com.example.demomaster.dto.PincodeCreateDTO;
import com.example.demomaster.dto.PincodeDTO;
import com.example.demomaster.dto.PincodeSearchDTO;
import com.example.demomaster.enums.PincodeEnum;
import com.example.demomaster.service.PincodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pincode")
@Tag(name = "Pincode API", description = "Operations related to pincode management")
public class PincodeController {

    @Autowired
    private PincodeService pincodeService;

    @GetMapping
    @Operation(summary = "Get all pincode details",
            description = "Retrieve a paginated list of all pincode details")
    public List<PincodeDTO> getAllPincodeDetails(@RequestParam(defaultValue = "0")int page,
                                                 @RequestParam(defaultValue = "5") int  size){
        return pincodeService.getAllPincodeDetails(page,size).getContent();
    }

    @PostMapping
    @Operation(summary = "Create pincode details",
            description = "Create new pincode details with provided data")
    public ResponseEntity<PincodeDTO> createPincodeDetails(@RequestBody PincodeCreateDTO pincodeCreateDTO){
        return ResponseEntity.ok(pincodeService.createPincodeDetails(pincodeCreateDTO));
    }

    @PutMapping("/{pincodeId}")
    @Operation(summary = "Update pincode details",
            description = "Update existing pincode details by pincode ID")
    public PincodeDTO updatePincodeDetails(@PathVariable Long pincodeId, PincodeCreateDTO pincodeCreateDTO){
        return pincodeService.updatePincodeDetails(pincodeId,pincodeCreateDTO);
    }

    @PostMapping("/search/{pincodeId}")
    @Operation(summary = "Search pincode details",
            description = "Search pincode details based on filter criteria with pagination")
    public List<PincodeDTO> searchPincodeDetails(@RequestBody PincodeSearchDTO pincodeSearchDTO,
                                                 @RequestParam(defaultValue = "0")int page,
                                                 @RequestParam(defaultValue = "5")int size){
        return pincodeService.searchPincodeDetails(pincodeSearchDTO,page,size).getContent();
    }

    @DeleteMapping("/{pincodeId}")
    @Operation(summary = "Delete pincode details",
            description = "Delete pincode details by pincode ID")
    public void deletePincodeDetails(@PathVariable Long pincodeId){
        pincodeService.deletePincodeDetails(pincodeId);
    }

    @PatchMapping("/{pincodeId}")
    @Operation(summary = "Patch pincode status",
            description = "Update pincode status by pincode ID")
    public PincodeDTO patchPincodeDetails(@PathVariable Long pincodeId, @RequestParam PincodeEnum status){
        return pincodeService.patchPincodeDetails(pincodeId,status);
    }

    @PostMapping(value = "/import", consumes = "multipart/form-data")
    @Operation(summary = "Import Pincode details",
            description = "Import Pincode details from provided excel")
    public void importFromExcel(@RequestParam MultipartFile file) throws IOException {
        pincodeService.importFromExcel(file);
    }

    @GetMapping("/export")
    @Operation(summary = "Export all pincode details",
            description = "Export paginated list of all pincode details to excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
        pincodeService.exportToExcel(response);
    }
}
