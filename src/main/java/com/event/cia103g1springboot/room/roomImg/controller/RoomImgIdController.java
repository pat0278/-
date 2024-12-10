package com.event.cia103g1springboot.room.roomImg.controller;

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

import com.event.cia103g1springboot.room.roomImg.model.RImgService;
import com.event.cia103g1springboot.room.roomImg.model.RImgVO;
import com.event.cia103g1springboot.room.roomtype.model.RTService;
import com.event.cia103g1springboot.room.roomtype.model.RTVO;

@Controller
@Validated
@RequestMapping("/rImg")
public class RoomImgIdController {

	@Autowired
	RImgService rImgSvc;
	
	@Autowired
	RTService rtSvc;
	
	@PostMapping("getOneRImg_For_Display")
	public String getOneRImg_For_Display(
			@NotEmpty(message="圖片編號:請勿空白")
			@RequestParam String rImgId,ModelMap model) {
		
		RImgVO rImgVO = rImgSvc.getOneRoomImg(Integer.valueOf(rImgId));
		List<RImgVO> list = rImgSvc.getAllRImg();
		model.addAttribute("rImgListData",list);
		model.addAttribute("rtVO",new RTVO());
		List<RTVO> list2 = rtSvc.getAllRT();
		model.addAttribute("rtListData",list2);
		
		if(rImgVO == null) {
			model.addAttribute("errorMessage","查無資料");
			return "back-end/rImg/select_page_RImg";
		}
		model.addAttribute("rImgVO",rImgVO);
		model.addAttribute("getOneRImg_For_Display","true");
		return "back-end/rImg/select_page_RImg";
	}
	@ExceptionHandler(value= {ConstraintViolationException.class})
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
		List<RImgVO> list = rImgSvc.getAllRImg();
		model.addAttribute("rImgListData", list); 
		model.addAttribute("rtVO", new RTVO()); 
		List<RTVO> list2 = rtSvc.getAllRT();
    	model.addAttribute("rtListData",list2); 
		String message = strBuilder.toString();
	    return new ModelAndView("back-end/rImg/select_page_RImg", "errorMessage", "請修正以下錯誤:<br>"+message);
	}	
	
	@ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException e, Model model) {
        model.addAttribute("errorMessage", "圖片上傳失敗：" + e.getMessage());
        return "back-end/rImg/addRImg"; // 返回至適當的頁面
    }
}
