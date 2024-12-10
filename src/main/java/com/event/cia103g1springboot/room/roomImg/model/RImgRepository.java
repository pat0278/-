package com.event.cia103g1springboot.room.roomImg.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface RImgRepository extends JpaRepository<RImgVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from RoomImg where roomImgId =?1", nativeQuery = true)
	void deleteByroomImgId(int roomImgId);
	
	@Transactional
	@Modifying
	@Query(value = "select* from RoomImg where roomTypeId =?1", nativeQuery = true)
	List<RImgVO> getByroomTypeId(int roomTypeId);
}
