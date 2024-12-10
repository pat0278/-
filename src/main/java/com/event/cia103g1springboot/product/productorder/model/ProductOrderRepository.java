package com.event.cia103g1springboot.product.productorder.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOrderRepository extends JpaRepository<ProductOrderVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from ProductOrder where pdtOrderId =?1", nativeQuery = true)
	void deleteByPdtOrderId(int pdtOrderId);
	
	//● (自訂)條件查詢
	@Query(value = "from ProductOrderVO where memId=?1")
	List<ProductOrderVO> findByMemId(int memId);
	
	//● (自訂)條件查詢
	@Query(value = "from ProductOrderVO where orderStat=?1")
	List<ProductOrderVO> findByOrderStat(int orderStat);
}
