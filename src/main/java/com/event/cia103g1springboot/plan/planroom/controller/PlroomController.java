package com.event.cia103g1springboot.plan.planroom.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.event.cia103g1springboot.plan.plan.model.PlanService;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoom;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoomService;
import com.event.cia103g1springboot.room.roomtype.model.RTService;
import com.event.cia103g1springboot.room.roomtype.model.RTVO;

@RequestMapping("/planroom")
@Controller
public class PlroomController {

    @Autowired
    PlanRoomService planRoomService;

    @Autowired
    PlanService planService;

    @Autowired
    RTService rtService;

    @GetMapping("/listall")
    public String ListALL(PlanRoom planRoom, Model model) {
       List<PlanRoom> list =planRoomService.findAll();
       model.addAttribute("list", list);
       return "/plan/planroom/planroom";
    }

    @GetMapping("/addpage")
    public String Add(PlanRoom planRoom, Model model) {
        model.addAttribute("plans", planService.getAllPlans()); //直接拿不爽啦
        model.addAttribute("rooms", rtService.getAllRT());
        return "/plan/planroom/addpage";
    }

    @PostMapping("/add")
    public String AddPost(PlanRoom planRoom, Model model) {
            // 1. 查詢現有房型
            RTVO rtvo = rtService.getOneRT(planRoom.getRoomTypeId());
            if (rtvo == null) {
                // 處理找不到房型的情況
                return "error_page";
            }
            int totalqty = rtvo.getRoomQty() - planRoom.getRoomQty();
            rtService.updateRoomQty(planRoom.getRoomTypeId(), totalqty);
            planRoomService.save(planRoom);
            return "redirect:/planroom/listall";
    }

    @Transactional
    @GetMapping("/editpage/{roomid}/{planid}")
    public String editPage(@PathVariable Integer planid,@PathVariable Integer roomid, Model model) {
        PlanRoom planroom = planRoomService.findByRmTypeIdAndPlanId(roomid, planid);
        // 將資料加入 Model
        model.addAttribute("planroom", planroom);
        model.addAttribute("errors", new HashMap<String, String>());
        return "plan/planroom/editpage";
    }

    @Transactional
    @PostMapping("/edit")
    public String edit(PlanRoom planRoom, Model model) {
        planRoomService.save(planRoom);
        return "redirect:/planroom/listall";
    }



}
