package com.event.cia103g1springboot.product.producttype.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;




public interface PdtTypeRepository extends JpaRepository<PdtTypeVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from producttype where pdtTypeId =?1", nativeQuery = true)
	void deleteByPdtTypeId(int pdtTypeId);

//	//● (自訂)條件查詢
//	@Query(value = "from PdtTypeVO where pdtTypeId=?1 and pdtTypeName like?2 and pdtTypeDesc like?3 order by pdtTypeId")
//	List<PdtTypeVO> findByOthers(int pdtTypeId , String pdtTypeName , String pdtTypeDesc);
//	
}
