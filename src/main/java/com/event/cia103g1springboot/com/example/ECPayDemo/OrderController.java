package com.event.cia103g1springboot.com.example.ECPayDemo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.cia103g1springboot.ecpay.payment.integration.AllInOne;
import com.event.cia103g1springboot.ecpay.payment.integration.domain.AioCheckOutALL;


@RestController
public class OrderController {

	@Autowired
	OrderService orderService;

//	@PostMapping("/ecpayCheckout")
//	public String ecpayCheckout(Model model) {
//		
//		String aioCheckOutALLForm = orderService.ecpayCheckout();
//
//		return aioCheckOutALLForm;
//	}
}