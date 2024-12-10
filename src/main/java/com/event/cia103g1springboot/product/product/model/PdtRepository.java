package com.event.cia103g1springboot.product.product.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface PdtRepository extends JpaRepository<PdtVO,Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET pdtStat = 2 WHERE pdtId = ?1", nativeQuery = true)
	void deleteByPdtId(int pdtId);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM product WHERE pdtStat <2", nativeQuery = true)
	List<PdtVO> getAllPdt();

	//● (自訂)條件查詢
	@Query(value = "from PdtVO where pdtId=?1 and pdtTypeId =?2 and pdtStat=?3 and pdtName like?4 and pdtDesc like?5 and pdtPrice between ?6 and ?7 order by pdtId")
	List<PdtVO> findByOthers(int pdtId ,int pdtTypeId,Byte pdtStat  ,String pdtName ,String pdtDesc, int pdtPrice);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM product WHERE pdtStat =1", nativeQuery = true)
	List<PdtVO> getSalePdt();
}
