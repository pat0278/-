package com.event.cia103g1springboot.plan.controller;

import com.event.cia103g1springboot.plan.plan.model.Plan;
import com.event.cia103g1springboot.plan.plan.model.PlanService;
import com.event.cia103g1springboot.plan.planinfo.model.PlanInfo;
import com.event.cia103g1springboot.plan.planinfo.model.PlanInfoService;
import com.event.cia103g1springboot.plan.plantype.model.PlanType;
import com.event.cia103g1springboot.plan.plantype.model.PlanTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/plans")
public class PlanThymeleafController {
    @Autowired
    private PlanService planService;
    @Autowired
    private PlanTypeService planTypeService;
    @Autowired
    private PlanInfoService planInfoService;


    // 行程all 跳轉
    @GetMapping("/query")
    public String getAllPlans(Model model) {
        List<Plan> plans = planService.getAllPlans();
        model.addAttribute("plans", plans);
        return "plan/footer";
    }

    // 行程類別all 跳轉
    @GetMapping("plantype/query")
    public String getAllPlanTypes(Model model) {
        List<PlanType> planTypes = planTypeService.getAllPlanTypes();
        model.addAttribute("planTypes", planTypes);
        return "plantype/allplantype"; // 確保對應模板位置為 templates/plantype/allplantypes.html
    }


    // 行程總類跳轉
    @GetMapping("/addplantype")
    public String redirectToAddPlanType(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 3; // 每頁顯示 5 筆
        Page<PlanType> planTypePage = planTypeService.getPlanTypesByPage(page, pageSize);

        // 防止頁碼越界
        if (page < 0 || page >= planTypePage.getTotalPages()) {
            return "redirect:/addplantype?page=0"; // 重定向到第一頁
        }

        model.addAttribute("planTypes", planTypePage.getContent()); // 當前頁的數據
        model.addAttribute("currentPage", page); // 當前頁碼
        model.addAttribute("totalPages", planTypePage.getTotalPages()); // 總頁數

        model.addAttribute("planType", new PlanType()); // 用於新增表單
        return "plantype/addplantype";
    }





    @GetMapping("/addplan")
    public String redirectToAddPlan(Model model) {
        // 獲取所有行程類型
        List<PlanType> planTypes = planTypeService.getAllPlanTypes();
        model.addAttribute("planTypes", planTypes); // 傳遞行程類型列表到前端
        model.addAttribute("plan", new Plan());     // 傳遞一個空的 Plan 對象，用於表單綁定
        return "/plan/addplan";                           // 返回對應的 Thymeleaf 模板名稱
    }




    @GetMapping("/info/{planTypeId}")
    public String showPlanInfoByPlanTypeId(@PathVariable String planTypeId, Model model) {
        // 根據 planTypeId 查詢行程類型資訊
        PlanType planType = planTypeService.findPlanTypeById(planTypeId);

        if (planType == null) {
            model.addAttribute("error", "找不到行程類型 ID 為 " + planTypeId + " 的行程資訊！");
            return "redirect:/plans/plantype/query"; // 返回到行程類型查詢頁面
        }

        // 查詢 typeinfo 表中的內容
        List<PlanInfo> typeInfoList = planInfoService.findByPlanTypeId(planTypeId);

        // 獲取行程類型的天數
        int days = planType.getPlanDay();
        Map<Integer, String> planContentMap = new HashMap<>();

        // 將已存在的行程內容放入 Map，以便後端補全空白天數
        for (PlanInfo info : typeInfoList) {
            planContentMap.put(info.getPlanDay(), info.getPlanCon());
        }

        // 動態生成完整天數表，補全缺少的天數
        List<PlanInfo> completeTypeInfoList = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            PlanInfo info = new PlanInfo();
            info.setPlanDay(i);
            info.setPlanCon(planContentMap.getOrDefault(i, "")); // 如果該天無內容，則填空
            completeTypeInfoList.add(info);
        }

        // 將資料傳遞到前端
        model.addAttribute("planTypeId", planTypeId); // 行程類型 ID
        model.addAttribute("planTypeName", planType.getPlanName()); // 行程名稱
        model.addAttribute("planDay", days); // 行程天數
        model.addAttribute("typeInfoList", completeTypeInfoList); // 補全後的 typeinfo 表內容

        return "plantype/planinfo"; // 跳轉到 planinfo.html 頁面
    }



    @GetMapping("/editplan/{planId}")
    public String editPlan(@PathVariable int planId, Model model) {
        Plan plan = planService.findPlanById(planId);
        if (plan != null) {
            model.addAttribute("plan", plan);
            model.addAttribute("planTypes", planTypeService.getAllPlanTypes()); // 傳遞行程類型列表
            return "plan/editplan"; // 確保這裡的路徑對應 `templates/plan/editplan.html`
        } else {
            model.addAttribute("error", "未找到行程 ID: " + planId);
            return "redirect:/plan"; // 返回行程列表頁面（自行設置列表頁面路徑）
        }
    }

    @GetMapping("/editplantype/{planTypeId}")
    public String editPlanType(@PathVariable String planTypeId, Model model) {
        PlanType planType = planTypeService.findPlanTypeById(planTypeId);
        if (planType != null) {
            model.addAttribute("planType", planType); // 传递行程类型对象
            return "plantype/editplantype"; // 确保模板路径正确
        } else {
            model.addAttribute("error", "未找到行程類型 ID: " + planTypeId);
            return "redirect:/plantype/query"; // 返回到列表页面
        }
    }



    @GetMapping("/delete/{planId}")
    public String deletePlan(@PathVariable int planId) {
        planService.deletePlanById(planId);
        return "redirect:/plans";
    }

}
