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


//
//    @PostMapping("/add")
//    public String addPlanType(@ModelAttribute PlanType planType, Model model, RedirectAttributes redirectAttributes) {
//        // 1. 檢查行程名稱中的天數是否小於指定的天數
//        int extractedDays = planTypeService.extractDayFromName(planType.getPlanName());
//        if (planType.getPlanDay() > extractedDays) {
//            redirectAttributes.addFlashAttribute("alertMessage", "新增行程類別失敗：行程天數不能大於名稱中定義的天數。");
//            return "redirect:/plantype/addplantype"; // 返回到新增頁面
//        }
//
//        // 2. 檢查行程類型 ID 是否重複
//        PlanType existingPlanType = planTypeService.findPlanTypeById(planType.getPlanTypeId());
//        if (existingPlanType != null) {
//            redirectAttributes.addFlashAttribute("alertMessage", "新增行程類別失敗：行程類型 ID 已存在。");
//            return "redirect:/plantype/addplantype"; // 返回到新增頁面
//        }
//
//        try {
//            // 3. 保存行程類別
//            planTypeService.savePlanType(planType);
//
//            // 4. 保存成功，跳轉到列表頁
//            redirectAttributes.addFlashAttribute("alertMessage", "新增行程類別成功！");
//            return "redirect:/plantype/query"; // 跳轉到行程類別列表頁面
//        } catch (Exception e) {
//            // 5. 保存失敗，返回錯誤消息
//            redirectAttributes.addFlashAttribute("alertMessage", "新增行程類別失敗，請檢查輸入的資料是否正確：" + e.getMessage());
//            return "redirect:/plantype/addplantype"; // 返回到新增頁面
//        }
//    }

//    @PostMapping("/add")
//    public String addPlanType(@ModelAttribute PlanType planType, Model model) {
//        try {
//            // 驗證 planTypeId 格式是否為大寫 A~Z
//            if (!planType.getPlanTypeId().matches("^[A-Z]+$")) {
//                model.addAttribute("error", "新增失敗：行程類型 ID 僅能包含大寫英文字母 (A~Z)。");
//                return "redirect:/plans/addplantype"; // 返回新增頁面
//            }
//
//            // 檢查是否有重複的行程類型 ID
//            PlanType existingPlanType = planTypeService.findPlanTypeById(planType.getPlanTypeId());
//            if (existingPlanType != null) {
//                model.addAttribute("error", "新增失敗：行程類型 ID 已存在。");
//                return "redirect:/plans/addplantype"; // 返回新增頁面
//            }
//
//            // 自動提取天數並更新
//            planTypeService.updatePlanTypeDay(planType);
//
//            // 返回成功消息
//            model.addAttribute("success", "新增行程類別成功！");
//            return "redirect:/plans/plantype/query"; // 新增成功後跳轉到列表頁面
//        } catch (Exception e) {
//            // 返回錯誤消息
//            model.addAttribute("error", "新增失敗：" + e.getMessage());
//            return "plans/addplantype"; // 返回新增頁面
//        }
//    }

    @PostMapping("/add")
    public String addPlanType(@ModelAttribute PlanType planType, RedirectAttributes redirectAttributes) {
        try {
            // 驗證 planTypeId 格式是否為大寫 A~Z
            if (!planType.getPlanTypeId().matches("^[A-Z]+$")) {
                redirectAttributes.addFlashAttribute("error", "新增失敗：行程類型 ID 僅能包含大寫英文字母 (A~Z)。");
                return "redirect:/plans/addplantype"; // 返回新增頁面
            }

            // 檢查是否有重複的行程類型 ID
            PlanType existingPlanType = planTypeService.findPlanTypeById(planType.getPlanTypeId());
            if (existingPlanType != null) {
                redirectAttributes.addFlashAttribute("error", "新增失敗：行程類型 ID 已存在。");
                return "redirect:/plans/addplantype"; // 返回新增頁面
            }

            // 新增成功
            planTypeService.updatePlanTypeDay(planType);
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
