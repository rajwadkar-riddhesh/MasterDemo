package com.example.demomaster.repository;

import com.example.demomaster.entity.PincodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeRepository extends JpaRepository<PincodeEntity, Long> {
    Page<PincodeEntity> findAll(Specification<PincodeEntity> specification, Pageable pageable);

    PincodeEntity findByPincodeIgnoreCase(String pincode);
}
