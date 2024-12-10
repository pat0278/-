package com.event.cia103g1springboot.room.roomtype.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RTRepository extends JpaRepository<RTVO,Integer>{
	@Transactional
	@Modifying
	@Query(value = "delete  from roomType where roomTypeId =?1 ",nativeQuery = true)
	void deleteByRoomTypeId(int roomTypeId);

	@Modifying
	@Query("UPDATE RTVO rt SET rt.roomQty = :qty WHERE rt.roomTypeId = :roomTypeId")
	void updateRoomQty(@Param("roomTypeId") Integer roomTypeId, @Param("qty") Integer qty);
}

