package com.edu.repository;

import com.edu.entity.LoginLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LoginLogRepository {
    
    @Insert("INSERT INTO login_logs(user_id, username, ip_address, user_agent, operating_system, browser, location, login_time, status, session_id) " +
            "VALUES(#{userId}, #{username}, #{ipAddress}, #{userAgent}, #{operatingSystem}, #{browser}, #{location}, #{loginTime}, #{status}, #{sessionId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(LoginLog loginLog);
    
    @Select("SELECT * FROM login_logs WHERE user_id = #{userId} ORDER BY login_time DESC LIMIT #{limit}")
    List<LoginLog> findByUserIdOrderByLoginTimeDescWithLimit(@Param("userId") Long userId, @Param("limit") int limit);
    
    @Select("SELECT * FROM login_logs WHERE user_id = #{userId} ORDER BY login_time DESC")
    List<LoginLog> findByUserIdOrderByLoginTimeDesc(@Param("userId") Long userId);
    
    @Select("SELECT * FROM login_logs ORDER BY login_time DESC LIMIT #{limit}")
    List<LoginLog> findAllOrderByLoginTimeDesc(@Param("limit") int limit);
    
    @Select("SELECT COUNT(*) FROM login_logs WHERE user_id = #{userId}")
    int countByUserId(@Param("userId") Long userId);
    
    @Select("SELECT COUNT(*) FROM login_logs WHERE user_id = #{userId} AND status = 'SUCCESS'")
    int countSuccessLoginsByUserId(@Param("userId") Long userId);
    
    @Select("SELECT COUNT(*) FROM login_logs WHERE user_id = #{userId} AND status = 'FAILED'")
    int countFailedLoginsByUserId(@Param("userId") Long userId);
    
    @Delete("DELETE FROM login_logs WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
    
    @Delete("DELETE FROM login_logs WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
}