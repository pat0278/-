package com.event.cia103g1springboot.product.pdtimg.controller;

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

import com.event.cia103g1springboot.product.pdtimg.model.PdtImgService;
import com.event.cia103g1springboot.product.pdtimg.model.PdtImgVO;
import com.event.cia103g1springboot.product.product.model.PdtService;
import com.event.cia103g1springboot.product.product.model.PdtVO;
import com.event.cia103g1springboot.product.producttype.model.PdtTypeService;

@Controller
@RequestMapping("/pdtImg")
public class PdtImgController<pdtImgVO> {

	@Autowired
	PdtImgService pdtImgSvc;
	
	@Autowired
	PdtService pdtSvc;
	
	@Autowired
	PdtTypeService pdtTypeSvc;
	
	@GetMapping("addPdtImg")
	public String addPdtImg(@RequestParam("pdtId") String pdtId, Model model) {
		PdtImgVO pdtImgVO = new PdtImgVO();
		PdtVO pdtVO = pdtSvc.getOnePdt(Integer.valueOf(pdtId));
		pdtImgVO.setPdtVO(pdtVO);
		model.addAttribute("pdtImgVO",pdtImgVO);
		model.addAttribute("pdtVO",pdtVO);
		return "back-end/pdtImg/addPdtImg";
	}
	
	@PostMapping("insertPdtImg")
	public String insertPdtImg(@Valid PdtImgVO pdtImgVO, BindingResult result, 
	                           PdtVO pdtVO, ModelMap model, 
	                           @RequestParam("pdtImgFiles") MultipartFile[] parts) throws IOException {

	    result = removeFieldError(pdtImgVO, result, "pdtImg");

	    if (parts[0].isEmpty()) {
	        model.addAttribute("errorMessage", "商品圖片: 請至少上傳一張商品圖片");
	        return "back-end/pdtImg/addPdtImg";
	    }

	    List<PdtImgVO> pdtImgList = new ArrayList<>();
	    for (MultipartFile multipartFile : parts) {
	        if (!multipartFile.isEmpty()) {
	            PdtImgVO newPdtImgVO = new PdtImgVO();
	            newPdtImgVO.setPdtImg(multipartFile.getBytes());
	            newPdtImgVO.setPdtImgName(multipartFile.getOriginalFilename());
	            newPdtImgVO.setPdtVO(pdtVO);
	            pdtImgList.add(newPdtImgVO);
	        }
	    }

	    if (pdtImgList.isEmpty()) {
	        model.addAttribute("errorMessage", "商品圖片: 上傳的文件均無效");
	        return "back-end/pdtImg/addPdtImg";
	    }

	    List<PdtImgVO> newPdtImgs = pdtImgSvc.addPdtImgs(pdtImgList);
	    if (newPdtImgs == null || newPdtImgs.isEmpty()) {
	        model.addAttribute("errorMessage", "商品圖片: 新增失敗，請稍後再試！");
	        return "back-end/pdtImg/addPdtImg";
	    }

	    PdtImgVO firstImg = newPdtImgs.get(0);
	    PdtVO updatedPdtVO = pdtSvc.getOnePdt(firstImg.getPdtVO().getPdtId());
	    model.addAttribute("pdtVO", updatedPdtVO);

	    List<PdtImgVO> pdtImgListByPdtId = pdtImgSvc.getByPdtId(updatedPdtVO.getPdtId());
	    model.addAttribute("pdtImgListByPdtId", pdtImgListByPdtId);

	    model.addAttribute("success", "新增成功");
	    return "back-end/product/listOnePdt";
	}

	@PostMapping("getOnePdtImg_For_Update")
	public String getOnePdtImg_For_Update(@RequestParam("pdtImgId") String pdtImgId , ModelMap model) {
		PdtImgVO pdtImgVO = pdtImgSvc.getOnePdtImg(Integer.valueOf(pdtImgId));
		model.addAttribute("pdtImgVO",pdtImgVO);
		return "back-end/pdtImg/update_pdtImg_input";
	}
	
	@PostMapping("updatePdtImg")
	public String updatePdtImg(@Valid PdtImgVO pdtImgVO, BindingResult result,ModelMap model,
			@RequestParam("pdtImg") MultipartFile[] parts) throws IOException{
		
		result = removeFieldError(pdtImgVO , result , "pdtImg");
		if(parts[0].isEmpty()) {
			byte[] pdtImg = pdtImgSvc.getOnePdtImg(pdtImgVO.getPdtImgId()).getPdtImg();
			pdtImgVO.setPdtImg(pdtImg);
		}else {
			for(MultipartFile multipartFile : parts) {
				byte[] pdtImg = multipartFile.getBytes();
				pdtImgVO.setPdtImg(pdtImg);
			}
		}
		if(result.hasErrors()) {
			return "back-end/pdtImg/update_pdtImg_input";
		}
		
		pdtImgSvc.updatePdtImg(pdtImgVO);
		model.addAttribute("success","-(更新成功)");
		pdtImgVO = pdtImgSvc.getOnePdtImg(Integer.valueOf(pdtImgVO.getPdtImgId()));
		model.addAttribute("pdtImgVO",pdtImgVO);
		List<PdtImgVO> list = pdtImgSvc.getByPdtId(pdtImgVO.getPdtVO().getPdtId());
		model.addAttribute("pdtImgListByPdtId",list);
		PdtVO pdtVO = pdtImgVO.getPdtVO();
		model.addAttribute("pdtVO",pdtVO);
		return "back-end/product/listOnePdt";
	}
	
	@PostMapping("deletePdtImg")
	public String deletePdtImg(@RequestParam("pdtImgId") String pdtImgId , ModelMap model) {
		PdtImgVO orpdtImgVO = pdtImgSvc.getOnePdtImg(Integer.valueOf(pdtImgId));
		PdtVO pdtVO = pdtSvc.getOnePdt(orpdtImgVO.getPdtVO().getPdtId());
		model.addAttribute("pdtVO",pdtVO);
		pdtImgSvc.deletePdtImg(Integer.valueOf(pdtImgId));
		List<PdtImgVO> pdtImgListByPdtId = pdtImgSvc.getByPdtId(pdtVO.getPdtId());
		model.addAttribute("pdtImgListByPdtId",pdtImgListByPdtId);
		return "back-end/product/listOnePdt";
	}
	
	@ModelAttribute("pdtListData")
	protected List<PdtVO> referenceListData(){
		List<PdtVO> list = pdtSvc.getAll();
		return list;
	}
	
	public BindingResult removeFieldError(PdtImgVO pdtImgVO,BindingResult result,String removedFieldname){
		List<FieldError> errorListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(pdtImgVO , "pdtImgVO");
		for(FieldError fieldError : errorListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	@PostMapping("listPdtImg_ByCompositeQuery")
	public String listAllPdtImg(HttpServletRequest req, Model model) {
		Map<String, String[]> map = req.getParameterMap();
		List<PdtImgVO> list = pdtImgSvc.getAllPdtImg(map);
		model.addAttribute("pdtImgListData", list);
		return "back-end/pdtImg/listAllPdtImg";
	}
}
