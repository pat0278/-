package com.event.cia103g1springboot.plan.plantype.model;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlanTypeService {
    @Autowired
    private PlanTypeRepository planTypeRepository;

    public void savePlanType(PlanType planType) {
        planTypeRepository.save(planType);
    }

    public PlanType findPlanTypeById(String planTypeId) {
        return planTypeRepository.findById(planTypeId).orElse(null);
    }

    public List<PlanType> getAllPlanTypes() {
        return planTypeRepository.findAll();
    }

    public void deletePlanTypeById(String planTypeId) {
        planTypeRepository.deleteById(planTypeId);
    }

    public boolean existsByPlanTypeId(String planTypeId) {
        return planTypeRepository.existsByPlanTypeId(planTypeId);
    }
    public Page<PlanType> getPlanTypesByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return planTypeRepository.findAll(pageable);
    }
    public int extractDayFromName(String planName) {
        Map<Character, Integer> chineseNumberMap = Map.of(
                '一', 1, '二', 2, '三', 3, '四', 4,
                '五', 5, '六', 6, '七', 7, '八', 8,
                '九', 9, '十', 10
        );

        if (planName.contains("日遊")) {
            String prefix = planName.split("日遊")[0];
            int day = 0;

            for (char c : prefix.toCharArray()) {
                if (chineseNumberMap.containsKey(c)) {
                    day = day * 10 + chineseNumberMap.get(c); // 支持十位數
                }
            }
            return day;
        }

        throw new IllegalArgumentException("行程名稱格式錯誤，無法提取天數！");
    }

    public void updatePlanTypeDay(PlanType planType) {
        int days = extractDayFromName(planType.getPlanName());
        planType.setPlanDay(days);
        planTypeRepository.save(planType);
    }
}
