package com.event.cia103g1springboot.room.roomImg.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_RImg;

@Service("roomImgService")
public class RImgService {

	@Autowired
	RImgRepository reponsitory;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addRImg(RImgVO rImgVO) {
		reponsitory.save(rImgVO);
	}
	
	public void updateRImg(RImgVO rImgVO) {
		reponsitory.save(rImgVO);
	}
	
	public void deleteRImg(Integer roomImgId) {
		if(reponsitory.existsById(roomImgId)) {
			reponsitory.deleteByroomImgId(roomImgId);
		}
	}
	
	public RImgVO getOneRoomImg(Integer roomImgId) {
		Optional<RImgVO> optional = reponsitory.findById(roomImgId);
		return optional.orElse(null);
	}
	
	public List<RImgVO> getAllRImg(){
		return reponsitory.findAll();
	}

	//記得家回來
//	public List<RImgVO> getAllRImg(Map<String, String[]> map){
//		return HibernateUtil_CompositeQuery_RImg.getAllC(map,sessionFactory.openSession());
//	}
	
	public void addRImgs(List<RImgVO> rImgVOs) {
		for(RImgVO rImgVO : rImgVOs) {
			reponsitory.save(rImgVO);
		}
	}
	
}
