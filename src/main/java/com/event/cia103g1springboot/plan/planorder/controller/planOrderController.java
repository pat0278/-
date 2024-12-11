package com.event.cia103g1springboot.plan.planorder.controller;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrder;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.event.cia103g1springboot.member.mem.model.MemService;
import com.event.cia103g1springboot.member.mem.model.MemVO;
import com.event.cia103g1springboot.plan.plan.model.Plan;
import com.event.cia103g1springboot.plan.plan.model.PlanService;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrderService;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/planord")
@Controller
public class planOrderController {

    @Autowired
    PlanOrderService planOrderService;

    @Autowired
    PlanService planService;

    @Autowired
    PlanRoomService planRoomService;

    @Autowired
    MemService memService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping("/detail/{id}")
    public String planDetail(@PathVariable Integer id, Model model) {
        Plan plan = planService.findPlanById(id);

        if (plan == null) {
            throw new RuntimeException("Plan not found with ID: " + id); // 防止空值問題
        }

        // 為前端準備房型數據
        List<Map<String, Object>> roomDataList = new ArrayList<>();
        for (PlanRoom planRoom : plan.getPlanRoom()) {
            Map<String, Object> roomData = new HashMap<>();
            roomData.put("roomTypeId", planRoom.getRoomTypeId());
            roomData.put("roomTypeName", planRoom.getRtvo().getRoomTypeName());
            roomData.put("roomPrice", planRoom.getRtvo().getRoomPrice());
            roomData.put("roomQty", planRoom.getRoomQty());
            roomData.put("description", planRoom.getRtvo().getRoomTypeDesc());
            roomData.put("occupancy", planRoom.getRtvo().getOccupancy());
            roomDataList.add(roomData);
        }

        // 將數據轉換為 JSON 字符串
        try {
            ObjectMapper mapper = new ObjectMapper();
            String roomDataJson = mapper.writeValueAsString(roomDataList);
            model.addAttribute("roomDataJson", roomDataJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("plan", plan);
        return "plan/planfront/detail";
    }

    @PostMapping("/api/setRooms")
    @ResponseBody
    public ResponseEntity<?> setRooms(@RequestBody RoomSelectionRequest request) {

        try {
            String cartKey = "plan:cart:" + request.getPlanId();

            Map<String, String> cartData = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            cartData.put("rooms", mapper.writeValueAsString(request.getRooms()));
            cartData.put("totalPrice", String.valueOf(request.getTotalPrice()));
            cartData.put("planId", String.valueOf(request.getPlanId()));

            redisTemplate.opsForHash().putAll(cartKey, cartData);
            redisTemplate.expire(cartKey, 30, TimeUnit.MINUTES);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping("/cart/{planId}")
    public ResponseEntity<?> getCart(@PathVariable Integer planId) {
        System.out.println("收到請求，planId: " + planId);
        String cartKey = "plan:cart:" + planId;

        try {
            // 从 Redis 获取数据
            Map<Object, Object> cartData = redisTemplate.opsForHash().entries(cartKey);

            // 检查是否有数据
            if (cartData.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // 使用 ObjectMapper 解析房型数据
            ObjectMapper mapper = new ObjectMapper();
            String roomsJson = (String) cartData.get("rooms");
            List<RoomSelection> rooms = mapper.readValue(roomsJson, new TypeReference<List<RoomSelection>>() {});

            // 更新 Redis 数据过期时间
            redisTemplate.expire(cartKey, 15, TimeUnit.MINUTES);
            System.out.println("Redis 數據: " + cartData);
            // 返回解析后的数据
            return ResponseEntity.ok(Map.of(
                    "rooms", rooms,
                    "totalPrice", cartData.get("totalPrice")
            ));
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }



    @GetMapping("/attend/{id}")
    public String attend(@PathVariable Integer id, Model model) {
        Plan plan = planService.findPlanById(id);
        MemVO mem = memService.findOneMem("benson000");
        String cartKey = "plan:cart:" + id;
        model.addAttribute("mem", mem);
        model.addAttribute("plan", plan);

        try {
            Map<Object, Object> cartData = redisTemplate.opsForHash().entries(cartKey);

            if (cartData.isEmpty()) {
                return "redirect:/planord/detail/" + id;
            }

            ObjectMapper mapper = new ObjectMapper();
            List<RoomSelection> rooms = mapper.readValue(
                    (String) cartData.get("rooms"),
                    new TypeReference<List<RoomSelection>>() {}
            );

            // 計算房間總價
            int totalRoomPrice = rooms.stream()
                    .mapToInt(room -> room.getRoomPrice() * room.getQuantity())
                    .sum();

            // 計算總價（含行程價格）
            int totalPrice = totalRoomPrice + plan.getPlanPrice();
//            System.out.println(plan.getPlanPrice());
            // 更新 Redis 過期時間
            redisTemplate.expire(cartKey, 15, TimeUnit.MINUTES);
//            System.out.println("rooms data from Redis: " + cartData.get("rooms"));
            // 添加所有需要的數據到模型
            model.addAttribute("selectedRooms", rooms);
            model.addAttribute("totalRoomPrice", totalRoomPrice);
            model.addAttribute("totalPrice", totalPrice);

            return "plan/planfront/attendpage";
        } catch (Exception e) {
            System.out.println("Exception caught!");
            e.printStackTrace();
            return "redirect:/planord/detail/" + id;
        }
    }




@PostMapping("/confirm/{id}")
public String confirm(@PathVariable Integer id, @Valid PlanOrder planOrder,
                      @RequestParam("rooms") String roomsJson, Model model) {
    try {
        ObjectMapper mapper = new ObjectMapper();
        List<RoomSelection> selectedRooms = mapper.readValue(roomsJson,
                new TypeReference<List<RoomSelection>>() {});

        //拿會員跟行程先寫死
        MemVO memVO = memService.findOneMem("benson000");
        Plan plan = planService.findPlanById(id);


        plan.setAttEnd(plan.getAttEnd() + 1);

        // 處理付款
        if (planOrder.getPayMethod() == 0) {
            planOrder.setRemAcct(null);
        } else if (planOrder.getPayMethod() == 1) {
            planOrder.setCardLast4(null);
        }

        // 計算總房價
        int totalRoomPrice = selectedRooms.stream()
                .mapToInt(room -> room.getRoomPrice() * room.getQuantity())
                .sum();

        planOrder.setRoomPrice(totalRoomPrice);

        int totalPrice = totalRoomPrice + plan.getPlanPrice();
        // 更新每個房型的庫存
        for (RoomSelection roomSelection : selectedRooms) {
            PlanRoom planRoom = planRoomService.findByRmTypeIdAndPlanId(
                    roomSelection.getRoomTypeId(), plan.getPlanId());

            if (planRoom.getRoomQty() < roomSelection.getQuantity()) {
                throw new RuntimeException("房間數量不足");
            }

            planRoom.setRoomQty(planRoom.getRoomQty() - roomSelection.getQuantity());
            planRoom.setReservedRoom(planRoom.getReservedRoom() + roomSelection.getQuantity());
            planRoomService.save(planRoom);
        }

        // 設置訂單關聯
        planOrder.setPlan(plan);
        planOrder.setMemVO(memVO);

        // 保存訂單
        PlanOrder savedOrder = planOrderService.addPlanOrder(planOrder);

        // 發mail
        try {
            planOrderService.sendPlanOrdMail(savedOrder, selectedRooms);
        } catch (MessagingException e) {
            e.printStackTrace();
            model.addAttribute("error", "郵件發送失敗");
        }

        // 清除 Redis 購物車數據
        String cartKey = "plan:cart:" + id;
        redisTemplate.delete(cartKey);

        model.addAttribute("totalprice", totalPrice);
        model.addAttribute("planord", savedOrder);
        model.addAttribute("plan", plan);
        model.addAttribute("mem", memVO);
        model.addAttribute("selectedRooms", selectedRooms);

        return "plan/planfront/attendsucess";

    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("error", "訂單提交失敗：" + e.getMessage());
        return "redirect:/planord/detail/" + id;
    }
}

    //    後端------------------------------------------------------
    @GetMapping("/listall")
    public String listAll(Model model) {
        List<PlanOrder> orders = planOrderService.findAllPlanOrders();
        model.addAttribute("list", orders);
        return "plan/planorder/planordlist";
    }


    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        PlanOrder order = planOrderService.findPlanOrderById(id);
        if (order == null) {
            return "redirect:/planorder/listall";
        }
        model.addAttribute("order", order);
        return "plan/planorder/view";
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RoomSelectionRequest {
        private Integer planId;
        private List<RoomSelection> rooms;
        private Integer totalPrice;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RoomSelection {
        private Long id;
        private Integer roomTypeId;
        private String roomTypeName;
        private Integer roomPrice;
        private Integer quantity;
        private Integer maxQuantity;
    }
}



