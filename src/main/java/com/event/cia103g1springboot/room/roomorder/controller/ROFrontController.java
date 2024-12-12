package com.event.cia103g1springboot.room.roomorder.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.event.cia103g1springboot.plan.planorder.model.PlanOrder;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrderService;
import com.event.cia103g1springboot.room.roomorder.model.ROService;
import com.event.cia103g1springboot.room.roomorder.model.ROVO;
import com.event.cia103g1springboot.room.roomtype.model.RTService;
import com.event.cia103g1springboot.room.roomtype.model.RTVO;

@Controller
@RequestMapping("/roomOrder")
public class ROFrontController {

	@Autowired
	ROService roSvc;
	
	@Autowired
	RTService rtSvc;
	
	@Autowired
	PlanOrderService poSvc;
	
	@PostMapping("roomOrderList")
	public String roomOrderList (@RequestParam("planOrderId") String planOrderId,ModelMap model )throws IOException  {
		if(planOrderId == null || planOrderId.trim().length()==0 || planOrderId.isEmpty()) {
			model.addAttribute("errorMessage","查無資料");
			model.addAttribute("errorMessage","行程訂單編號不可空白");
			return "front-end/roomOrder/selectRO";
		}
		PlanOrder newPO = poSvc.findPlanOrderById(Integer.valueOf(planOrderId));
		model.addAttribute("planOrder",newPO);
		List<ROVO> roListByPOId = roSvc.getByPlan(newPO.getPlanOrderId());
		model.addAttribute("roListByPOId",roListByPOId);
		ROVO firstRO = roListByPOId.get(0);
		RTVO newRT = rtSvc.getOneRT(firstRO.getRtVO().getRoomTypeId());
		model.addAttribute("rtVO",newRT);
	
		return "front-end/roomOrder/roomOrderList";
	}
	
	@GetMapping("/selectRO")
	public String selectRO(ModelMap mode) {
		return "front-end/roomOrder/selectRO";
	}
}
