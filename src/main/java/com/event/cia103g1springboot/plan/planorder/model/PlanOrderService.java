package com.event.cia103g1springboot.plan.planorder.model;

import com.event.cia103g1springboot.plan.plan.model.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlanOrderService {
    @Autowired
    private PlanOrderRepository planOrderRepository;

    @Autowired
    PlanService planService;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    TemplateEngine templateEngine;

        @Transactional
        public PlanOrder addPlanOrder(PlanOrder planOrder) {
            return planOrderRepository.save(planOrder);  // 使用小寫的變量名
        }
        public List<PlanOrder> findAllPlanOrders() { return planOrderRepository.findAll(); }

        public PlanOrder findPlanOrderById(Integer id) {
            Optional<PlanOrder> planOrder = planOrderRepository.findById(id);
            return planOrder.orElse(new PlanOrder());
        };

        public void sendPlanOrdMail(PlanOrder order, Map<Object, Object> roomData) throws MessagingException {
        Integer roomPrice = Integer.parseInt(roomData.get("roomPrice").toString());
        int roomQty = Integer.parseInt(roomData.get("roomQty").toString());
        int totalRoomPrice = roomQty * roomPrice;
        Integer totalprice = order.getPlanPrice()+totalRoomPrice;
        Context context = new Context();
        context.setVariable("memberName", order.getMemVO().getName());
        context.setVariable("planName", order.getPlan().getPlanType().getPlanName());
        context.setVariable("roomType", roomData.get("roomTypeName"));
        context.setVariable("payMethod", order.getPayMethod());
        context.setVariable("totalPrice", totalprice);
        context.setVariable("startDate", order.getPlan().getStartDate());
        context.setVariable("orderDate", order.getOrderDate());
//        context.setVariable("totalRoomPrice", totalRoomPrice);

        String mailContent = templateEngine.process("plan/planfront/planemail", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo("mm950490@gmail.com");
        helper.setSubject("鄰星嗨嗨:行程訂單成立通知");
        helper.setText(mailContent, true);

        // 若是付款成功，添加圖片
            ClassPathResource footer = new ClassPathResource("static/email/planemail.png");
            helper.addInline("footer", footer);
        // 發送郵件
        mailSender.send(message);
    }
}

