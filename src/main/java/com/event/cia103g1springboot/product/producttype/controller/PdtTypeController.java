package com.event.cia103g1springboot.product.producttype.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.event.cia103g1springboot.product.producttype.model.PdtTypeService;
import com.event.cia103g1springboot.product.producttype.model.PdtTypeVO;

@Controller
@RequestMapping("/productType")
public class PdtTypeController {

	@Autowired
	PdtTypeService pdtTypeSvc;
	
	@GetMapping("addPdtType")
	public String  addPdtType (ModelMap model) {
		PdtTypeVO pdtTypeVO = new PdtTypeVO();
		model.addAttribute("pdtTypeVO",pdtTypeVO);
		return "back-end/productType/addPdtType";
	}
	
	@PostMapping("insertPdtType")
	public String insert(@Valid PdtTypeVO pdtTypeVO, BindingResult result,ModelMap model)throws IOException {
		if(result.hasErrors()) {
			return "back-end/productType/addPdtType";
		}
		pdtTypeSvc.addPdtType(pdtTypeVO);
		return "redirect:/productType/listAllPdtType";
	}
	
	
	@PostMapping("getPdtType_For_Update")
	public String getPdtType_For_Update(@RequestParam("pdtTypeId") String pdtTypeId,ModelMap model) {
		PdtTypeVO pdtTypeVO = pdtTypeSvc.getOnePdtType(Integer.valueOf(pdtTypeId));
		model.addAttribute("pdtTypeVO",pdtTypeVO);
		return "back-end/productType/update_pdtType_input";
	}
	
	@PostMapping("updatePdtType")
	public String updatePdtType(@ModelAttribute("pdtTypeVO")@Valid PdtTypeVO pdtTypeVO,BindingResult result,Model model)throws IOException{
		 System.out.println("==========進入 update 方法==========");
			System.out.println("pdtTypeId"+pdtTypeVO.getPdtTypeId());
			System.out.println("pdtTypeName"+pdtTypeVO.getPdtTypeName());
			System.out.println("pdtTypeDesc"+pdtTypeVO.getPdtTypeDesc());
		 pdtTypeSvc.updatePdtType(pdtTypeVO);
		 model.addAttribute("success","-(修改成功)");
		 pdtTypeVO = pdtTypeSvc.getOnePdtType(Integer.valueOf(pdtTypeVO.getPdtTypeId()));
		 model.addAttribute("pdtTypeVO",pdtTypeVO);
		 return "back-end/productType/listOnePdtType";
	}
	
	@PostMapping("deletePdtType")
	public String deletePdtType(@RequestParam("pdtTypeId")Integer pdtTypeId,ModelMap model) {
		pdtTypeSvc.deletePdtType(pdtTypeId);
		List<PdtTypeVO> list = pdtTypeSvc.getAll();
		model.addAttribute("pdtTypeListData", list);
		model.addAttribute("success","-(刪除成功)");
		return "back-end/productType/listAllPdtType";
	}
	
	public BindingResult removeFieldError_PdtType(PdtTypeVO pdtTypeVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(pdtTypeVO, "pdtTypeVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	
}
