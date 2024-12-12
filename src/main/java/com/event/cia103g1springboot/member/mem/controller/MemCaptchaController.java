package com.event.cia103g1springboot.member.mem.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@RestController
@RequestMapping("/mem")
public class MemCaptchaController {

	@Autowired
	private DefaultKaptcha defaultKaptcha;

	@GetMapping("/getCaptcha")
	public void getCaptcha(HttpServletResponse res, HttpSession session) throws IOException {
		String captchaString = defaultKaptcha.createText();
		session.setAttribute("captcha", captchaString);

		BufferedImage captchaImg = defaultKaptcha.createImage(captchaString);
		res.setContentType("img/jpeg");

		try (ServletOutputStream out = res.getOutputStream()) {
			ImageIO.write(captchaImg, "jpg", out);
		}
	}
}
