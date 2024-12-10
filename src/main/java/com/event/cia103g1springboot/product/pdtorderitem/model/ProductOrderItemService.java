package com.event.cia103g1springboot.product.pdtorderitem.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productOrderItemService")
public class ProductOrderItemService {

	@Autowired
	private ProductOrderItemRepository repository;


//	public ProductOrderItemVO getOrderItem(Integer pdtOrderId, Integer pdtId) {
//	    ProductOrderItemKey key = new ProductOrderItemKey(pdtOrderId, pdtId);
//	    return repository.findById(key).orElse(null);
//	}
	
	public void addProductOrderItem(ProductOrderItemVO productOrderItemVO) {
		repository.save(productOrderItemVO);
	}
	
	//pdtOrderId搜尋訂單
	public List<ProductOrderItemVO> getOrderItemByPdtOrderId(Integer pdtOrderId){
		return repository.findByPdtOrderId(pdtOrderId);
	}
}
