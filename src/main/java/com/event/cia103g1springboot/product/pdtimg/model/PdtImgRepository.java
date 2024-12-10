package com.event.cia103g1springboot.product.pdtimg.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface PdtImgRepository extends JpaRepository<PdtImgVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from productImg where pdtImgId =?1", nativeQuery = true)
	void deleteBypdtImgId(int pdtImgId);
	
	@Transactional
	@Modifying
	@Query(value = "select* from productImg where pdtId =?1", nativeQuery = true)
	List<PdtImgVO> getByPdtId(int pdtId);
	

}
