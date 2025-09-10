package com.edu.repository;

import com.edu.entity.User;
import org.apache.ibatis.annotations.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Mapper
public interface UserRepository {
    
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);
    
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
    
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);
    
    @Select("SELECT * FROM users WHERE phone = #{phone}")
    User findByPhone(String phone);
    
    @Select("SELECT COUNT(*) > 0 FROM users WHERE username = #{username}")
    boolean existsByUsername(String username);
    
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean existsByEmail(String email);
    
    @Select("SELECT COUNT(*) > 0 FROM users WHERE phone = #{phone}")
    boolean existsByPhone(String phone);
    
    @Select("SELECT * FROM users WHERE username = #{username} OR email = #{username} OR phone = #{username}")
    User findByUsernameOrEmailOrPhone(@Param("username") String username);
    
    @Select("SELECT * FROM users")
    List<User> findAll();
    
    @Insert("INSERT INTO users(username, password, email, phone, real_name, role, avatar, balance, create_time, update_time, last_log, enabled) " +
            "VALUES(#{username}, #{password}, #{email}, #{phone}, #{realName}, #{role}, #{avatar}, #{balance}, #{createTime}, #{updateTime}, #{lastLog}, #{enabled})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(User user);
    
    @Update("UPDATE users SET username=#{username}, password=#{password}, email=#{email}, phone=#{phone}, " +
            "real_name=#{realName}, role=#{role}, avatar=#{avatar}, balance=#{balance}, update_time=#{updateTime}, last_log=#{lastLog}, enabled=#{enabled} " +
            "WHERE id=#{id}")
    int update(User user);
    
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 统计总用户数
     */
    @Select("SELECT COUNT(*) FROM users")
    long countTotalUsers();
    
    /**
     * 更新用户最后登录时间
     */
    @Update("UPDATE users SET last_log = #{lastLog}, update_time = #{updateTime} WHERE id = #{id}")
    int updateLastLog(@Param("id") Long id, @Param("lastLog") LocalDateTime lastLog, @Param("updateTime") LocalDateTime updateTime);
}