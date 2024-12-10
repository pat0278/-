package com.event.cia103g1springboot.event.evtcontroller;

import com.event.cia103g1springboot.event.evtimgmodel.EvtImgService;
import com.event.cia103g1springboot.event.evtimgmodel.EvtImgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class EvtImgController {

    @Autowired
    EvtImgService evtImgService;

    //根據照片編號拿照片
    @GetMapping("/image/{imgId}")
    @ResponseBody
    public byte[] getImage(@PathVariable Integer imgId) {
        EvtImgVO img = evtImgService.getImageById(imgId);
        return img.getEvtImg();
    }
}


