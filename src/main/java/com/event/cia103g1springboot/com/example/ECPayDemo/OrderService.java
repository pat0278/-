package com.event.cia103g1springboot.com.example.ECPayDemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.event.cia103g1springboot.ecpay.payment.integration.AllInOne;
import com.event.cia103g1springboot.ecpay.payment.integration.domain.AioCheckOutALL;


@Service
public class OrderService {

//	public String ecpayCheckout() {
//
//		String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
//		AllInOne all = new AllInOne("");
//
//		AioCheckOutALL obj = new AioCheckOutALL();
//		obj.setMerchantTradeNo(uuId);
//		obj.setMerchantTradeDate("2017/01/01 08:05:23");
//		obj.setTotalAmount("50");
//		obj.setTradeDesc("test Description");
//		obj.setItemName("TestItem");
//		obj.setReturnURL("<http://211.23.128.214:5000>");
//		obj.setNeedExtraPaidInfo("N");
//		String form = all.aioCheckOut(obj, null);
//
//		return form;
//	}
}
