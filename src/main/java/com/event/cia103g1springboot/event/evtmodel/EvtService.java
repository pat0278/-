package com.event.cia103g1springboot.event.evtmodel;
import com.event.cia103g1springboot.event.evtimgmodel.EvtImgService;
import com.event.cia103g1springboot.event.evtimgmodel.EvtImgVO;
import com.event.cia103g1springboot.event.evtordermodel.EvtOrderService;
import com.event.cia103g1springboot.event.evtordermodel.EvtOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EvtService {

    @Autowired
    EvtRepository evtRepository;

    @Autowired
    EvtOrderService evtOrderService;

    @Autowired
    EvtImgService evtImgService;

    @Transactional
    public EvtVO addEvt(EvtVO evtVO) {
        return evtRepository.save(evtVO);
    }

    @Transactional
    public void updateEvtStatus(Integer evtId, Integer evtStatus) {
        Optional<EvtVO> optional = evtRepository.findById(evtId);
        EvtVO evtVO = optional.get();
        evtVO.setEvtStat(evtStatus);
        evtRepository.save(evtVO);
    }

    public EvtVO getOneEvt(Integer evtId) {
        Optional<EvtVO> optional = evtRepository.findByEvtId(evtId);
        return optional.orElse(new EvtVO());
    }


    public Page<EvtVO> getAllEvts(int page) {
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by("evtId").descending());
        Page<EvtVO> evtPage = evtRepository.findAll(pageRequest);

        //報名人數如果大於等於上限 直接改狀態
        for (EvtVO event : evtPage.getContent()) {
            event.getEvtMax().equals(event.getEvtAttend());
            if (event.getEvtStat() == 1 &&
                    event.getEvtMax() != null && event.getEvtMax().equals(event.getEvtAttend())) {
                event.setEvtStat(3);
                evtRepository.save(event);
            }
        }

        return evtRepository.findAll(pageRequest);
    }

    //maybe用到
    public List<EvtVO> getAllEvts() {
        return evtRepository.findAll();
    }



    public List<EvtVO> getEventsForWeek(LocalDate date) {
        LocalDate weekStart = date;
        LocalDate weekEnd = date.plusDays(6);

        LocalDateTime startDateTime = weekStart.atStartOfDay();
        LocalDateTime endDateTime = weekEnd.atTime(23, 59, 59);
        return evtRepository.findEventsForWeek(startDateTime, endDateTime);
    }


    public List<EvtVO> findByEvtStatOrderByEvtDateAsc(Integer status) {
        return evtRepository.findByEvtStatOrderByEvtDateAsc(status);
    }

    public List<EvtVO> findByEvtStatOrderByEvtDateAsc2(Integer status1,Integer status2) {
        return evtRepository.findByEvtStatOrEvtStatOrderByEvtDateAsc(status1, status2);
    }

    public void attend(Integer eventId, EvtOrderVO evtOrderVO) {
        // 1. 取得活動資訊
        EvtVO event = getOneEvt(eventId);
        evtOrderVO.setEvtVO(event);

        // 2. 檢查並更新報名人數
        int attendTotal = event.getEvtAttend() + evtOrderVO.getEvtAttend();
        if (attendTotal > event.getEvtMax()) {
            throw new IllegalArgumentException("報名人數超過活動上限");
        }

        // 3. 更新活動參與人數
        event.setEvtAttend(attendTotal);
        addEvt(event);

        // 4. 新增訂單
        evtOrderService.addEvtOrder(evtOrderVO);
    }

    @Transactional
    public EvtVO addEventWithImages(EvtVO evtVO, MultipartFile[] files) throws IOException {
        // 1. 儲存活動
        EvtVO savedEvt = addEvt(evtVO);
        String uploadPath = "src/main/resources/static/images";
        // 2. 處理圖片上傳
        if (files != null && files.length > 0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(
                            originalFilename.lastIndexOf("."));
                    String fileName = System.currentTimeMillis() + extension;

                    File upload = new File(uploadDir, fileName);
                    file.transferTo(upload);

                    EvtImgVO evtImgVO = new EvtImgVO(
                            fileName,
                            Files.readAllBytes(upload.toPath())
                    );
                    evtImgVO.setEvtVO(savedEvt);
                    evtImgService.addEventImage(evtImgVO);
                }
            }
        }
        return savedEvt;
    }
}











