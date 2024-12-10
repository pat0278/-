package com.event.cia103g1springboot.plan.planroom.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRoomRepository extends JpaRepository<PlanRoom, Integer> {
    public PlanRoom findByRoomTypeIdAndPlanId(Integer roomTypeId, Integer planId);

    public List<PlanRoom> findByPlanId(Integer planId);
}
