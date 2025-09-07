package com.edu.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    private Long id;
    private String name;
    private String code;
    private CouponType type;
    private BigDecimal discountValue; // 折扣值
    private BigDecimal minAmount; // 最小使用金额
    private Integer totalCount; // 总数量
    private Integer usedCount = 0; // 已使用数量
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean enabled = true;
    private LocalDateTime createTime = LocalDateTime.now();
    
    // 临时字段，用于前端显示状态
    private String status;
    
    public enum CouponType {
        FIXED, // 固定金额
        PERCENT // 百分比折扣
    }
}