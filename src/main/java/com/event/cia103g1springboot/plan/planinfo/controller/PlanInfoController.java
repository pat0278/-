package com.event.cia103g1springboot.plan.planinfo.controller;
import com.event.cia103g1springboot.plan.planinfo.model.PlanInfo;
import com.event.cia103g1springboot.plan.planinfo.model.PlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/planinfo")
public class PlanInfoController {

    @Autowired
    private PlanInfoService planInfoService;


    @GetMapping("/info/{planTypeId}")
    public String getPlanInfoByPlanTypeId(@PathVariable String planTypeId, Model model) {
        // 根據 planTypeId 查詢行程資訊
        List<PlanInfo> planInfos = planInfoService.findByPlanTypeId(planTypeId);

        if (planInfos.isEmpty()) {
            model.addAttribute("error", "找不到行程類型 ID 為 " + planTypeId + " 的行程資訊！");
            return "redirect:/plans/plantype/query"; // 返回頁面，顯示錯誤消息
        }

        // 將查詢結果傳遞到前端
        model.addAttribute("planInfos", planInfos);
        model.addAttribute("planTypeId", planTypeId);
        return "planinfo/list"; // 返回行程資訊列表頁面
    }
}



