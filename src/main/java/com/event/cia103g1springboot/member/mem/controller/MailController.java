package com.event.cia103g1springboot.member.mem.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.event.cia103g1springboot.member.mem.model.MemService;
import com.event.cia103g1springboot.member.mem.model.MemVO;

@Controller
@RequestMapping("/mem")
public class MailController {
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	MemService memSvc;

	@Transactional
	@PostMapping("/mail")
	public String registerMail(@ModelAttribute MemVO memVO, @RequestParam String email, HttpSession session,
			ModelMap model) throws MessagingException, UnsupportedEncodingException {
		session.removeAttribute("code");

		String randNum = "";
		for (int i = 0; i < 6; i++) {
			int num = (int) Math.floor((Math.random() * 9));
			randNum += num;
		}

		Context context = new Context();
		context.setVariable("code", randNum);

		String mailContent = templateEngine.process("front-end/mem/registerMail", context);

		MimeMessage message = mailSender.createMimeMessage();
		// 第二個參數 "multipart=true"表示可以內嵌圖片或副件
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(email.trim());
		helper.setSubject("鄰星嗨嗨會員認證信");
		helper.setText(mailContent, true);
		helper.setFrom("a0989159280@gmail.com", "鄰星嗨嗨有限公司");
		mailSender.send(message);

		session.setAttribute("code", randNum);

		return "front-end/mem/register";
	}

	
	@Transactional
	@PostMapping("modifyPwdMail")
	public String modifyPwd(@RequestAttribute("email") String email, ModelMap model)
			throws MessagingException, UnsupportedEncodingException {

		Context context = new Context();
		context.setVariable("back_url", "http://localhost:8080/mem/modifyPwd");

		String mailContent = templateEngine.process("frontend/mem/modifyPwdMail", context);

		MimeMessage message = mailSender.createMimeMessage();
		// 第二個參數 "multipart=true"表示可以內嵌圖片或副件
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(email);
		helper.setSubject("密碼修改通知信");
		helper.setText(mailContent, true);
		helper.setFrom("a0989159280@gmail.com", "鄰星嗨嗨有限公司");
		mailSender.send(message);

		model.addAttribute("mailSend", true);
		System.out.println("密碼修改信寄成功");
		return "frontend/mem/forgetpwd";
	}

//	@ModelAttribute("memVO")
//	MemVO getMemVO() {
//		MemVO mem = new MemVO();
//		return mem;
//	}
}
