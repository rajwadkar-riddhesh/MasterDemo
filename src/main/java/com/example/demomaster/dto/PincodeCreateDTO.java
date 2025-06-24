package com.example.demomaster.dto;

import com.example.demomaster.entity.CityEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PincodeCreateDTO {

    @NotNull
    private Long cityId;

    @NotBlank(message = "Pincode must not be blank")
    @Size(max = 6, message = "Pincode must be of 6 digits")
    private String pincode;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
