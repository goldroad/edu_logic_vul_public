package com.bafangwy.service;

import com.bafangwy.entity.Coupon;
import com.bafangwy.entity.UserCoupon;
import com.bafangwy.dto.CouponUserStats;
import com.bafangwy.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;
    
    @Autowired
    private UserCouponService userCouponService;

    /**
     * 分页查询优惠券
     */
    public CouponPageResult getCouponsWithPagination(int page, int size, String status, String keyword) {
        int offset = (page - 1) * size;
        List<Coupon> coupons;
        long total;

        if (keyword != null && !keyword.trim().isEmpty()) {
            coupons = couponRepository.searchCoupons(keyword.trim(), offset, size);
            // 简化处理，实际应该有专门的搜索计数方法
            total = couponRepository.countTotal();
        } else if (status != null && !status.equals("ALL")) {
            coupons = couponRepository.findByStatusWithPagination(status, offset, size);
            total = getCountByStatus(status);
        } else {
            coupons = couponRepository.findAllWithPagination(offset, size);
            total = couponRepository.countTotal();
        }

        // 计算每个优惠券的状态
        for (Coupon coupon : coupons) {
            coupon.setStatus(calculateCouponStatus(coupon));
        }

        return new CouponPageResult(coupons, total, page, size);
    }

    /**
     * 获取优惠券统计信息
     */
    public CouponStatistics getCouponStatistics() {
        long total = couponRepository.countTotal();
        long active = couponRepository.countActive();
        long usedUp = couponRepository.countUsedUp();
        long expired = couponRepository.countExpired();
        long disabled = couponRepository.countDisabled();

        return new CouponStatistics(total, active, usedUp, expired, disabled);
    }

    /**
     * 根据ID获取优惠券
     */
    public Coupon getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id);
        if (coupon != null) {
            coupon.setStatus(calculateCouponStatus(coupon));
        }
        return coupon;
    }

    /**
     * 创建优惠券
     */
    public Coupon createCoupon(String name, String code, Coupon.CouponType type, 
                              java.math.BigDecimal discountValue, java.math.BigDecimal minAmount,
                              Integer totalCount, LocalDateTime startTime, LocalDateTime endTime) {
        // 检查优惠券代码是否已存在
        if (couponRepository.findByCode(code) != null) {
            throw new RuntimeException("优惠券代码已存在");
        }

        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setCode(code);
        coupon.setType(type);
        coupon.setDiscountValue(discountValue);
        coupon.setMinAmount(minAmount);
        coupon.setTotalCount(totalCount);
        coupon.setUsedCount(0);
        coupon.setStartTime(startTime);
        coupon.setEndTime(endTime);
        coupon.setEnabled(true);
        coupon.setCreateTime(LocalDateTime.now());

        couponRepository.insert(coupon);
        coupon.setStatus(calculateCouponStatus(coupon));
        return coupon;
    }

    /**
     * 更新优惠券
     */
    public Coupon updateCoupon(Long id, String name, String code, Coupon.CouponType type,
                              java.math.BigDecimal discountValue, java.math.BigDecimal minAmount,
                              Integer totalCount, LocalDateTime startTime, LocalDateTime endTime) {
        Coupon existingCoupon = couponRepository.findById(id);
        if (existingCoupon == null) {
            throw new RuntimeException("优惠券不存在");
        }

        // 如果修改了代码，检查新代码是否已存在
        if (!existingCoupon.getCode().equals(code)) {
            Coupon codeCheck = couponRepository.findByCode(code);
            if (codeCheck != null && !codeCheck.getId().equals(id)) {
                throw new RuntimeException("优惠券代码已存在");
            }
        }

        existingCoupon.setName(name);
        existingCoupon.setCode(code);
        existingCoupon.setType(type);
        existingCoupon.setDiscountValue(discountValue);
        existingCoupon.setMinAmount(minAmount);
        existingCoupon.setTotalCount(totalCount);
        existingCoupon.setStartTime(startTime);
        existingCoupon.setEndTime(endTime);

        couponRepository.update(existingCoupon);
        existingCoupon.setStatus(calculateCouponStatus(existingCoupon));
        return existingCoupon;
    }

    /**
     * 更新优惠券状态（启用/禁用）
     */
    public void updateCouponStatus(Long id, Boolean enabled) {
        couponRepository.updateStatus(id, enabled);
    }

    /**
     * 删除优惠券
     */
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    /**
     * 生成随机优惠券代码
     */
    public String generateCouponCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 8; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        // 确保代码唯一
        if (couponRepository.findByCode(code.toString()) != null) {
            return generateCouponCode(); // 递归生成新代码
        }
        
        return code.toString();
    }

    /**
     * 计算优惠券状态
     */
    private String calculateCouponStatus(Coupon coupon) {
        if (!coupon.getEnabled()) {
            return "DISABLED";
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(coupon.getEndTime())) {
            return "EXPIRED";
        }

        // 已用数量大于等于可用数量，显示已领完
        if (coupon.getUsedCount() >= coupon.getTotalCount()) {
            return "USED_UP";
        }
        
        if (now.isBefore(coupon.getStartTime())) {
            return "NOT_STARTED";
        }
        
        return "ACTIVE";
    }

    /**
     * 兑换优惠券
     */
    public UserCoupon exchangeCoupon(String code, Long userId) {
        // 根据兑换码查找优惠券
        Coupon coupon = couponRepository.findByCode(code);
        if (coupon == null) {
            throw new RuntimeException("兑换码不存在");
        }
        
        // 检查优惠券状态
        String status = calculateCouponStatus(coupon);
        if (!"ACTIVE".equals(status)) {
            switch (status) {
                case "DISABLED":
                    throw new RuntimeException("优惠券已被禁用");
                case "EXPIRED":
                    throw new RuntimeException("优惠券已过期");
                case "USED_UP":
                    throw new RuntimeException("优惠券已被兑换完");
                case "NOT_STARTED":
                    throw new RuntimeException("优惠券尚未开始");
                default:
                    throw new RuntimeException("优惠券不可用");
            }
        }
        
        // 检查用户是否已经兑换过此优惠券
        // 这里可以根据业务需求决定是否允许重复兑换
        
        // 创建用户优惠券记录
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(coupon.getId());
        userCoupon.setCouponName(coupon.getName());
        userCoupon.setCouponCode(coupon.getCode());
        userCoupon.setType(UserCoupon.CouponType.valueOf(coupon.getType().name()));
        userCoupon.setDiscountValue(coupon.getDiscountValue());
        userCoupon.setMinAmount(coupon.getMinAmount());
        userCoupon.setStatus(UserCoupon.CouponStatus.UNUSED);
        userCoupon.setReceiveTime(LocalDateTime.now());
        userCoupon.setExpireTime(coupon.getEndTime());
        
        // 保存用户优惠券
        userCouponService.save(userCoupon);
        
        // 更新优惠券使用数量
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.update(coupon);
        
        return userCoupon;
    }

    /**
     * 根据状态获取数量
     */
    private long getCountByStatus(String status) {
        switch (status) {
            case "ACTIVE":
                return couponRepository.countActive();
            case "USED_UP":
                return couponRepository.countUsedUp();
            case "EXPIRED":
                return couponRepository.countExpired();
            case "DISABLED":
                return couponRepository.countDisabled();
            default:
                return couponRepository.countTotal();
        }
    }

    /**
     * 优惠券分页结果类
     */
    public static class CouponPageResult {
        private List<Coupon> coupons;
        private long total;
        private int currentPage;
        private int pageSize;
        private int totalPages;

        public CouponPageResult(List<Coupon> coupons, long total, int currentPage, int pageSize) {
            this.coupons = coupons;
            this.total = total;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalPages = (int) Math.ceil((double) total / pageSize);
        }

        // Getters
        public List<Coupon> getCoupons() { return coupons; }
        public long getTotal() { return total; }
        public int getCurrentPage() { return currentPage; }
        public int getPageSize() { return pageSize; }
        public int getTotalPages() { return totalPages; }
    }

    /**
     * 优惠券统计信息类
     */
    public static class CouponStatistics {
        private long total;
        private long active;
        private long usedUp;
        private long expired;
        private long disabled;

        public CouponStatistics(long total, long active, long usedUp, long expired, long disabled) {
            this.total = total;
            this.active = active;
            this.usedUp = usedUp;
            this.expired = expired;
            this.disabled = disabled;
        }

        // Getters
        public long getTotal() { return total; }
        public long getActive() { return active; }
        public long getUsedUp() { return usedUp; }
        public long getExpired() { return expired; }
        public long getDisabled() { return disabled; }
    }

    /**
     * 优惠券统计详情结果类
     */
    public static class CouponStatsResult {
        private Coupon coupon;
        private List<CouponUserStats> userStats;
        private CouponStatsSummary summary;
        private long total;
        private int currentPage;
        private int pageSize;
        private int totalPages;

        public CouponStatsResult(Coupon coupon, List<CouponUserStats> userStats, 
                               CouponStatsSummary summary, long total, int currentPage, int pageSize) {
            this.coupon = coupon;
            this.userStats = userStats;
            this.summary = summary;
            this.total = total;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalPages = (int) Math.ceil((double) total / pageSize);
        }

        // Getters
        public Coupon getCoupon() { return coupon; }
        public List<CouponUserStats> getUserStats() { return userStats; }
        public CouponStatsSummary getSummary() { return summary; }
        public long getTotal() { return total; }
        public int getCurrentPage() { return currentPage; }
        public int getPageSize() { return pageSize; }
        public int getTotalPages() { return totalPages; }
    }

    /**
     * 优惠券统计汇总信息类
     */
    public static class CouponStatsSummary {
        private long totalReceived;
        private long totalUsed;
        private long totalUnused;

        public CouponStatsSummary(long totalReceived, long totalUsed, long totalUnused) {
            this.totalReceived = totalReceived;
            this.totalUsed = totalUsed;
            this.totalUnused = totalUnused;
        }

        // Getters
        public long getTotalReceived() { return totalReceived; }
        public long getTotalUsed() { return totalUsed; }
        public long getTotalUnused() { return totalUnused; }
    }

    /**
     * 获取优惠券统计详情
     */
    public CouponStatsResult getCouponStats(Long couponId, int page, int size, String type) {
        // 获取优惠券信息
        Coupon coupon = getCouponById(couponId);
        if (coupon == null) {
            throw new RuntimeException("优惠券不存在");
        }

        // 获取用户统计数据
        int offset = (page - 1) * size;
        List<CouponUserStats> userStats = userCouponService.getCouponUserStats(couponId, type, offset, size);
        
        // 获取总数
        long total = userCouponService.getCouponUserStatsCount(couponId, type);
        
        // 获取汇总信息
        CouponStatsSummary summary = userCouponService.getCouponStatsSummary(couponId);

        return new CouponStatsResult(coupon, userStats, summary, total, page, size);
    }
}