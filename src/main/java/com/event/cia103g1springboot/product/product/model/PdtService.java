package com.event.cia103g1springboot.product.product.model;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//複合查詢再打開
//import hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_Pdt;

@Service("pdtService")
public class PdtService {
	
	@Autowired
	PdtRepository repository;


	//阿芳ㄉ
	public Integer calculateTotal(List<CartVO> cart) {
		return (int) cart.stream().mapToDouble(CartVO::getSubtotal).sum();
	}

	public void addPdt (PdtVO pdtVO) {
		repository.save(pdtVO);
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
//		return optional.get();
		return optional.orElse(null);
	}
	
	public List<PdtVO> getAll(){
		return repository.getAllPdt();
	}


	//複合查詢 我沒有抓
//	public List<PdtVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Pdt.getAllC(map,sessionFactory.openSession());
//	}
	
	public List<PdtVO> getSalePdt(){
		return repository.getSalePdt();
	}
}
