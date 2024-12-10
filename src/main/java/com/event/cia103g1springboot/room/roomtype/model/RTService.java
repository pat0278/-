package com.event.cia103g1springboot.room.roomtype.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.cia103g1springboot.hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_RT;


@Service("roomType")
public class RTService {

	@Autowired
	RTRepository reponsitory;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addRT (RTVO rtVO) {
		reponsitory.save(rtVO);
	}
	
	@Transactional
	public void updateRT (RTVO rtVO) {
		reponsitory.save(rtVO);
	}
	
	public void deleteRT (Integer roomTypeId) {
		if( reponsitory.existsById(roomTypeId)) {
			reponsitory.deleteByRoomTypeId(roomTypeId);
		}
	}
	
	 @Transactional
	 public void updateRoomQty(Integer roomTypeId, Integer qty) {
	  reponsitory.updateRoomQty(roomTypeId, qty);
	 }
	
	public RTVO getOneRT(Integer rooomTypeId) {
		Optional<RTVO> optional = reponsitory.findById(rooomTypeId);
		return optional.orElse(null);
	}
	
	public List<RTVO> getAllRT(){
		return reponsitory.getAllRT();
	}
	
	public List<RTVO>getAllRT(Map<String,String[]> map){
		return HibernateUtil_CompositeQuery_RT.getAllC(map,sessionFactory.openSession());
	}
}
