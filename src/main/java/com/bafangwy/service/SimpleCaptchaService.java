package com.bafangwy.service;

import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Service
public class SimpleCaptchaService {
    
    private static final String UNIVERSAL_CAPTCHA = "WUYA";
    
    // 内存存储验证码，避免数据库问题
    private final Map<String, CaptchaData> captchaStore = new ConcurrentHashMap<>();
    
    /**
     * 生成验证码
     */
    public String generateCaptcha(String sessionId) {
        String code = generateRandomCode(4);
        CaptchaData captchaData = new CaptchaData(code, LocalDateTime.now().plusMinutes(5));
        captchaStore.put(sessionId, captchaData);
        return code;
    }
    
    /**
     * 生成验证码图片
     */
    public byte[] generateCaptchaImage(String sessionId) {
        // 先检查是否已有验证码，如果没有则生成新的
        CaptchaData existingData = captchaStore.get(sessionId);
        String code;
        if (existingData == null || existingData.expireTime.isBefore(LocalDateTime.now())) {
            code = generateCaptcha(sessionId);
        } else {
            code = existingData.code;
        }
        return createImageBytes(code);
    }
    
    /**
     * 验证验证码
     */
    public boolean verifyCaptcha(String sessionId, String inputCode) {
        // 万能验证码
        if (UNIVERSAL_CAPTCHA.equalsIgnoreCase(inputCode)) {
            return true;
        }
        
        CaptchaData captchaData = captchaStore.get(sessionId);
        if (captchaData == null) {
            return false;
        }
        
        // 检查是否过期
        if (captchaData.expireTime.isBefore(LocalDateTime.now())) {
            captchaStore.remove(sessionId);
            return false;
        }
        
        // 验证码匹配
        if (captchaData.code.equalsIgnoreCase(inputCode)) {
            captchaStore.remove(sessionId); // 使用后删除
            return true;
        }
        
        return false;
    }
    
    /**
     * 获取验证码（用于隐藏字段）
     */
    public String getCaptchaCode(String sessionId) {
        CaptchaData captchaData = captchaStore.get(sessionId);
        return captchaData != null ? captchaData.code : "";
    }
    
    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    
    private byte[] createImageBytes(String code) {
        int width = 120;
        int height = 40;
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        // 设置字体
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        Random random = new Random();
        
        // 绘制验证码字符
        for (int i = 0; i < code.length(); i++) {
            // 随机颜色
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            
            // 随机位置
            int x = 20 + i * 20;
            int y = 25 + random.nextInt(10);
            
            g.drawString(String.valueOf(code.charAt(i)), x, y);
        }
        
        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        
        g.dispose();
        
        // 转换为字节数组
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
    
    /**
     * 清理过期验证码
     */
    public void cleanExpiredCaptchas() {
        LocalDateTime now = LocalDateTime.now();
        captchaStore.entrySet().removeIf(entry -> entry.getValue().expireTime.isBefore(now));
    }
    
    private static class CaptchaData {
        final String code;
        final LocalDateTime expireTime;
        
        CaptchaData(String code, LocalDateTime expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }
    }
}