package com.event.cia103g1springboot.configuration;
import com.event.cia103g1springboot.event.evtimgmodel.EvtImgService;
import com.event.cia103g1springboot.event.evtimgmodel.EvtImgVO;
import com.event.cia103g1springboot.event.evtmodel.EvtService;
import com.event.cia103g1springboot.event.evtmodel.EvtVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@ControllerAdvice
public class EvtExceptionHandler {

    @Autowired
    private EvtService evtService;

    @Autowired
    private EvtImgService evtImgService;

        @ExceptionHandler(BindException.class)
        public String handleBindException(
                HttpServletRequest request,
                BindException e,
                Model model) {

            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : e.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            String uri = request.getRequestURI();
            if (uri.contains("/event/add")) {
                EvtVO evtVO = (EvtVO) e.getTarget();
                //保留值
                model.addAttribute("evt", evtVO);
                model.addAttribute("errors", errorMap);
                return "back-end/evtaddpage";
            }

            if (uri.contains("/event/edit")) {
                //把抓到的錯誤轉成evtvo物件
                EvtVO evtVO = (EvtVO) e.getTarget();
                Integer evtId = evtVO.getEvtId();

                EvtVO evt = evtService.getOneEvt(evtId);
                List<EvtImgVO> evtImgs = evtImgService.getImagesByEvtId(evtId);

                evt.setEvtDesc(evtVO.getEvtDesc());
                evt.setEvtName(evtVO.getEvtName());
                evt.setEvtMax(evtVO.getEvtMax());
                evt.setEvtAttend(evtVO.getEvtAttend());

                model.addAttribute("evt", evt);
                model.addAttribute("evtImgs", evtImgs);
                model.addAttribute("errors", errorMap);
                return "back-end/evteditpage";
            }

            return "error";
        }
    }
