package com.edu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsRecord {
    private Long id;
    private String phone;
    private String code;
    private String content;
    private LocalDateTime sendTime = LocalDateTime.now();
    private Boolean isUsed = false;
    private LocalDateTime expireTime;
    private String type = "REGISTER"; // 短信类型：REGISTER, LOGIN, RESET_PASSWORD等
    
    public SmsRecord(String phone, String code, String content) {
        this.phone = phone;
        this.code = code;
        this.content = content;
        this.sendTime = LocalDateTime.now();
        this.expireTime = LocalDateTime.now().plusMinutes(1); // 1分钟有效期
        this.isUsed = false;
    }
}