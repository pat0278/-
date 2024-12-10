package com.event.cia103g1springboot.product.pdtimg.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import com.event.cia103g1springboot.product.pdtimg.model.PdtImgService;
import com.event.cia103g1springboot.product.pdtimg.model.PdtImgVO;
import com.event.cia103g1springboot.product.product.model.PdtService;
import com.event.cia103g1springboot.product.product.model.PdtVO;

@Controller
@Validated
@RequestMapping("/pdtImg")
public class PdtImgIdController {

	@Autowired
	PdtImgService pdtImgSvc;
	
	@Autowired
	PdtService pdtSvc;
	
	@PostMapping("getOnePdtImg_For_Display")
	public String getOnePdtImg_For_Display(
			@NotEmpty(message="圖片編號:請勿空白")
			@RequestParam String pdtImgId,ModelMap model) {
		
		PdtImgVO pdtImgVO = pdtImgSvc.getOnePdtImg(Integer.valueOf(pdtImgId));
		List<PdtImgVO> list = pdtImgSvc.getAllPdtImg();
		model.addAttribute("pdtImgListData",list);
		model.addAttribute("pdtVO",new PdtVO());
		List<PdtVO> list2 = pdtSvc.getAll();
		model.addAttribute("pdtListData",list2);
		
		if(pdtImgVO == null) {
			model.addAttribute("errorMessage","查無資料");
			return "back-end/pdtImg/select_page_pdtImg";
		}
		model.addAttribute("pdtImgVO",pdtImgVO);
		model.addAttribute("getOnePdtImg_For_Display","true");
		return "back-end/pdtImg/select_page_pdtImg";
	}
	
	@ExceptionHandler(value= {ConstraintViolationException.class})
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
		List<PdtImgVO> list = pdtImgSvc.getAllPdtImg();
		model.addAttribute("pdtImgListData", list); 
		model.addAttribute("pdtVO", new PdtVO()); 
		List<PdtVO> list2 = pdtSvc.getAll();
    	model.addAttribute("pdtListData",list2); 
		String message = strBuilder.toString();
	    return new ModelAndView("back-end/pdtImg/select_page_pdtImg", "errorMessage", "請修正以下錯誤:<br>"+message);
	}	
	
	@ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException e, Model model) {
        model.addAttribute("errorMessage", "圖片上傳失敗：" + e.getMessage());
        return "back-end/pdtImg/addPdtImg"; // 返回至適當的頁面
    }
	
}
