package com.event.cia103g1springboot.plan.planinfo.controller;
import com.event.cia103g1springboot.plan.planinfo.model.PlanInfo;
import com.event.cia103g1springboot.plan.planinfo.model.PlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/planinfo")
public class PlanInfoController {

    @Autowired
    private PlanInfoService planInfoService;

    @PostMapping("/saveInfo")
    public String savePlanInfo(@RequestParam Map<String, String> formData, @RequestParam("planTypeId") String planTypeId) {
        try {
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // 篩選出 day 開頭的鍵值
                if (key.startsWith("day-")) {
                    int day = Integer.parseInt(key.replace("day-", ""));
                    PlanInfo existingInfo = planInfoService.findByPlanTypeIdAndPlanDay(planTypeId, day);

                    if (value != null && !value.trim().isEmpty()) {
                        // 如果有值，更新或新增
                        if (existingInfo != null) {
                            existingInfo.setPlanCon(value);
                            planInfoService.save(existingInfo);
                        } else {
                            PlanInfo newInfo = new PlanInfo();
                            newInfo.setPlanTypeId(planTypeId);
                            newInfo.setPlanDay(day);
                            newInfo.setPlanCon(value);
                            planInfoService.save(newInfo);
                        }
                    } else if (existingInfo != null) {
                        // 如果值為空且記錄已存在，刪除記錄
                        planInfoService.delete(existingInfo);
                    }
                }
            }

            // 重定向回行程類別列表頁面
            return "redirect:/plans/plantype/query";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/plans/plantype/query?error=保存失敗：" + e.getMessage();
        }
    }


}



