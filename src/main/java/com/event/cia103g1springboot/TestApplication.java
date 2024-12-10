package com.event.cia103g1springboot;

import com.event.cia103g1springboot.member.mem.model.MemRepository;
import com.event.cia103g1springboot.member.mem.model.MemVO;
import com.event.cia103g1springboot.member.notify.model.MemberNotifyRepository;
import com.event.cia103g1springboot.member.notify.model.MemberNotifyService;
import com.event.cia103g1springboot.member.notify.model.MemberNotifyVO;
import com.event.cia103g1springboot.plan.plan.model.Plan;
import com.event.cia103g1springboot.plan.plan.model.PlanRepository;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrder;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrderRepository;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrderService;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoom;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoomRepository;
import com.event.cia103g1springboot.plan.plantype.model.PlanType;
import com.event.cia103g1springboot.plan.plantype.model.PlanTypeRepository;
import com.event.cia103g1springboot.product.pdtorderitem.model.ProductOrderItemRepository;
import com.event.cia103g1springboot.product.pdtorderitem.model.ProductOrderItemVO;
import com.event.cia103g1springboot.product.product.model.PdtRepository;
import com.event.cia103g1springboot.product.product.model.PdtVO;
import com.event.cia103g1springboot.product.productorder.model.ProductOrderRepository;
import com.event.cia103g1springboot.product.productorder.model.ProductOrderService;
import com.event.cia103g1springboot.product.productorder.model.ProductOrderVO;
import com.event.cia103g1springboot.product.producttype.model.PdtTypeRepository;
import com.event.cia103g1springboot.product.producttype.model.PdtTypeService;
import com.event.cia103g1springboot.product.producttype.model.PdtTypeVO;
import com.event.cia103g1springboot.room.roomtype.model.RTRepository;
import com.event.cia103g1springboot.room.roomtype.model.RTVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class TestApplication implements CommandLineRunner{


    @Autowired
    RTRepository rtRepository;
    @Autowired
    PlanRoomRepository planRoomRepository;
    @Autowired
    PlanTypeRepository planTypeRepository;

    @Autowired
    PlanOrderRepository planOrderRepository;

    @Autowired
    PlanRepository planRepository;
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class);
    }
    @Autowired
    PlanOrderService planOrderService;

    @Autowired
    MemberNotifyService notifyService;

    @Autowired
    MemberNotifyRepository notifyRepository;

    @Autowired
    PdtTypeService ptTypeService;

    @Autowired
    PdtTypeRepository pdtTypeRepository;

    @Autowired
    PdtRepository pdtRepository;

    @Autowired
    ProductOrderItemRepository productOrderItemRepository;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    MemRepository memRepository;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        // 建立商品類型
            // 建立會員
            MemVO mem = new MemVO();
            mem.setName("測試會員");
            mem.setMemType(1);
            mem.setMemAcct("test123");
            mem.setMemPwd("Test123");
            mem.setBirth(java.sql.Date.valueOf("1990-01-01"));
            mem.setSex(1);
            mem.setEmail("test@test.com");
            mem.setTel("0912345678");
            mem.setAddr("台北市測試區");
            memRepository.save(mem);
            System.out.println("會員ID: " + mem.getMemId());

            // 建立商品類型
            PdtTypeVO type = new PdtTypeVO();
            type.setPdtTypeName("測試分類");
            type.setPdtTypeDesc("測試類別描述");
            pdtTypeRepository.save(type);
            System.out.println("商品類型ID: " + type.getPdtTypeId());

            // 建立商品
            PdtVO product = new PdtVO();
            product.setPdtName("測試商品");
            product.setPdtDesc("商品描述");
            product.setPdtPrice(100);
            product.setPdtStat((byte) 1);
            product.setPdtTypeVO(type);
            pdtRepository.save(product);
            System.out.println("商品ID: " + product.getPdtId());

            // 建立訂單
            ProductOrderVO order = new ProductOrderVO();
            order.setMemVO(mem);  // 直接設定會員物件
            order.setOrderAmt(100);
            order.setOrderStat(1);
            order.setPayMethod(1);
            order.setDelMethod(1);
            order.setRecName("測試收件人");
            order.setRecAddr("測試地址");
            order.setRecTel("0229876543");
            productOrderRepository.save(order);
            System.out.println("訂單ID: " + order.getPdtOrderId());

            // 建立訂單項目
            ProductOrderItemVO item = new ProductOrderItemVO();
            item.setPdtOrderId(order.getPdtOrderId());
            item.setPdtId(product.getPdtId());
            item.setPdtPrice(product.getPdtPrice());
            item.setPdtName(product.getPdtName());
            item.setOrderQty(1);
            productOrderItemRepository.save(item);
            System.out.println("訂單項目: orderId=" + item.getPdtOrderId() + ", pdtId=" + item.getPdtId());
            System.out.println("訂單項目: orderId=" + item.getPdtOrderId() + ", pdtId=" + item.getPdtId());
        }


    }

//        System.out.println("開始測試通知功能...");
//
//        // 使用現有會員 ID 創建通知
//        Integer existingMemberId = 1; // 替換成實際存在的會員 ID
//
//        try {
//            // 創建各種測試通知
//            createTestNotifications(existingMemberId);
//
//            // 測試查詢功能
//            testQueryFunctions(existingMemberId);
//
//            System.out.println("測試完成！");
//        } catch (Exception e) {
//            System.err.println("測試過程中發生錯誤：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void createTestNotifications(Integer memberId) {
//        try {
//            // 創建活動通知
//            MemberNotifyVO eventNotify = new MemberNotifyVO();
//            MemVO memVO = new MemVO();
//            memVO.setMemId(memberId);
//            eventNotify.setMember(memVO);
//            eventNotify.setNotifyType(1);
//            eventNotify.setNotifyCon("新活動通知：春季特賣會開始了！");
//            eventNotify.setIsRead(false);
//            eventNotify.setNotifyTime(LocalDateTime.now());
//            notifyRepository.save(eventNotify);
//            System.out.println("已創建活動通知");
//
//            // 創建訂單通知
//            MemberNotifyVO orderNotify = new MemberNotifyVO();
//            orderNotify.setMember(memVO);
//            orderNotify.setNotifyType(2);
//            orderNotify.setNotifyCon("訂單狀態更新：訂單已確認");
//            orderNotify.setIsRead(false);
//            orderNotify.setNotifyTime(LocalDateTime.now());
//            orderNotify.setBusinessKey("ORDER_001");
//            notifyRepository.save(orderNotify);
//            System.out.println("已創建訂單通知");
//
//            // 創建子通知（歷史記錄）
//            MemberNotifyVO historyNotify = new MemberNotifyVO();
//            historyNotify.setMember(memVO);
//            historyNotify.setNotifyType(2);
//            historyNotify.setNotifyCon("訂單歷史更新：處理中");
//            historyNotify.setIsRead(true);
//            historyNotify.setNotifyTime(LocalDateTime.now());
//            historyNotify.setParentId(orderNotify.getNotifyId()); // 使用剛創建的訂單通知 ID
//            notifyRepository.save(historyNotify);
//            System.out.println("已創建通知歷史記錄");
//
//        } catch (Exception e) {
//            System.err.println("創建通知時發生錯誤：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void testQueryFunctions(Integer memberId) {
//        try {
//            System.out.println("\n=== 測試查詢功能 ===");
//
//            // 1. 測試獲取所有主通知
//            System.out.println("\n1. 所有主通知：");
//            List<MemberNotifyVO> allNotifications = notifyService.getAllMainNotifications(memberId);
//            allNotifications.forEach(notify ->
//                    System.out.println("通知內容: " + notify.getNotifyCon())
//            );
//
//            // 2. 測試按類型查詢
//            System.out.println("\n2. 活動通知（類型1）：");
//            List<MemberNotifyVO> eventNotifications = notifyService.getMainNotificationsByType(memberId, 1);
//            eventNotifications.forEach(notify ->
//                    System.out.println("活動通知: " + notify.getNotifyCon())
//            );
//
//            // 3. 測試獲取未讀數量
//            System.out.println("\n3. 未讀通知數量：");
//            Long unreadCount = notifyService.getUnreadCount(memberId);
//            System.out.println("未讀數量: " + unreadCount);
//
//            // 4. 測試搜索功能
//            System.out.println("\n4. 搜索包含「訂單」的通知：");
//            List<MemberNotifyVO> searchResults = notifyService.searchNotifications(memberId, "訂單");
//            searchResults.forEach(notify ->
//                    System.out.println("搜索結果: " + notify.getNotifyCon())
//            );
//
//        } catch (Exception e) {
//            System.err.println("測試查詢功能時發生錯誤：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void testMarkAsRead(Integer memberId) {
//        try {
//            System.out.println("\n=== 測試標記已讀功能 ===");
//
//            // 顯示標記前的未讀數量
//            Long beforeCount = notifyService.getUnreadCount(memberId);
//            System.out.println("標記前未讀數量: " + beforeCount);
//
//            // 標記所有通知為已讀
//            notifyService.markAllAsRead(memberId);
//            System.out.println("已執行全部標記已讀");
//
//            // 顯示標記後的未讀數量
//            Long afterCount = notifyService.getUnreadCount(memberId);
//            System.out.println("標記後未讀數量: " + afterCount);
//
//        } catch (Exception e) {
//            System.err.println("測試標記已讀功能時發生錯誤：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}


//        PlanType planType = new PlanType();
//        planType.setPlanTypeId("PT001");
//        planType.setPlanName("基本行程");
//        planType.setPlanDay(3);
//        PlanType savedPlanType = planTypeRepository.save(planType);
//
//        // 創建Plan
//        Plan plan = new Plan();
//        plan.setPlanType(savedPlanType);  // 添加這行
//        plan.setStartDate(LocalDate.now());
//        plan.setEndDate(LocalDate.now().plusDays(7));
//        plan.setPlanPrice(5000);
//        plan.setAttMax(50);
//        plan.setAttEnd(0);
////
////// 創建RTVO
//        RTVO rtvo = new RTVO();
//        rtvo.setRoomTypeName("豪華雙人房");
//        rtvo.setRoomTypeDesc("寬敞舒適的雙人房");
//        rtvo.setRoomQty(10);
//        rtvo.setRoomPrice(2000);
//        rtvo.setOccupancy(2);
//
//// 保存基本實體
//        Plan savedPlan = planRepository.save(plan);
//        RTVO savedRtvo = rtRepository.save(rtvo);
//
//// 創建PlanRoom關聯
//        PlanRoom planRoom = new PlanRoom();
//        planRoom.setPlanId(savedPlan.getPlanId());
//        planRoom.setRoomTypeId(savedRtvo.getRoomTypeId());
//        planRoom.setRoomTypeName(savedRtvo.getRoomTypeName());
//        planRoom.setRoomPrice(savedRtvo.getRoomPrice());
//        planRoom.setRoomQty(5);
//        planRoom.setPlan(savedPlan);
//        planRoom.setRtvo(savedRtvo);
//
//        PlanOrder planOrder = new PlanOrder();
//        planOrder.setMemId(111);
//        planOrder.setMemId(3);
//        planOrder.setPlanPrice(1000);
//        planOrder.setRoomPrice(0);
//        planOrder.setOrderStat(0);
//        planOrder.setPayMethod(1);
//        planOrder.setPlan(savedPlan);
//        PlanOrder saveplanOrder = planOrderRepository.save(planOrder);
//
//
//// 設置關聯
//        Set<PlanRoom> planRooms = new HashSet<>();
//        planRooms.add(planRoom);
//        savedPlan.setPlanRoom(planRooms);
//        savedRtvo.setPlanRoom(planRooms);
//
//        Set<PlanOrder> planOrders = new HashSet<>();
//        planOrders.add(saveplanOrder);
//        planOrders.add(planOrder);
//        savedPlan.setPlanOrder(planOrders);
//
//
//// 保存關聯
//        planRepository.save(savedPlan);
//
//
//        System.out.println(rtvo.getPlanRoom());
//        System.out.println(planRoom.getRtvo());
//        System.out.println(plan.getPlanRoom());
//        System.out.println(planOrder.getPlan());
//        System.out.println("planoder="+plan.getPlanOrder());

//        PlanOrder planOrder = planOrderService.findPlanOrderById(1);
//        System.out.println(planOrder);

//        planTypeRepository.save(planType);
//
//
//        Plan plan = new Plan();
//        plan.setPlanType(planType);
//        plan.setPlanPrice(5000);
//        plan.setStartDate(LocalDate.from(LocalDateTime.of(2024, 1, 1, 8, 0)));
//        plan.setEndDate(LocalDate.from(LocalDateTime.of(2024, 1, 4, 8, 0))); // 結束日期加3天
//        plan.setAttMax(1);
//        plan.setAttEnd(10);
//        planRepository.save(plan);
//
//
//        RTVO roomType = new RTVO();
//        roomType.setRoomTypeName("雙人房");
//        roomType.setRoomPrice(2000);
//        roomType.setRoomTypeDesc("標準雙人房");
//        roomType.setRoomQty(5);
//        roomType.setOccupancy(2);
//        rtRepository.save(roomType);
//
//
//        PlanRoom planRoom = new PlanRoom();
//        planRoom.setPlanId(plan.getPlanId());
//        planRoom.setRoomTypeId(roomType.getRoomTypeId());
//        planRoom.setRtvo(roomType);
//        planRoom.setRoomTypeName("雙人房");
//        planRoom.setRoomPrice(2000);
//        planRoom.setRoomQty(2);
//        planRoom.setReservedRoom(0);
//        planRoomRepository.save(planRoom);
//             建立 PlanType, Plan
//        PlanType planType = new PlanType();
//        planType.setPlanTypeId("PT001");
//        planType.setPlanName("基本行程");
//        planType.setPlanDay(3);
//        planTypeRepository.save(planType);
//
//        Plan plan = new Plan();
//        plan.setPlanType(planType);
//        plan.setPlanPrice(5000);
//        plan.setStartDate(LocalDate.of(2024, 1, 1));
//        plan.setEndDate(LocalDate.of(2024, 1, 4));
//        plan.setAttEnd(1);
//        plan.setAttMax(10);
//        Plan savedPlan = planRepository.save(plan);
//
//        PlanOrder order = new PlanOrder();
//        order.setMemId(1);
//        order.setPlan(savedPlan);
//        order.setPlanPrice(savedPlan.getPlanPrice());
//        order.setRoomPrice(2000);
//        order.setOrderStat(0);
//        order.setPayMethod(1);
//        planOrderRepository.save(order);
//    }}




