package com.bafangwy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNo;
    private Long userId; // 改为userId
    private Long courseId; // 改为courseId
    private User user; // 保留用于业务逻辑
    private Course course; // 保留用于业务逻辑
    private BigDecimal originalAmount; // 原价
    private BigDecimal discountAmount = BigDecimal.ZERO; // 折扣金额
    private BigDecimal shippingFee = BigDecimal.ZERO; // 运费
    private BigDecimal finalAmount; // 最终金额
    private Integer quantity = 1; // 数量
    private OrderStatus status = OrderStatus.PENDING;
    private PaymentMethod paymentMethod;
    private String paymentTransactionId;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime payTime;
    private String remark;
    
    public enum OrderStatus {
        PENDING, PAID, CANCELLED, REFUNDED
    }
    
    public enum PaymentMethod {
        ALIPAY, WECHAT, BALANCE
    }
}