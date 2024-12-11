//package com.bb.controller;
//
//import java.util.List;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.constraints.Digits;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotEmpty;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.bb.model.BBService;
//import com.bb.model.BBVO;
//
//@Controller
//@Validated
//@RequestMapping("/bb")
//public class MsgIdController {
//
//    @Autowired
//    private BBService bbSvc;
//
//    // 單筆公告查詢
//    @PostMapping("/getOne_For_Display")
//    public String getOneForDisplay(
//            @NotEmpty(message = "公告編號: 請勿空白")
//            @Digits(integer = 4, fraction = 0, message = "公告編號: 請輸入數字，且不得超過{integer}位數")
//            @Min(value = 1, message = "公告編號: 必須大於{value}")
//            @RequestParam("msgId") Integer msgId,
//            ModelMap model) {
//        BBVO bbVO = bbSvc.getOneMsg(msgId);
//        if (bbVO == null) {
//            model.addAttribute("errorMsg", "查無資料");
//            return "back-end/bb/select_page_bb";
//        }
//        model.addAttribute("bbVO", bbVO);
//        return "back-end/bb/listOneMsg";
//    }
//
//    // 錯誤處理
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ModelAndView handleValidationError(HttpServletRequest req, ConstraintViolationException e, ModelMap model) {
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        StringBuilder errorMessages = new StringBuilder();
//        for (ConstraintViolation<?> violation : violations) {
//            errorMessages.append(violation.getMessage()).append("<br>");
//        }
//        model.addAttribute("errorMsg", "請修正以下錯誤:<br>" + errorMessages.toString());
//        return new ModelAndView("back-end/bb/select_page_bb");
//    }
//}




package com.event.cia103g1springboot.bb.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.event.cia103g1springboot.bb.model.BBService;
import com.event.cia103g1springboot.bb.model.BBVO;

@Controller
@Validated
@RequestMapping("/bb")
public class MsgIdController {

	@Autowired
	BBService bbSvc;
	
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			@NotNull(message="公告編號:請勿空白")
			@Digits(integer = 4,fraction = 0,message="公告編號:請填數字，請勿超過{integer}位數")
			@Min(value = 1,message = "公告編號:不得小於{value}")
			@RequestParam("msgid") Integer msgid,
			ModelMap model
			) {
		BBVO bbVO = bbSvc.getOneMsg(msgid);
		
		List<BBVO> list = bbSvc.getAll();
		model.addAttribute("msgListData",list);
		if(bbVO == null) {
			model.addAttribute("errorMsg","查無資料");
			return "back-end/bb/select_page_bb";
		}
		
		model.addAttribute("bbVO",bbVO);
		model.addAttribute("getOne_For_Display",true);
		return "back-end/bb/select_page_bb";
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for(ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage()+"<br>");
		}
		List<BBVO> list = bbSvc.getAll();
		model.addAttribute("bbListData",list);
		String msg = strBuilder.toString();
		return new ModelAndView("back-end/bb/select_page_bb","errorMsg","請修正以下錯誤:<br>"+msg);
	}
}
