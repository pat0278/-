package com.event.cia103g1springboot.configuration;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
    @Configuration
    public class KaptchaConfig {
        @Bean
        public Producer kaptchaProducer() {
            DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
            Properties properties = new Properties();

            // 邊框設定
            properties.setProperty("kaptcha.border", "no");

            // 圖片尺寸
            properties.setProperty("kaptcha.image.width", "160");  // 加寬一點
            properties.setProperty("kaptcha.image.height", "50");  // 加高一點

            // 文字相關設定
            properties.setProperty("kaptcha.textproducer.font.size", "38");  // 字體加大
            properties.setProperty("kaptcha.textproducer.font.color", "0,102,204");  // 使用深藍色
            properties.setProperty("kaptcha.textproducer.char.space", "5");  // 字符間距
            properties.setProperty("kaptcha.textproducer.char.length", "4");  // 驗證碼長度

            // 字體設定
            properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");  // 使用更清晰的字體

            // 背景設定
            properties.setProperty("kaptcha.background.clear.from", "white");  // 漸層
            properties.setProperty("kaptcha.background.clear.to", "white");

            // 雜訊
            properties.setProperty("kaptcha.noise.color", "114,175,255");
            properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");

            // 文字扭曲設定 看不懂= =
            properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");  // 水波紋效果

            Config config = new Config(properties);
            defaultKaptcha.setConfig(config);
            return defaultKaptcha;
        }
    }

