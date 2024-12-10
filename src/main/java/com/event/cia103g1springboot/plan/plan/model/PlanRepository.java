package com.event.cia103g1springboot.plan.plan.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Integer> {
    @Query("SELECT p FROM Plan p " +
            "LEFT JOIN FETCH p.planOrder po " +
            "LEFT JOIN FETCH p.planType pt " +
            "WHERE p.planId = ?1")
    Optional<Plan> findPlanWithOrders(Integer planId);

    @Override
    Optional<Plan> findById(Integer integer);
}