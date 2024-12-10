package com.event.cia103g1springboot.room.roomImg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.event.cia103g1springboot.room.roomImg.model.RImgService;
import com.event.cia103g1springboot.room.roomImg.model.RImgVO;
import com.event.cia103g1springboot.room.roomtype.model.RTService;
import com.event.cia103g1springboot.room.roomtype.model.RTVO;

@Controller
@RequestMapping("/rImg")
public class RImgController<rImgVO> {

	@Autowired
	RImgService rImgSvc;
	
	@Autowired
	RTService rtSvc;
	
	@GetMapping("addRImg")
	public String addRImg(@RequestParam("roomTypeId") String roomTypeId, Model model) {
		RImgVO rImgVO = new RImgVO();
		RTVO rtVO = rtSvc.getOneRT(Integer.valueOf(roomTypeId));
		rImgVO.setRtVO(rtVO);
		model.addAttribute("rImgVO",rImgVO);
		model.addAttribute("rtVO",rtVO);
		return "back-end/rImg/addRImg";
	}
	
	@PostMapping("insertRImg")
	public String insertRImg(@Valid RImgVO rImgVO,BindingResult result,
							RTVO rtVO,ModelMap model,
							@RequestParam("roomImgFiles") MultipartFile[] parts)throws IOException  {
		result = removeFieldError(rImgVO,result,"roomImg");
		if(parts[0].isEmpty()) {
			model.addAttribute("errorMessage","房型圖片:請上傳房型圖片");
			 return "back-end/rImg/addRImg";
		}
		List<RImgVO> rImgList = new ArrayList<>();
		for(MultipartFile multipartFile : parts) {
			if(!multipartFile.isEmpty()) {
				RImgVO newRImgVO =new RImgVO();
				newRImgVO.setRoomImg(multipartFile.getBytes());
				newRImgVO.setRoomImgName(multipartFile.getOriginalFilename());
				newRImgVO.setRtVO(rtVO);
				rImgList.add(newRImgVO);
			}
		}
		
		if(rImgList.isEmpty()) {
			model.addAttribute("errorMessage","商品圖片:上傳的文件均無效");
			return "back-end/rImg/addRImg";
		}
		
		List<RImgVO> newRImgs = rImgSvc.addRImgs(rImgList);
		if(newRImgs == null || newRImgs.isEmpty()) {
			model.addAttribute("errorMessage","房型圖片:新增失敗，請稍後再試");
			return "back-end/rImg/addRImg";
		}
		
		RImgVO firstRImg = newRImgs.get(0);
		RTVO updateRTVO = rtSvc.getOneRT(firstRImg.getRtVO().getRoomTypeId());
		model.addAttribute("rtVO",updateRTVO);
		
		List<RImgVO> list = rImgSvc.getByroomTypeId(updateRTVO.getRoomTypeId());
		model.addAttribute("rImgListByRTId",list);
		model.addAttribute("success","-(新增成功)");
		return "back-end/rt/listOneRT";
	}
	
	@PostMapping("getOneRImg_For_Update")
	public String getOneRImg_For_Update(@RequestParam("roomImgId") String roomImgId,ModelMap model) {
		RImgVO rImgVO =rImgSvc.getOneRoomImg(Integer.valueOf(roomImgId));
		model.addAttribute("rImgVO",rImgVO);
		return "back-end/rImg/update_RImg_input";
	}
	
	@PostMapping("updateRImg")
	public String updateRImg(@Valid RImgVO rImgVO , BindingResult result, ModelMap model,
			@RequestParam("roomImg") MultipartFile[] parts)throws IOException {
		result = removeFieldError(rImgVO,result,"roomImg");
		if(parts[0].isEmpty()) {
			byte[] roomImg = rImgSvc.getOneRoomImg(rImgVO.getRoomImgId()).getRoomImg();
			rImgVO.setRoomImg(roomImg);
		}else {
			for(MultipartFile multipartFile : parts) {
				byte[] roomImg = multipartFile.getBytes();
				rImgVO.setRoomImg(roomImg);
			}
		}
		if(result.hasErrors()) {
			return "back-end/rImg/update_RImg_input";
		}
		rImgSvc.updateRImg(rImgVO);
		model.addAttribute("success","-(更新成功)");
		rImgVO = rImgSvc.getOneRoomImg(Integer.valueOf(rImgVO.getRoomImgId()));
		model.addAttribute("rImgVO",rImgVO);
		List<RImgVO> list = rImgSvc.getByroomTypeId(rImgVO.getRtVO().getRoomTypeId());
		model.addAttribute("rImgListByRTId",list);
		RTVO rtVO = rImgVO.getRtVO();
		model.addAttribute("rtVO",rtVO);
		return "back-end/rt/listOneRT";
	}
	
	@PostMapping("deleteRImg")
	public String deleteRImg(@RequestParam("roomImgId") String roomImgId,ModelMap model) {
		RImgVO rImgVO = rImgSvc.getOneRoomImg(Integer.valueOf(roomImgId));
		RTVO rtVO = rtSvc.getOneRT(rImgVO.getRtVO().getRoomTypeId());
		model.addAttribute("rtVO",rtVO);
		rImgSvc.deleteRImg(Integer.valueOf(roomImgId));
		List<RImgVO> list = rImgSvc.getByroomTypeId(rtVO.getRoomTypeId());
		model.addAttribute("rImgListByRTId",list);
		model.addAttribute("success","-(刪除成功)");
		return "back-end/rt/listOneRT";
	}
	
	@ModelAttribute("rtListData")
	protected List<RTVO> referenceListData(){
		List<RTVO> list = rtSvc.getAllRT();
		return list;
	}
	
	public BindingResult removeFieldError(RImgVO rImgVO,BindingResult result,String removedFieldname){
		List<FieldError> errorListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(rImgVO , "rImgVO");
		for(FieldError fieldError : errorListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	@PostMapping("listRImg_ByCompositeQuery")
	public String listAllRImg(HttpServletRequest req, Model model) {
		Map<String, String[]> map = req.getParameterMap();
		List<RImgVO> list = rImgSvc.getAllRImg(map);
		model.addAttribute("rImgListData", list);
		return "back-end/rImg/listAllRImg";
	}
}
