package com.example.demomaster.dto;

import com.example.demomaster.enums.StateEnum;

import java.time.LocalDateTime;

public class StateDTO {

    private Long stateId;
    private String stateName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private StateEnum status;

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public StateEnum getStatus() {
        return status;
    }

    public void setStatus(StateEnum status) {
        this.status = status;
    }
}
