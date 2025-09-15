package com.bafangwy.controller;

import com.bafangwy.entity.Coupon;
import com.bafangwy.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@RestController
@RequestMapping("/admin/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 获取优惠券列表API
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCoupons(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword) {
        
        try {
            CouponService.CouponPageResult result = couponService.getCouponsWithPagination(page, size, status, keyword);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("coupons", result.getCoupons());
            response.put("total", result.getTotal());
            response.put("currentPage", result.getCurrentPage());
            response.put("totalPages", result.getTotalPages());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取优惠券列表失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 获取优惠券统计信息API
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getCouponStatistics() {
        try {
            CouponService.CouponStatistics statistics = couponService.getCouponStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("statistics", statistics);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取统计信息失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 获取优惠券详情API
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<Map<String, Object>> getCouponDetail(@PathVariable Long couponId) {
        try {
            Coupon coupon = couponService.getCouponById(couponId);
            
            Map<String, Object> response = new HashMap<>();
            if (coupon != null) {
                response.put("success", true);
                response.put("coupon", coupon);
            } else {
                response.put("success", false);
                response.put("message", "优惠券不存在");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取优惠券详情失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 创建优惠券API
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCoupon(@RequestBody Map<String, Object> requestData) {
        try {
            String name = (String) requestData.get("name");
            String code = (String) requestData.get("code");
            String typeStr = (String) requestData.get("type");
            BigDecimal discountValue = new BigDecimal(requestData.get("discountValue").toString());
            BigDecimal minAmount = new BigDecimal(requestData.get("minAmount").toString());
            Integer totalCount = Integer.valueOf(requestData.get("totalCount").toString());
            String startTimeStr = (String) requestData.get("startTime");
            String endTimeStr = (String) requestData.get("endTime");

            // 如果没有提供代码，自动生成
            if (code == null || code.trim().isEmpty()) {
                code = couponService.generateCouponCode();
            }

            Coupon.CouponType type = Coupon.CouponType.valueOf(typeStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

            Coupon coupon = couponService.createCoupon(name, code, type, discountValue, minAmount, totalCount, startTime, endTime);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "优惠券创建成功");
            response.put("coupon", coupon);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "创建优惠券失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 更新优惠券API
     */
    @PutMapping("/{couponId}")
    public ResponseEntity<Map<String, Object>> updateCoupon(@PathVariable Long couponId, @RequestBody Map<String, Object> requestData) {
        try {
            String name = (String) requestData.get("name");
            String code = (String) requestData.get("code");
            String typeStr = (String) requestData.get("type");
            BigDecimal discountValue = new BigDecimal(requestData.get("discountValue").toString());
            BigDecimal minAmount = new BigDecimal(requestData.get("minAmount").toString());
            Integer totalCount = Integer.valueOf(requestData.get("totalCount").toString());
            String startTimeStr = (String) requestData.get("startTime");
            String endTimeStr = (String) requestData.get("endTime");

            Coupon.CouponType type = Coupon.CouponType.valueOf(typeStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

            Coupon coupon = couponService.updateCoupon(couponId, name, code, type, discountValue, minAmount, totalCount, startTime, endTime);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "优惠券更新成功");
            response.put("coupon", coupon);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "更新优惠券失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 更新优惠券状态API
     */
    @PutMapping("/{couponId}/status")
    public ResponseEntity<Map<String, Object>> updateCouponStatus(@PathVariable Long couponId, @RequestBody Map<String, Object> requestData) {
        try {
            Boolean enabled = (Boolean) requestData.get("enabled");
            couponService.updateCouponStatus(couponId, enabled);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", enabled ? "优惠券已启用" : "优惠券已禁用");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "更新优惠券状态失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 删除优惠券API
     */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Map<String, Object>> deleteCoupon(@PathVariable Long couponId) {
        try {
            couponService.deleteCoupon(couponId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "优惠券删除成功");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "删除优惠券失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 生成优惠券代码API
     */
    @GetMapping("/generate-code")
    public ResponseEntity<Map<String, Object>> generateCouponCode() {
        try {
            String code = couponService.generateCouponCode();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("code", code);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "生成优惠券代码失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}