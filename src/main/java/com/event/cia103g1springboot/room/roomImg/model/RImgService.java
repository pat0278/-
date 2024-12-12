package com.event.cia103g1springboot.room.roomImg.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.cia103g1springboot.hibernate.util.CompositeQuery.HibernateUtil_CompositeQuery_RImg;


@Service("roomImgService")
public class RImgService {

	@Autowired
	RImgRepository repository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addRImg(RImgVO rImgVO) {
		repository.save(rImgVO);
	}
	
	public void updateRImg(RImgVO rImgVO) {
		repository.save(rImgVO);
	}
	
	public void deleteRImg(Integer roomImgId) {
		if(repository.existsById(roomImgId)) {
			repository.deleteByroomImgId(roomImgId);
		}
	}
	
	public RImgVO getOneRoomImg(Integer roomImgId) {
		Optional<RImgVO> optional = repository.findById(roomImgId);
		return optional.orElse(null);
	}
	
	public List<RImgVO> getAllRImg(){
		return repository.findAll();
	}
	
	public List<RImgVO> getByroomTypeId(Integer roomTypeId){
		return repository.getByroomTypeId(roomTypeId);
	}
	
	public List<RImgVO> getAllRImg(Map<String, String[]> map){
		return HibernateUtil_CompositeQuery_RImg.getAllC(map,sessionFactory.openSession());
	}
	
	public List<RImgVO> addRImgs(List<RImgVO> rImgVOs) {
		List<RImgVO> newRimgs = new ArrayList<>();
		for(RImgVO rImgVO : rImgVOs) {
			repository.save(rImgVO);
			newRimgs.add(rImgVO);
		}
		return newRimgs;
	}
	
}