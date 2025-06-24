package com.example.demomaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CityCreateDTO {

    private Long stateId;

    @NotBlank(message = "City name must not be blank")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    private String cityName;

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
