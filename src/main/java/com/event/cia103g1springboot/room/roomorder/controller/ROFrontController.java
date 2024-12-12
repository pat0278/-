package com.event.cia103g1springboot.room.roomorder.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@ModelAttribute
	List<ROVO> roListByPOId (String planOrderId,Model model){
		return roSvc.getByPlan(Integer.valueOf(planOrderId)); 
	}
	
	@PostMapping("roomOrderList")
	public String roomOrderList (@RequestParam("planOrderId") String planOrderId,BindingResult result,ModelMap model )throws IOException  {
		List<ROVO> roListByPOId = roSvc.getByPlan(Integer.valueOf(planOrderId));
		model.addAttribute("roListByPOId",roListByPOId);
		ROVO firstRO = roListByPOId.get(0);
		RTVO newRT = rtSvc.getOneRT(firstRO.getRtVO().getRoomTypeId());
		model.addAttribute("rtVO",newRT);
		PlanOrder newPO = poSvc.getOnePlanOrder(firstRO.getPlanOrder().getPlanOrderId());
		model.addAttribute("planOrder",newPO);
		return "back-end/roomOrder/roomOrderList";
	}
}
