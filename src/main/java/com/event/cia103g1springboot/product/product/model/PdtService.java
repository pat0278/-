package com.event.cia103g1springboot.product.product.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.cia103g1springboot.hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_Pdt;
import com.event.cia103g1springboot.hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_Pdt_Front;
import com.event.cia103g1springboot.product.pdtimg.model.PdtImgService;
import com.event.cia103g1springboot.product.pdtimg.model.PdtImgVO;


@Service("pdtService")
public class PdtService {
	
	@Autowired
	PdtRepository repository;
	@Autowired
	PdtImgService pdtImgSvc;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addPdt (PdtVO pdtVO) {
		repository.save(pdtVO);
	}
	
	@Transactional
	public void addPdtAndImgs(PdtVO pdtVO, List<PdtImgVO> pdtImgList) {
		PdtService pdtSvc = new PdtService();
		pdtSvc.addPdt(pdtVO);
		pdtImgSvc.addPdtImgs(pdtImgList);
	}
	
	@Transactional
	public void updatePdt(PdtVO pdtVO) {
		repository.save(pdtVO);
	}
	
	public void deletePdt(Integer pdtId) {
		if (repository.existsById(pdtId))
			repository.deleteByPdtId(pdtId);
	}
	
	public PdtVO getOnePdt(Integer pdtId) {
		Optional<PdtVO> optional = repository.findById(pdtId);
		return optional.orElse(null);
	}
	
	public List<PdtVO> getAll(){
		return repository.getAllPdt();
	}
	
	public List<PdtVO> getAll(Map<String, String[]> map) {
		return HibernateUtil_CompositeQuery_Pdt.getAllC(map,sessionFactory.openSession());
	}
	
	public List<PdtVO> getAllFront(Map<String, String[]> map) {
		return HibernateUtil_CompositeQuery_Pdt_Front.getAllC(map,sessionFactory.openSession());
	}
	
	public List<PdtVO> getSalePdt(){
		return repository.getSalePdt();
	}


}
