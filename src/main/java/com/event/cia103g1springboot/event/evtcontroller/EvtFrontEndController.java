package com.event.cia103g1springboot.event.evtcontroller;


import com.event.cia103g1springboot.event.evtimgmodel.EvtImgService;
import com.event.cia103g1springboot.event.evtimgmodel.EvtImgVO;
import com.event.cia103g1springboot.event.evtmodel.EvtService;
import com.event.cia103g1springboot.event.evtmodel.EvtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/front")
@Controller
public class EvtFrontEndController {
    @Autowired
    EvtService evtService;
    @Autowired
    EvtImgService evtImgService;



    //只拿上架活動
    @GetMapping("/list")
    public String listActiveEvents(Model model) {
        List<EvtVO> events = evtService.findByEvtStatOrderByEvtDateAsc2(1,3);
        model.addAttribute("events", events);
        return "front-end/evt/listpage";
    }

    //根據活動id拿照片跟活動內容
    @GetMapping("/detail/{id}")//
    public String showEventDetail(@PathVariable Integer id, Model model) {

        EvtVO event = evtService.getOneEvt(id);

        List<EvtImgVO> evtImgs = evtImgService.getImagesByEvtId(id);

        model.addAttribute("evtImgs", evtImgs);
        model.addAttribute("evt", event);
        return "front-end/evt/eventdetail";
    }
    
}