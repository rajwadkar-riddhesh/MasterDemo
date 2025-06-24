package com.example.demomaster.entity;

import com.example.demomaster.enums.CityEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "citydetails")
@SQLDelete(sql = "UPDATE citydetails SET deleted_at = current_timestamp, status = 'INACTIVE' WHERE city_id=?")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CityEnum status = CityEnum.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntity stateId;


    public CityEntity() {
    }

    public CityEntity(Long cityId, String cityName, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, CityEnum status, StateEntity stateId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.status = status;
        this.stateId = stateId;
    }

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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public CityEnum getStatus() {
        return status;
    }

    public void setStatus(CityEnum status) {
        this.status = status;
    }

    public StateEntity getStateId() {
        return stateId;
    }

    public void setStateId(StateEntity state) {
        this.stateId = state;
    }
}
