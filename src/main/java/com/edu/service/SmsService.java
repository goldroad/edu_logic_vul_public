package com.edu.service;

import com.edu.entity.SmsRecord;
import com.edu.repository.SmsRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Service
public class SmsService {
    
    @Autowired
    private SmsRecordRepository smsRecordRepository;
    
    /**
     * 发送验证码
     */
    public String sendVerificationCode(String phone) {
        // 生成6位随机验证码
        String code = generateVerificationCode();
        String content = "您的验证码是：" + code + "，有效期1分钟，请勿泄露给他人。";
        
        // 创建短信记录
        SmsRecord smsRecord = new SmsRecord(phone, code, content);
        
        // 保存到数据库
        smsRecordRepository.insert(smsRecord);
        
        return code;
    }
    
    /**
     * 验证验证码
     */
    public boolean verifyCode(String phone, String code) {
        SmsRecord record = smsRecordRepository.findValidCode(phone, code, LocalDateTime.now());
        if (record != null) {
            // 标记为已使用
            smsRecordRepository.markAsUsed(record.getId());
            return true;
        }
        return false;
    }
    
    /**
     * 获取所有短信记录
     */
    public List<SmsRecord> getAllRecords() {
        return smsRecordRepository.findAll();
    }
    
    /**
     * 获取今日发送数量
     */
    public int getTodayCount() {
        return smsRecordRepository.countByDate(LocalDate.now());
    }
    
    /**
     * 获取本月发送数量
     */
    public int getThisMonthCount() {
        LocalDate now = LocalDate.now();
        return smsRecordRepository.countByMonth(now.getYear(), now.getMonthValue());
    }
    
    /**
     * 获取总发送数量
     */
    public int getTotalCount() {
        return smsRecordRepository.countTotal();
    }
    
    /**
     * 生成4位验证码
     */
    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}