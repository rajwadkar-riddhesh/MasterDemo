package com.example.demomaster.repository;

import com.example.demomaster.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<StateEntity,Long>, JpaSpecificationExecutor<StateEntity> {
}
