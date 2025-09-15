package com.bafangwy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon {
    private Long id;
    private Long userId;
    private Long couponId;
    private User user; // 保留用于业务逻辑
    private Coupon coupon; // 保留用于业务逻辑
    
    // 优惠券基本信息
    private String couponName;
    private String couponCode;
    private String description;
    private CouponType type;
    private BigDecimal discountValue; // 折扣值或减免金额
    private BigDecimal minAmount; // 最小使用金额
    
    // 状态和时间
    private CouponStatus status = CouponStatus.UNUSED;
    private LocalDateTime receiveTime = LocalDateTime.now();
    private LocalDateTime useTime;
    private LocalDateTime expireTime; // 过期时间
    
    // 使用限制
    private String usageRestriction; // 使用限制描述
    private String applicableCategory; // 适用分类
    
    // 订单信息
    private Long orderId;
    private Order order; // 保留用于业务逻辑
    
    public enum CouponStatus {
        UNUSED("可使用"), 
        USED("已使用"), 
        EXPIRED("已过期");
        
        private final String description;
        
        CouponStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    public enum CouponType {
        FIXED("满减券"), 
        PERCENT("折扣券");
        
        private final String description;
        
        CouponType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // 检查优惠券是否已过期
    public boolean isExpired() {
        return expireTime != null && LocalDateTime.now().isAfter(expireTime);
    }
    
    // 检查优惠券是否可用
    public boolean isAvailable() {
        return status == CouponStatus.UNUSED && !isExpired();
    }
}