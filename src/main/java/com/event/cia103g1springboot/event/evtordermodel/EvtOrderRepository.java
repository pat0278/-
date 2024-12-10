package com.event.cia103g1springboot.event.evtordermodel;

import com.event.cia103g1springboot.event.evtmodel.EvtVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvtOrderRepository extends JpaRepository<EvtOrderVO, Integer> {
    EvtOrderVO findByEvtVO(EvtVO evtVO);;
}