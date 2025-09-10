package com.bafangwy.repository;

import com.bafangwy.entity.UserCoupon;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Mapper
public interface UserCouponRepository {
    
    @Select("SELECT * FROM user_coupon WHERE id = #{id}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "couponId", column = "coupon_id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "couponName", column = "coupon_name"),
        @Result(property = "couponCode", column = "coupon_code"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "receiveTime", column = "receive_time"),
        @Result(property = "useTime", column = "use_time"),
        @Result(property = "expireTime", column = "expire_time"),
        @Result(property = "usageRestriction", column = "usage_restriction"),
        @Result(property = "applicableCategory", column = "applicable_category")
    })
    UserCoupon findById(Long id);
    
    @Select("SELECT * FROM user_coupon WHERE user_id = #{userId} ORDER BY receive_time DESC")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "couponId", column = "coupon_id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "couponName", column = "coupon_name"),
        @Result(property = "couponCode", column = "coupon_code"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "receiveTime", column = "receive_time"),
        @Result(property = "useTime", column = "use_time"),
        @Result(property = "expireTime", column = "expire_time"),
        @Result(property = "usageRestriction", column = "usage_restriction"),
        @Result(property = "applicableCategory", column = "applicable_category")
    })
    List<UserCoupon> findByUserId(Long userId);
    
    @Select("SELECT * FROM user_coupon WHERE user_id = #{userId} AND status = #{status} ORDER BY receive_time DESC")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "couponId", column = "coupon_id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "couponName", column = "coupon_name"),
        @Result(property = "couponCode", column = "coupon_code"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "receiveTime", column = "receive_time"),
        @Result(property = "useTime", column = "use_time"),
        @Result(property = "expireTime", column = "expire_time"),
        @Result(property = "usageRestriction", column = "usage_restriction"),
        @Result(property = "applicableCategory", column = "applicable_category")
    })
    List<UserCoupon> findByUserIdAndStatus(Long userId, String status);
    
    // 统计用户优惠券数量
    @Select("SELECT COUNT(*) FROM user_coupon WHERE user_id = #{userId}")
    int countByUserId(Long userId);
    
    @Select("SELECT COUNT(*) FROM user_coupon WHERE user_id = #{userId} AND status = #{status}")
    int countByUserIdAndStatus(Long userId, String status);
    
    // 统计可使用的优惠券（未使用且未过期）
    @Select("SELECT COUNT(*) FROM user_coupon WHERE user_id = #{userId} AND status = 'UNUSED' AND (expire_time IS NULL OR expire_time > NOW())")
    int countAvailableByUserId(Long userId);
    
    // 统计已过期的优惠券
    @Select("SELECT COUNT(*) FROM user_coupon WHERE user_id = #{userId} AND (status = 'EXPIRED' OR (status = 'UNUSED' AND expire_time IS NOT NULL AND expire_time <= NOW()))")
    int countExpiredByUserId(Long userId);
    
    @Select("SELECT * FROM user_coupon WHERE user_id = #{userId} AND coupon_id = #{couponId}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "couponId", column = "coupon_id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "couponName", column = "coupon_name"),
        @Result(property = "couponCode", column = "coupon_code"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "receiveTime", column = "receive_time"),
        @Result(property = "useTime", column = "use_time"),
        @Result(property = "expireTime", column = "expire_time"),
        @Result(property = "usageRestriction", column = "usage_restriction"),
        @Result(property = "applicableCategory", column = "applicable_category")
    })
    UserCoupon findByUserIdAndCouponId(Long userId, Long couponId);
    
    @Select("SELECT COUNT(*) > 0 FROM user_coupon WHERE user_id = #{userId} AND coupon_id = #{couponId}")
    boolean existsByUserIdAndCouponId(Long userId, Long couponId);
    
    @Select("SELECT * FROM user_coupon ORDER BY receive_time DESC")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "couponId", column = "coupon_id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "couponName", column = "coupon_name"),
        @Result(property = "couponCode", column = "coupon_code"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "receiveTime", column = "receive_time"),
        @Result(property = "useTime", column = "use_time"),
        @Result(property = "expireTime", column = "expire_time"),
        @Result(property = "usageRestriction", column = "usage_restriction"),
        @Result(property = "applicableCategory", column = "applicable_category")
    })
    List<UserCoupon> findAll();
    
    @Insert("INSERT INTO user_coupon(user_id, coupon_id, coupon_name, coupon_code, description, type, " +
            "discount_value, min_amount, status, receive_time, use_time, expire_time, usage_restriction, " +
            "applicable_category, order_id) VALUES(#{userId}, #{couponId}, #{couponName}, #{couponCode}, " +
            "#{description}, #{type}, #{discountValue}, #{minAmount}, #{status}, #{receiveTime}, #{useTime}, " +
            "#{expireTime}, #{usageRestriction}, #{applicableCategory}, #{orderId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(UserCoupon userCoupon);
    
    @Update("UPDATE user_coupon SET user_id=#{userId}, coupon_id=#{couponId}, coupon_name=#{couponName}, " +
            "coupon_code=#{couponCode}, description=#{description}, type=#{type}, discount_value=#{discountValue}, " +
            "min_amount=#{minAmount}, status=#{status}, receive_time=#{receiveTime}, use_time=#{useTime}, " +
            "expire_time=#{expireTime}, usage_restriction=#{usageRestriction}, applicable_category=#{applicableCategory}, " +
            "order_id=#{orderId} WHERE id=#{id}")
    int update(UserCoupon userCoupon);
    
    @Delete("DELETE FROM user_coupon WHERE id = #{id}")
    int deleteById(Long id);
}