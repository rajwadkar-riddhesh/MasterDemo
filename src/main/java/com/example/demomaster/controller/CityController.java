package com.example.demomaster.controller;

import com.example.demomaster.dto.*;
import com.example.demomaster.enums.CityEnum;
import com.example.demomaster.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
@Tag(name = "City API", description = "Operations related to city management")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    @Operation(summary = "Get all city details",
            description = "Retrieve a paginated list of all city details")
    public List<CityDTO> getAllCityDetails(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size){
        return cityService.getAllCityDetails(page,size).getContent();
    }

    @PostMapping
    @Operation(summary = "Create city details",
            description = "Create new city details with the provided data")
    public CityDTO createCityDetails(@RequestBody CityCreateDTO cityCreateDTO){
        return cityService.createCityDetails(cityCreateDTO);
    }

    @PostMapping("/search")
    @Operation(summary = "Search city details",
            description = "Search city details based on filter criteria with pagination")
    public List<CityDTO> searchCityDeatails(
            @RequestBody CitySearchDTO citySearchDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return cityService.searchCityDeatails(citySearchDTO, page, size).getContent();
    }

    @PutMapping("/{cityId}")
    @Operation(summary = "Update city details",
            description = "Update existing city details by city ID")
    public CityDTO updateCityDetails(@PathVariable Long cityId,@RequestBody CityCreateDTO cityCreateDTO){
        return cityService.updateCityDetails(cityId,cityCreateDTO);
    }

    @DeleteMapping("/{cityId}")
    @Operation(summary = "Delete city details",
            description = "Delete city details by city ID")
    public void deleteCityDetails(@PathVariable Long cityId){
        cityService.deleteCityDetails(cityId);
    }

    @PatchMapping("/{cityId}")
    @Operation(summary = "Patch city status",
            description = "Update city status by city ID")
    public CityDTO patchCityDetails(@PathVariable Long cityId, @RequestParam CityEnum status){
        return cityService.patchCityDetails(cityId, status);
    }
}
