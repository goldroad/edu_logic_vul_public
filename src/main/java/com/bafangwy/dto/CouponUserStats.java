package com.bafangwy.dto;

import java.time.LocalDateTime;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
public class CouponUserStats {
    private Long userId;
    private String username;
    private String realName;
    private String phone;
    private String email;
    private String status;
    private LocalDateTime receiveTime;
    private LocalDateTime useTime;

    public CouponUserStats() {}

    public CouponUserStats(Long userId, String username, String realName, String phone, 
                         String email, String status, LocalDateTime receiveTime, LocalDateTime useTime) {
        this.userId = userId;
        this.username = username;
        this.realName = realName;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.receiveTime = receiveTime;
        this.useTime = useTime;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getReceiveTime() { return receiveTime; }
    public void setReceiveTime(LocalDateTime receiveTime) { this.receiveTime = receiveTime; }

    public LocalDateTime getUseTime() { return useTime; }
    public void setUseTime(LocalDateTime useTime) { this.useTime = useTime; }
}