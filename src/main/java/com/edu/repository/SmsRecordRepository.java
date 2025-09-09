package com.edu.repository;

import com.edu.entity.SmsRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SmsRecordRepository {
    
    @Insert("INSERT INTO sms_records (phone, code, content, send_time, is_used, expire_time, type) " +
            "VALUES (#{phone}, #{code}, #{content}, #{sendTime}, #{isUsed}, #{expireTime}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(SmsRecord smsRecord);
    
    @Select("SELECT * FROM sms_records WHERE phone = #{phone} AND code = #{code} " +
            "AND is_used = false AND expire_time > #{now} ORDER BY send_time DESC LIMIT 1")
    SmsRecord findValidCode(@Param("phone") String phone, @Param("code") String code, @Param("now") LocalDateTime now);
    
    @Update("UPDATE sms_records SET is_used = true WHERE id = #{id}")
    void markAsUsed(@Param("id") Long id);
    
    @Select("SELECT * FROM sms_records ORDER BY send_time DESC")
    List<SmsRecord> findAll();
    
    @Select("SELECT COUNT(*) FROM sms_records WHERE DATE(send_time) = #{date}")
    int countByDate(@Param("date") LocalDate date);
    
    @Select("SELECT COUNT(*) FROM sms_records WHERE YEAR(send_time) = #{year} AND MONTH(send_time) = #{month}")
    int countByMonth(@Param("year") int year, @Param("month") int month);
    
    @Select("SELECT COUNT(*) FROM sms_records")
    int countTotal();
}