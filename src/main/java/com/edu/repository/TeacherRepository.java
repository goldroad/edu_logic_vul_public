package com.edu.repository;

import com.edu.entity.Teacher;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TeacherRepository {
    
    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher findById(Long id);
    
    @Select("SELECT * FROM teacher WHERE enabled = 1")
    List<Teacher> findAllEnabled();
    
    @Select("SELECT * FROM teacher")
    List<Teacher> findAll();
    
    @Select("SELECT * FROM teacher WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Teacher> findByNameContaining(String name);
    
    @Insert("INSERT INTO teacher(name, id_card, phone, email, address, title, speciality, introduction, avatar, experience, enabled, create_time, update_time) " +
            "VALUES(#{name}, #{idCard}, #{phone}, #{email}, #{address}, #{title}, #{speciality}, #{introduction}, #{avatar}, #{experience}, #{enabled}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Teacher teacher);
    
    @Update("UPDATE teacher SET name=#{name}, id_card=#{idCard}, phone=#{phone}, email=#{email}, address=#{address}, " +
            "title=#{title}, speciality=#{speciality}, introduction=#{introduction}, avatar=#{avatar}, " +
            "experience=#{experience}, enabled=#{enabled}, update_time=#{updateTime} WHERE id=#{id}")
    int update(Teacher teacher);
    
    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT COUNT(*) FROM teacher")
    long countTotal();
    
    @Select("SELECT COUNT(*) FROM teacher WHERE enabled = 1")
    long countEnabled();
}