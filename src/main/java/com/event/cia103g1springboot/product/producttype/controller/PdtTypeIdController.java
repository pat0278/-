package com.event.cia103g1springboot.product.producttype.controller;

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

import com.event.cia103g1springboot.product.producttype.model.PdtTypeService;
import com.event.cia103g1springboot.product.producttype.model.PdtTypeVO;

@Controller
@Validated
@RequestMapping("/productType")
public class PdtTypeIdController {
	
	@Autowired
	PdtTypeService pdtTypeSvc;
	
	@PostMapping("getPdtType_For_Display")
	public String getPdtType_For_Display(
			@NotNull(message="類別編號:請勿空白")
			@Digits(integer = 3, fraction = 0,message="類別編號:請填數字，請勿超過{integer}位數")
			@Min(value = 1,message="類別編號:不可小於{value}")
			@RequestParam("pdtTypeId")
			Integer pdtTypeId,ModelMap model){
		PdtTypeVO pdtTypeVO = pdtTypeSvc.getOnePdtType(pdtTypeId);
		List<PdtTypeVO> list = pdtTypeSvc.getAll();
		model.addAttribute("pdtTypeListData",list);
		if(pdtTypeVO == null) {
			model.addAttribute("errorMsgs","查無資料");
			return "back-end/productType/select_page_pdtType";
		}
		model.addAttribute("pdtTypeVO",pdtTypeVO);
		model.addAttribute("getPdtType_For_Display",true);
		return "back-end/productType/select_page_pdtType";
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	public ModelAndView handleErrorPdtType(HttpServletRequest req,ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for(ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage()+"<br>");
		}
		List<PdtTypeVO> list = pdtTypeSvc.getAll();
		model.addAttribute("pdtTypeListData",list);
		String msg = strBuilder.toString();
		return new ModelAndView("back-end/productType/select_page_pdtType","errorMsg","請修正以下錯誤:<br>"+msg);
	}
	
}
