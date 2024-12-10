package com.event.cia103g1springboot.product.pdtimg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.cia103g1springboot.hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_PdtImg;



//複合查詢要記得自己加入
//import hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_PdtImg;

@Service("pdtImgService")
public class PdtImgService {

	@Autowired
	PdtImgRepository repository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addPdtImg(PdtImgVO pdtImgVO) {
		repository.save(pdtImgVO);
	}
	
	public void updatePdtImg(PdtImgVO pdtImgVO) {
		repository.save(pdtImgVO);
	}
	
	 public void deletePdtImg(Integer pdtImgId) {
		 if(repository.existsById(pdtImgId)) {
			 repository.deleteBypdtImgId(pdtImgId);
		 }
	 }
	 
	 public PdtImgVO getOnePdtImg(Integer pdtImgId) {
		 Optional<PdtImgVO> optional = repository.findById(pdtImgId);
		 return optional.orElse(null);
	 }
	 
	 public List<PdtImgVO> getAllPdtImg(){
		 return repository.findAll();
	 }
	 
	 public List<PdtImgVO> getByPdtId(Integer pdtId){
		 return repository.getByPdtId(pdtId);
	 }
	 
	 public List<PdtImgVO> getAllPdtImg(Map<String, String[]> map){
		 return HibernateUtil_CompositeQuery_PdtImg.getAllC(map,sessionFactory.openSession());
	 }
	 
	 public List<PdtImgVO> addPdtImgs(List<PdtImgVO> pdtImgVOs) {
		 List<PdtImgVO> newPdtImgs = new ArrayList<>();   
		 for (PdtImgVO pdtImgVO : pdtImgVOs) {
		        repository.save(pdtImgVO);
		        newPdtImgs.add(pdtImgVO);
		    }
		    return newPdtImgs;
		}
}
