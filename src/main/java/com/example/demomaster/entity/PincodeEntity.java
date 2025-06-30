package com.example.demomaster.entity;

import com.example.demomaster.enums.PincodeEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "pincodedetails")
@SQLDelete(sql = "UPDATE pincodedetails SET deleted_at = current_timestamp, status = 'INACTIVE' WHERE pincode_id=?")
public class PincodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pincodeId;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "created_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PincodeEnum status = PincodeEnum.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity cityId;

    public PincodeEntity() {
    }

    public PincodeEntity(Long pincodeId, String pincode, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, PincodeEnum status, CityEntity cityId) {
        this.pincodeId = pincodeId;
        this.pincode = pincode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.status = status;
        this.cityId = cityId;
    }

    public Long getPincodeId() {
        return pincodeId;
    }

    public void setPincodeId(Long pincodeId) {
        this.pincodeId = pincodeId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public PincodeEnum getStatus() {
        return status;
    }

    public void setStatus(PincodeEnum status) {
        this.status = status;
    }

    public CityEntity getCityId() {
        return cityId;
    }

    public void setCityId(CityEntity city) {
        this.cityId = city;
    }
}
