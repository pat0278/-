package com.event.cia103g1springboot.plan.planinfo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanInfoRepository extends JpaRepository<PlanInfo, Integer> {
    List<PlanInfo> findByPlanTypeId(String planTypeId);
}
