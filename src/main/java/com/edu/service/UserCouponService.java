package com.edu.service;

import com.edu.entity.UserCoupon;
import com.edu.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Service
public class UserCouponService {
    
    @Autowired
    private UserCouponRepository userCouponRepository;
    
    /**
     * 获取用户所有优惠券
     */
    public List<UserCoupon> getUserCoupons(Long userId) {
        List<UserCoupon> coupons = userCouponRepository.findByUserId(userId);
        // 更新过期状态
        updateExpiredStatus(coupons);
        return coupons;
    }
    
    /**
     * 根据状态获取用户优惠券
     */
    public List<UserCoupon> getUserCouponsByStatus(Long userId, UserCoupon.CouponStatus status) {
        List<UserCoupon> coupons = userCouponRepository.findByUserIdAndStatus(userId, status.name());
        // 更新过期状态
        updateExpiredStatus(coupons);
        return coupons;
    }
    
    /**
     * 获取用户优惠券统计信息
     */
    public Map<String, Integer> getUserCouponStats(Long userId) {
        Map<String, Integer> stats = new HashMap<>();
        
        // 总优惠券数
        int total = userCouponRepository.countByUserId(userId);
        
        // 可使用数量（未使用且未过期）
        int available = userCouponRepository.countAvailableByUserId(userId);
        
        // 已使用数量
        int used = userCouponRepository.countByUserIdAndStatus(userId, "USED");
        
        // 已过期数量
        int expired = userCouponRepository.countExpiredByUserId(userId);
        
        stats.put("total", total);
        stats.put("available", available);
        stats.put("used", used);
        stats.put("expired", expired);
        
        return stats;
    }
    
    /**
     * 获取可使用的优惠券
     */
    public List<UserCoupon> getAvailableCoupons(Long userId) {
        List<UserCoupon> coupons = userCouponRepository.findByUserIdAndStatus(userId, "UNUSED");
        // 过滤掉已过期的
        coupons.removeIf(coupon -> coupon.isExpired());
        return coupons;
    }
    
    /**
     * 获取已使用的优惠券
     */
    public List<UserCoupon> getUsedCoupons(Long userId) {
        return userCouponRepository.findByUserIdAndStatus(userId, "USED");
    }
    
    /**
     * 获取已过期的优惠券
     */
    public List<UserCoupon> getExpiredCoupons(Long userId) {
        List<UserCoupon> expiredCoupons = userCouponRepository.findByUserIdAndStatus(userId, "EXPIRED");
        
        // 同时获取未使用但已过期的优惠券
        List<UserCoupon> unusedCoupons = userCouponRepository.findByUserIdAndStatus(userId, "UNUSED");
        for (UserCoupon coupon : unusedCoupons) {
            if (coupon.isExpired()) {
                // 更新状态为已过期
                coupon.setStatus(UserCoupon.CouponStatus.EXPIRED);
                userCouponRepository.update(coupon);
                expiredCoupons.add(coupon);
            }
        }
        
        return expiredCoupons;
    }
    
    /**
     * 使用优惠券
     */
    public boolean useCoupon(Long couponId, Long orderId) {
        UserCoupon coupon = userCouponRepository.findById(couponId);
        // if (coupon == null || !coupon.isAvailable()) {
        // 支付时未检查优惠券状态，导致并发漏洞（查询的时候可用，但是支付的时候已经不可用）
        if (coupon == null ) {
            return false;
        }
        
        coupon.setStatus(UserCoupon.CouponStatus.USED);
        coupon.setUseTime(LocalDateTime.now());
        coupon.setOrderId(orderId);
        
        return userCouponRepository.update(coupon) > 0;
    }
    
    /**
     * 保存优惠券
     */
    public int save(UserCoupon userCoupon) {
        return userCouponRepository.save(userCoupon);
    }
    
    /**
     * 更新优惠券
     */
    public int update(UserCoupon userCoupon) {
        return userCouponRepository.update(userCoupon);
    }
    
    /**
     * 根据ID查找优惠券
     */
    public UserCoupon findById(Long id) {
        return userCouponRepository.findById(id);
    }
    
    /**
     * 更新过期状态
     */
    private void updateExpiredStatus(List<UserCoupon> coupons) {
        for (UserCoupon coupon : coupons) {
            if (coupon.getStatus() == UserCoupon.CouponStatus.UNUSED && coupon.isExpired()) {
                coupon.setStatus(UserCoupon.CouponStatus.EXPIRED);
                userCouponRepository.update(coupon);
            }
        }
    }
}