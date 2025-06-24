package com.example.demomaster.specification;

import com.example.demomaster.dto.PincodeSearchDTO;
import com.example.demomaster.entity.PincodeEntity;
import com.example.demomaster.enums.PincodeEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PincodeSpecification {

    public static Specification<PincodeEntity> pincodeContains(String pincode){
         return (root, query, criteriaBuilder) ->
                 criteriaBuilder.equal(root.get("pincode"), pincode);
    }

    public static Specification<PincodeEntity> hasStatus(PincodeEnum status){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<PincodeEntity> createdGreaterThanEqualTo(LocalDateTime createdAtFrom){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAtFrom);
    }

    public static Specification<PincodeEntity> createdLowerThanEqualTo(LocalDateTime createdAtTo){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtTo);
    }

    public static Specification<PincodeEntity> specificationHelper(PincodeSearchDTO pincodeSearchDTO){
        Specification<PincodeEntity> specs = (root, query, criteriaBuilder) ->
            criteriaBuilder.conjunction();

        if(pincodeSearchDTO.getPincode() != null){
            specs = specs.and(PincodeSpecification.pincodeContains(pincodeSearchDTO.getPincode()));
        }

        if(pincodeSearchDTO.getStatus() != null){
            specs = specs.and(PincodeSpecification.hasStatus(pincodeSearchDTO.getStatus()));
        }

        if(pincodeSearchDTO.getCreatedAtFrom() != null){
            specs = specs.and(PincodeSpecification.createdGreaterThanEqualTo(pincodeSearchDTO.getCreatedAtFrom()));
        }

        if(pincodeSearchDTO.getCreatedAtTo() != null){
            specs = specs.and(PincodeSpecification.createdLowerThanEqualTo(pincodeSearchDTO.getCreatedAtTo()));
        }

        return specs;
    }
}