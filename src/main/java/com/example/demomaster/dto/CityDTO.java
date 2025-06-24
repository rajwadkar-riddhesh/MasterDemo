package com.example.demomaster.dto;

import com.example.demomaster.enums.CityEnum;
import com.example.demomaster.enums.StateEnum;

import java.time.LocalDateTime;

public class CityDTO {
    private Long cityId;
    private String cityName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CityEnum status;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CityEnum getStatus() {
        return status;
    }

    public void setStatus(CityEnum status) {
        this.status = status;
    }
}
