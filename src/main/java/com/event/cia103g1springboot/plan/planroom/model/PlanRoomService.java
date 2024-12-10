package com.event.cia103g1springboot.plan.planroom.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanRoomService {
    @Autowired
    private PlanRoomRepository planRoomRepository;

    public PlanRoom save(PlanRoom planRoom) {return planRoomRepository.save(planRoom);}

    public List<PlanRoom> findAll() {return planRoomRepository.findAll();}

    public PlanRoom findByRmTypeIdAndPlanId(Integer roomTypeID,Integer planId) {
        return planRoomRepository.findByRoomTypeIdAndPlanId(roomTypeID,planId);
    }

    public PlanRoom findById(Integer id) {
        return planRoomRepository.findById(id).orElse(null);
    }

}
