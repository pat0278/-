package com.event.cia103g1springboot.product.producttype.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("pdtTypeService")
public class PdtTypeService {

	@Autowired
	PdtTypeRepository repository;

	
	public void addPdtType(PdtTypeVO pdtTypeVO) {
		repository.save(pdtTypeVO);
	}
	
	public void updatePdtType(PdtTypeVO pdtTypeVO) {
		repository.save(pdtTypeVO);
	}
	
	public void deletePdtType(Integer pdtTypeId) {
		if(repository.existsById(pdtTypeId))
		repository.deleteByPdtTypeId(pdtTypeId);
	}
	
	public PdtTypeVO getOnePdtType(Integer PdtTypeId) {
		Optional<PdtTypeVO> optional = repository.findById(PdtTypeId);
		return optional.orElse(null);
	}
	
	public List<PdtTypeVO> getAll() {
		return repository.findAll();
	}

//	public List<PdtTypeVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//	}
//	
	
	
	
	
//	private ProductTypeDAO_interface dao;
//	
//	public PdtTypeService() {
//		dao = new ProductTypeJDBCDAO();
//	}
	
////	新增
//	public PdtTypeVO addproductType (String productTypeName, String productTypeDesc) {
//		
//		PdtTypeVO pdtTypeVO = new PdtTypeVO();
//		
//		pdtTypeVO.setPdtTypeName(productTypeName);
//		pdtTypeVO.setPdtTypeDesc(productTypeDesc);
//		dao.insert(pdtTypeVO);
//		
//		return pdtTypeVO;
//	}
//	
////	修改
//	public PdtTypeVO updateproductType (String productTypeName, String productTypeDesc, Integer productTypeId) {
//		
//		PdtTypeVO pdtTypeVO = new PdtTypeVO();
//		
//		pdtTypeVO.setPdtTypeName(productTypeName);
//		pdtTypeVO.setPdtTypeDesc(productTypeDesc);
//		pdtTypeVO.setPdtTypeId(productTypeId);
//		dao.update(pdtTypeVO);
//		
//		return pdtTypeVO;
//	}
//	
////	刪除
//	public void deleteProductType (Integer productTypeId) {
//		dao.delete(productTypeId);
//	}
//	
////	get one
//	public PdtTypeVO getOneProductType(Integer productTypeId) {
//		return dao.findByPrimaryKey(productTypeId);
//	}
//	
////	list all
//	public List<PdtTypeVO> getAll(){
//		return dao.getAll();
//	}
	
}
