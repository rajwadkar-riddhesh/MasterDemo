package com.example.demomaster.specification;

import com.example.demomaster.dto.StateSearchDTO;
import com.example.demomaster.entity.StateEntity;
import com.example.demomaster.enums.StateEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime ;

public class StateSpecification {
//=====================================================Single Method============================================
//    public static Specification<StateEntity> getSpecification (StateSearchDTO stateSearchDTO){
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//              if (stateSearchDTO.getStateName() != null && !stateSearchDTO.getStateName().isEmpty()){
//                  predicates.add(cb.like(cb.lower(root.get("stateName")), "%" + stateSearchDTO.getStateName().toLowerCase() + "%"));
//              }
//
//              if(stateSearchDTO.getStatus() != null && !stateSearchDTO.getStatus().isEmpty()){
//                  StateEnum statusEnum = StateEnum.valueOf(stateSearchDTO.getStatus());
//                  predicates.add(cb.equal(root.get("status"), statusEnum));
//              }
//
//              if(stateSearchDTO.getCreatedAtFrom() != null && !stateSearchDTO.getCreatedAtFrom().isEmpty()){
//                  predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"),stateSearchDTO.getCreatedAtFrom()));
//              }
//
//              if(stateSearchDTO.getCreatedAtTo() != null && !stateSearchDTO.getCreatedAtTo().isEmpty()){
//                  predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), stateSearchDTO.getCreatedAtTo()));
//              }
//
//            predicates.add(cb.isNull(root.get("deletedAt")));
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }


//    If you want UTC instant, convert LocalDateTime  to Instant with ZoneOffset.UTC
//    Instant instant = createdAtFrom.toInstant(ZoneOffset.UTC);
//    String isoString = instant.toString();  // This is your ISO 8601 string with Z


    public static Specification<StateEntity> nameContains(String stateName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("stateName"), stateName);
    }
    public static Specification<StateEntity> hasStatus(StateEnum status){
        return (root,  query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<StateEntity> createdGreaterThanEqualTo(LocalDateTime createdAtFrom){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAtFrom);
    }

    public static Specification<StateEntity> createdLowerThanEqualTo(LocalDateTime  createdAtTo){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtTo);
    }


    public static Specification<StateEntity> StateHelper(StateSearchDTO dto) {
        Specification<StateEntity> specs = (root, query, cb) -> cb.conjunction();

        if (dto.getStateName() != null) {
            specs = specs.and(StateSpecification.nameContains(dto.getStateName()));
        }

        if (dto.getStatus() != null) {
            specs = specs.and(StateSpecification.hasStatus(dto.getStatus()));
        }

        if (dto.getCreatedAtFrom() != null) {
            specs = specs.and(StateSpecification.createdGreaterThanEqualTo(dto.getCreatedAtFrom()));
        }

        if (dto.getCreatedAtTo() != null) {
            specs = specs.and(StateSpecification.createdLowerThanEqualTo(dto.getCreatedAtTo()));

        }

        return specs;
    }

}
