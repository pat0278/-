package com.event.cia103g1springboot.event.evtimgmodel;

import com.event.cia103g1springboot.event.evtmodel.EvtVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvtImgRepository extends JpaRepository<EvtImgVO,Integer> {
    List<EvtImgVO> findByEvtVO(EvtVO evtVO);
    List<EvtImgVO> findByEvtVOEvtId(Integer evtId);
    Optional<EvtImgVO> findById(Integer imgId);
}
