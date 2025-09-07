package com.edu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    private Long id;
    private Long userId;
    private String username;
    private String ipAddress;
    private String userAgent;
    private String operatingSystem;
    private String browser;
    private String location;
    private LocalDateTime loginTime;
    private String status; // SUCCESS, FAILED
    private String sessionId;
    
    // 构造函数
    public LoginLog(Long userId, String username, String ipAddress, String userAgent, 
                   String operatingSystem, String browser, String location, String status, String sessionId) {
        this.userId = userId;
        this.username = username;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.operatingSystem = operatingSystem;
        this.browser = browser;
        this.location = location;
        this.loginTime = LocalDateTime.now();
        this.status = status;
        this.sessionId = sessionId;
    }
}