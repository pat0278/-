    package com.event.cia103g1springboot;

    import com.event.cia103g1springboot.event.evtmodel.EvtRepository;
    import com.event.cia103g1springboot.event.evtmodel.EvtService;
    import com.event.cia103g1springboot.event.evtordermodel.EvtOrderRepository;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;

    @SpringBootApplication
    public class Cia103G1SpringBootApplication {

        public static void main(String[] args) {
            SpringApplication.run(Cia103G1SpringBootApplication.class, args);
        }
        @Bean
        CommandLineRunner runner(EvtRepository evtRepository, EvtService evtService,EvtOrderRepository evtOrderRepository ) {
            return args -> {

//                LocalDate now = LocalDate.now();
//                LocalDate weekStart = now.with(DayOfWeek.MONDAY);
//                LocalDate weekEnd = weekStart.plusDays(6);
//
//                // 轉換為 Timestamp
//                Timestamp startTimestamp = Timestamp.valueOf(weekStart.atStartOfDay());
//                Timestamp endTimestamp = Timestamp.valueOf(weekEnd.atTime(23, 59, 59));
//
//                // 查詢活動
//                List<EvtVO> events = evtRepository.findEventsForWeek(startTimestamp, endTimestamp);
//
//                // 輸出查詢結果
//                System.out.println("====== 測試活動查詢 ======");
//                System.out.println("查詢時間範圍：" + startTimestamp + " 到 " + endTimestamp);
//                System.out.println("找到活動數量：" + events.size());
//
//                events.forEach(event -> {
//                    System.out.println("------------------------");
//                    System.out.println("活動ID：" + event.getEvtId());
//                    System.out.println("活動名稱：" + event.getEvtName());
//                    System.out.println("活動日期：" + event.getEvtDate());
//                    System.out.println("開始時間：" + event.getEvtStart());
//                    System.out.println("報名截止：" + event.getEvtDeadLine());
//                    System.out.println("目前人數：" + event.getEvtAttend() + "/" + event.getEvtMax());
//                    System.out.println("活動狀態：" + event.getEvtStat());
//                });
//                List<EvtVO> evtList = evtRepository.findAll();
//
//                if (!evtList.isEmpty()) {
//                    // 遍历所有 EvtVO
//                    evtList.forEach(vo -> {
//                        Set<EvtOrderVO> evtOrderVO = vo.getEvtOrders();
//                        evtOrderVO.forEach(evtOrder -> System.out.println(evtOrder.getEvtOrderId()));
//                    });
//                } else {
//                    System.out.println("未找到任何 EvtVO!");
//                }
            };
        }
//                EvtVO vo = new EvtVO("哈哈","ssss",Timestamp.valueOf("1999-02-23 00:00:00"),Timestamp.valueOf("1999-02-23 00:00:00"),Timestamp.valueOf("1999-02-23 00:00:00"),30,40,2);
//                evtRepository.save(vo);
//                    evtRepository.findAll().forEach(s);
//                System.out.println(evtService.getOneEvt(101));
//                evtOrderRepository.findAll(Sort.by(Sort.Direction.DESC,"evtDate")).forEach(System.out::println);
//                System.out.println(evtRepository.findByEvtDate(Timestamp.valueOf("1999-02-23 00:00:00")));
//                System.out.println(evtRepository.count());
//                System.out.println("我是大英雄")


    };










