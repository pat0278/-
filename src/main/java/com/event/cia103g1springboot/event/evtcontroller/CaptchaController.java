package com.event.cia103g1springboot.event.evtcontroller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/generate")
    public void generateCaptcha(HttpServletResponse response,
                                @RequestParam String key) throws IOException {
        // 生成驗證碼文字
        String captchaText = kaptchaProducer.createText();
        // 保存到Redis
        redisTemplate.opsForValue().set(key, captchaText, 60, TimeUnit.SECONDS);
        // 生成圖片
        BufferedImage captchaImage = kaptchaProducer.createImage(captchaText);
        //不要快取
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 使用 try-with-resources 自動關閉資源
        try (OutputStream out = response.getOutputStream()) {
            ImageIO.write(captchaImage, "jpg", out);
            out.flush();
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyCaptcha(
            @RequestParam String key,
            @RequestParam String captcha) {
        String storedCaptcha = redisTemplate.opsForValue().get(key);
        boolean isValid = storedCaptcha != null && storedCaptcha.equalsIgnoreCase(captcha);
        // 驗證後馬上山
        if (isValid) {
            redisTemplate.delete(key);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isValid);
        return ResponseEntity.ok(response);
    }
}
