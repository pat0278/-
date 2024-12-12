package com.event.cia103g1springboot.plan.plantype.controller;


import com.event.cia103g1springboot.plan.plantype.model.PlanType;
import com.event.cia103g1springboot.plan.plantype.model.PlanTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/plantype")
public class PlanTypeController {
    @Autowired
    private PlanTypeService planTypeService;

//    @PostMapping
//    public void createPlanType(@RequestBody PlanType planType) {
//        planTypeService.savePlanType(planType);
//    }

    @GetMapping("/{planTypeId}")
    public PlanType getPlanTypeById(@PathVariable String planTypeId) {
        return planTypeService.findPlanTypeById(planTypeId);
    }

    @GetMapping
    public List<PlanType> getAllPlanTypes() {
        return planTypeService.getAllPlanTypes();
    }



    @PostMapping("/add")
    public String addPlanType(@ModelAttribute PlanType planType, RedirectAttributes redirectAttributes) {
        try {
            // 驗證行程類型 ID 格式是否為大寫 A~Z
            if (!planType.getPlanTypeId().matches("^[A-Z]+$")) {
                redirectAttributes.addFlashAttribute("error", "新增失敗：行程類型 ID 僅能包含大寫英文字母 (A~Z)。");
                return "redirect:/plans/addplantype"; // 返回新增頁面
            }

            // 檢查行程類型 ID 是否重複
            PlanType existingPlanType = planTypeService.findPlanTypeById(planType.getPlanTypeId());
            if (existingPlanType != null) {
                redirectAttributes.addFlashAttribute("error", "新增失敗：行程類型 ID 已存在。");
                return "redirect:/plans/addplantype"; // 返回新增頁面
            }

            // 驗證行程名稱是否包含數字
            if (planType.getPlanName().matches(".*\\d.*")) {
                redirectAttributes.addFlashAttribute("error", "新增失敗：行程名稱不能包含數字。");
                return "redirect:/plans/addplantype"; // 返回新增頁面
            }

            // 驗證行程名稱是否以 "日遊" 結尾
            if (!planType.getPlanName().endsWith("日遊")) {
                redirectAttributes.addFlashAttribute("error", "新增失敗：行程名稱必須以『日遊』結尾。");
                return "redirect:/plans/addplantype"; // 返回新增頁面
            }

            // 新增行程類型
            planTypeService.updatePlanTypeDay(planType);
            redirectAttributes.addFlashAttribute("success", "新增成功！");
            return "redirect:/plans/plantype/query"; // 新增成功後跳轉到列表頁面
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "新增失敗：" + e.getMessage());
            return "redirect:/plans/addplantype"; // 返回新增頁面
        }
    }





    @PostMapping("/edit")
    public String saveEditedPlanType(@ModelAttribute PlanType planType, Model model) {
        try {
            // 檢查行程類型是否存在
            PlanType existingPlanType = planTypeService.findPlanTypeById(planType.getPlanTypeId());
            if (existingPlanType == null) {
                model.addAttribute("error", "修改失敗：行程類型 ID 不存在。");
                return "plantype/editplantype"; // 返回修改頁面
            }

            // 自動提取天數並更新
            planTypeService.updatePlanTypeDay(planType);

            // 返回成功消息
            model.addAttribute("success", "行程類型修改成功！");
            model.addAttribute("planType", planType);
            return "redirect:/plans/plantype/query"; // 跳轉到行程類型列表頁面
        } catch (Exception e) {
            model.addAttribute("error", "修改失敗：" + e.getMessage());
            return "plantype/editplantype"; // 返回修改頁面
        }
    }








//    @DeleteMapping("/{planTypeId}")
//    public void deletePlanTypeById(@PathVariable String planTypeId) {
//        planTypeService.deletePlanTypeById(planTypeId);
//    }
}
