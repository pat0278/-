package com.event.cia103g1springboot.plan.planorder.controller;
import com.event.cia103g1springboot.plan.planorder.model.PlanOrder;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoom;
import com.event.cia103g1springboot.plan.planroom.model.PlanRoomId;
import com.event.cia103g1springboot.room.roomtype.model.RTVO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/detail/{id}")
    public String planDetail(@PathVariable Integer id, Model model) {
        Plan plan = planService.findPlanById(id);
        model.addAttribute("plan", plan);
        return "plan/planfront/detail";
    }

    @PostMapping("/setRoom")
    @ResponseBody                                      //不是ㄚㄅJ要注意= =
    public ResponseEntity<String> setRoom(@RequestBody Map<String, String> roomData) {
        try {
            PlanRoom planRoom = planRoomService.findByRmTypeIdAndPlanId(
                    Integer.parseInt(roomData.get("roomTypeId")),
                    Integer.parseInt(roomData.get("planId"))
            );

            if (planRoom.getRoomQty() == 0) {
                return ResponseEntity.badRequest().body("此房型已無剩餘房間");
            }

            int requestQty = Integer.parseInt(roomData.get("roomQty"));
            if (planRoom.getRoomQty() < requestQty) {
                return ResponseEntity.badRequest().body("選擇房間數量超過可用數量");
            }


            String key = "plan:room:" + roomData.get("planId");
            // 用 hash 別用string那爛咚咚
            stringRedisTemplate.opsForHash().putAll(key, roomData);
            stringRedisTemplate.expire(key, 30, TimeUnit.MINUTES);
            String roomQty = roomData.get("roomQty");
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("不能存");
        }
    }


    @GetMapping("/attend/{id}")
    public String attend(@PathVariable Integer id, Model model) {
        Plan plan = planService.findPlanById(id);
        MemVO memVO = memService.findOneMem("benson000");
        String key = "plan:room:" + id;
        Map<Object, Object> roomData = stringRedisTemplate.opsForHash().entries(key);
        if (!roomData.isEmpty()) {
            try {
                stringRedisTemplate.expire(key, 15, TimeUnit.MINUTES);
                // 取得房間價格和數量並轉換為整數
                Object roomPriceObj = roomData.get("roomPrice");
                Object roomQtyObj = roomData.get("roomQty");

                int roomPrice = (roomPriceObj instanceof Number)
                        ? ((Number) roomPriceObj).intValue()
                        : Integer.parseInt(roomPriceObj.toString());

                int roomQty = (roomQtyObj instanceof Number)
                        ? ((Number) roomQtyObj).intValue()
                        : Integer.parseInt(roomQtyObj.toString());

                // 計算房間總價和最終總價
                int totalRoomPrice = roomPrice * roomQty;
                int totalPrice = plan.getPlanPrice() + totalRoomPrice;
                System.out.println(totalRoomPrice);
                // 添加到 model
                model.addAttribute("selectedRoom", roomData);
                model.addAttribute("totalRoomPrice", totalRoomPrice);
                model.addAttribute("totalPrice", totalPrice);
                stringRedisTemplate.opsForHash().put(key, "totalRoomPrice", String.valueOf(totalRoomPrice));
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/planord/detail/" + id;
            }
        } else {
            return "redirect:/planord/detail/" + id;
        }
        model.addAttribute("mem", memVO);
        model.addAttribute("plan", plan);

        return "plan/planfront/attendpage";
    }

    @PostMapping("/confirm/{id}")
    public String confirm(@PathVariable Integer id, @Valid PlanOrder planOrder, Model model) {
        MemVO memVO = memService.findOneMem("benson000");
        Plan plan = planService.findPlanById(id);
        plan.setAttEnd(plan.getAttEnd()+1);


        if (planOrder.getPayMethod() == 0) {  // 信用卡
            planOrder.setRemAcct(null);  // 清空匯款帳號
        } else if (planOrder.getPayMethod() == 1) {  // 匯款
            planOrder.setCardLast4(null);  // 清空信用卡資訊
        }

        //這是人寫的???超醜超髒
        String key = "plan:room:" + id;
        Map<Object, Object> roomData = stringRedisTemplate.opsForHash().entries(key);
        if (!roomData.isEmpty()) {
            try {
                model.addAttribute("roomData", roomData);;

                // 直接使用 Redis 中的數量來更新房間庫存
                Integer roomTypeId = Integer.parseInt(roomData.get("roomTypeId").toString());
                int selectedRoomQty = Integer.parseInt(roomData.get("roomQty").toString());


                //還要重算??
                int roomPrice = Integer.parseInt(roomData.get("roomPrice").toString());
                int roomQty = Integer.parseInt(roomData.get("roomQty").toString());
                int totalRoomPrice = roomPrice * roomQty;
                planOrder.setRoomPrice(totalRoomPrice);
                model.addAttribute("totalRoomPrice", totalRoomPrice);

                //拿ROOM因為是複合主鍵.....
                PlanRoom planRoom = planRoomService.findByRmTypeIdAndPlanId(roomTypeId, plan.getPlanId());
                planRoom.setRoomQty(planRoom.getRoomQty() - selectedRoomQty);
                planRoom.setReservedRoom(planRoom.getReservedRoom() + selectedRoomQty);
                planRoomService.save(planRoom);

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/planord/detail/" + id;
            }
        }
        planOrder.setPlan(plan);
        planOrder.setMemVO(memVO);
        planOrderService.addPlanOrder(planOrder);

        model.addAttribute("planord", planOrder);
        model.addAttribute("plan", plan);
        model.addAttribute("mem", memVO);

        try {
            planOrderService.sendPlanOrdMail(planOrder, roomData);
        } catch (MessagingException e) {
            e.printStackTrace();
            model.addAttribute("error", "郵件發送失敗");
        }
        stringRedisTemplate.delete(key);
        return "/plan/planfront/attendsucess";
    }
//    後端------------------------------------------------------
    @GetMapping("/listall")
    public String listAll(Model model) {
    List<PlanOrder> orders = planOrderService.findAllPlanOrders();
    model.addAttribute("list", orders);
    return "plan/planorder/planordlist";}


    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        PlanOrder order = planOrderService.findPlanOrderById(id);
        if (order == null) {
            return "redirect:/planorder/listall";
        }
        model.addAttribute("order", order);
        return "plan/planorder/view";
    }

    }


