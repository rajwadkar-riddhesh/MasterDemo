package com.example.demomaster.specification;

import com.example.demomaster.dto.CitySearchDTO;
import com.example.demomaster.dto.PincodeSearchDTO;
import com.example.demomaster.entity.CityEntity;
import com.example.demomaster.entity.PincodeEntity;
import com.example.demomaster.enums.CityEnum;
import com.example.demomaster.enums.PincodeEnum;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitySpecification {
//===================================================Single method==============================================
//    public static Specification<CityEntity> getSpecification (CitySearchDTO citySearchDTO){
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (citySearchDTO.getCityName() != null && !citySearchDTO.getCityName().isEmpty()){
//                predicates.add(cb.like(cb.lower(root.get("cityName")), "%" + citySearchDTO.getCityName().toLowerCase() + "%"));
//            }
//
//            if(citySearchDTO.getStatus() != null){
//                CityEnum statusEnum = citySearchDTO.getStatus();
//                predicates.add(cb.equal(root.get("status"), statusEnum));
//            }
//
//            if(citySearchDTO.getCreatedAtFrom() != null){
//                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"),citySearchDTO.getCreatedAtFrom()));
//            }
//
//            if(citySearchDTO.getCreatedAtTo() != null){
//                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), citySearchDTO.getCreatedAtTo()));
//            }
//
//            predicates.add(cb.isNull(root.get("deletedAt")));
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }

    public static Specification<CityEntity> nameContains(String cityName){

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cityName"), cityName);
    }

    public static Specification<CityEntity> hasStatus(CityEnum status){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<CityEntity> createdGreaterThanEqualTo(LocalDateTime createdAtFrom){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAtFrom);
    }

    public static Specification<CityEntity> createdLowerThanEqualTo(LocalDateTime createdAtTo){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtTo);
    }

    public static Specification<CityEntity> CityHelper(CitySearchDTO citySearchDTO){
        Specification<CityEntity> specs = (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();

        if(citySearchDTO.getCityName() != null){
            specs = specs.and(CitySpecification.nameContains(citySearchDTO.getCityName()));
        }

        if(citySearchDTO.getStatus() != null){
            specs = specs.and(CitySpecification.hasStatus(citySearchDTO.getStatus()));
        }

        if(citySearchDTO.getCreatedAtFrom() != null){
            specs = specs.and(CitySpecification.createdGreaterThanEqualTo(citySearchDTO.getCreatedAtFrom()));
        }

        if(citySearchDTO.getCreatedAtTo() != null){
            specs = specs.and(CitySpecification.createdLowerThanEqualTo(citySearchDTO.getCreatedAtTo()));
        }

        return specs;
    }
}
