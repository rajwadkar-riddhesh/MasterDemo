package com.example.demomaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class StateCreateDTO {

    @NotBlank(message = "State name must not be blank")
    @Size(min = 2, max = 100, message = "State name must be between 2 and 100 characters")
    private String stateName;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


}

