package com.event.cia103g1springboot.plan.planinfo.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanInfoService {
    @Autowired
    private PlanInfoRepository planInfoRepository;

    public void savePlanInfo(PlanInfo planInfo) {
        planInfoRepository.save(planInfo);
    }


    public List<PlanInfo> findByPlanTypeId(String planTypeId) {
        return planInfoRepository.findByPlanTypeId(planTypeId);
    }
    public PlanInfo findByPlanTypeIdAndPlanDay(String planTypeId, int planDay) {
        return planInfoRepository.findByPlanTypeIdAndPlanDay(planTypeId, planDay);
    }
    public List<PlanInfo> getAllPlanInfos() {
        return planInfoRepository.findAll();
    }
    // 保存行程內容
    public void save(PlanInfo planInfo) {
        planInfoRepository.save(planInfo);
    }
    public void delete(PlanInfo planInfo) {
        planInfoRepository.delete(planInfo);
    }

}
