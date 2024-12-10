package com.event.cia103g1springboot.plan.plantype.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanTypeRepository extends JpaRepository<PlanType, String> {
    boolean existsByPlanTypeId(String planTypeId);
}
