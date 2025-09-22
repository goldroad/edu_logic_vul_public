package com.bafangwy.repository;

import com.bafangwy.entity.Coupon;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Mapper
public interface CouponRepository {

    /**
     * 查询所有优惠券（分页）
     */
    @Select("SELECT * FROM coupon ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "code", column = "code"),
        @Result(property = "type", column = "type"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "totalCount", column = "total_count"),
        @Result(property = "usedCount", column = "used_count"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "enabled", column = "enabled"),
        @Result(property = "createTime", column = "create_time")
    })
    List<Coupon> findAllWithPagination(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据状态查询优惠券（分页）
     */
    @Select("SELECT * FROM coupon WHERE " +
            "CASE " +
            "WHEN #{status} = 'ACTIVE' THEN enabled = 1 AND NOW() BETWEEN start_time AND end_time AND used_count < total_count " +
            "WHEN #{status} = 'EXPIRED' THEN enabled = 1 AND NOW() > end_time " +
            "WHEN #{status} = 'USED_UP' THEN enabled = 1 AND used_count >= total_count " +
            "WHEN #{status} = 'DISABLED' THEN enabled = 0 " +
            "ELSE 1=1 END " +
            "ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "code", column = "code"),
        @Result(property = "type", column = "type"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "totalCount", column = "total_count"),
        @Result(property = "usedCount", column = "used_count"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "enabled", column = "enabled"),
        @Result(property = "createTime", column = "create_time")
    })
    List<Coupon> findByStatusWithPagination(@Param("status") String status, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据关键词搜索优惠券
     */
    @Select("SELECT * FROM coupon WHERE name LIKE CONCAT('%', #{keyword}, '%') OR code LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "code", column = "code"),
        @Result(property = "type", column = "type"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "totalCount", column = "total_count"),
        @Result(property = "usedCount", column = "used_count"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "enabled", column = "enabled"),
        @Result(property = "createTime", column = "create_time")
    })
    List<Coupon> searchCoupons(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计总优惠券数
     */
    @Select("SELECT COUNT(*) FROM coupon")
    long countTotal();

    /**
     * 统计有效优惠券数
     */
    @Select("SELECT COUNT(*) FROM coupon WHERE enabled = 1 AND NOW() BETWEEN start_time AND end_time AND used_count < total_count")
    long countActive();

    /**
     * 统计已使用完的优惠券数
     */
    @Select("SELECT COUNT(*) FROM coupon WHERE enabled = 1 AND used_count >= total_count")
    long countUsedUp();

    /**
     * 统计已过期的优惠券数
     */
    @Select("SELECT COUNT(*) FROM coupon WHERE enabled = 1 AND NOW() > end_time")
    long countExpired();

    /**
     * 统计已禁用的优惠券数
     */
    @Select("SELECT COUNT(*) FROM coupon WHERE enabled = 0")
    long countDisabled();

    /**
     * 根据ID查询优惠券
     */
    @Select("SELECT * FROM coupon WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "code", column = "code"),
        @Result(property = "type", column = "type"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "totalCount", column = "total_count"),
        @Result(property = "usedCount", column = "used_count"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "enabled", column = "enabled"),
        @Result(property = "createTime", column = "create_time")
    })
    Coupon findById(@Param("id") Long id);

    /**
     * 根据优惠券代码查询
     */
    @Select("SELECT * FROM coupon WHERE code = #{code}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "code", column = "code"),
        @Result(property = "type", column = "type"),
        @Result(property = "discountValue", column = "discount_value"),
        @Result(property = "minAmount", column = "min_amount"),
        @Result(property = "totalCount", column = "total_count"),
        @Result(property = "usedCount", column = "used_count"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "enabled", column = "enabled"),
        @Result(property = "createTime", column = "create_time")
    })
    Coupon findByCode(@Param("code") String code);

    /**
     * 创建优惠券
     */
    @Insert("INSERT INTO coupon (name, code, type, discount_value, min_amount, total_count, used_count, start_time, end_time, enabled, create_time) " +
            "VALUES (#{name}, #{code}, #{type}, #{discountValue}, #{minAmount}, #{totalCount}, #{usedCount}, #{startTime}, #{endTime}, #{enabled}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Coupon coupon);

    /**
     * 更新优惠券
     */
    @Update("UPDATE coupon SET name = #{name}, code = #{code}, type = #{type}, discount_value = #{discountValue}, " +
            "min_amount = #{minAmount}, total_count = #{totalCount}, used_count=#{usedCount}, start_time = #{startTime}, end_time = #{endTime}, " +
            "enabled = #{enabled} WHERE id = #{id}")
    int update(Coupon coupon);

    /**
     * 更新优惠券状态
     */
    @Update("UPDATE coupon SET enabled = #{enabled} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("enabled") Boolean enabled);

    /**
     * 增加使用次数
     */
    @Update("UPDATE coupon SET used_count = used_count + 1 WHERE id = #{id}")
    int incrementUsedCount(@Param("id") Long id);

    /**
     * 删除优惠券
     */
    @Delete("DELETE FROM coupon WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
}