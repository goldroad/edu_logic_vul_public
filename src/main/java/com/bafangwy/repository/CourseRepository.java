package com.bafangwy.repository;

import com.bafangwy.entity.Course;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Mapper
public interface CourseRepository {
    
    @Select("SELECT * FROM course WHERE id = #{id}")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    Course findById(Long id);
    
    @Select("SELECT * FROM course WHERE status = #{status}")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    List<Course> findByStatus(String status);
    
    @Select("SELECT * FROM course WHERE teacher_id = #{teacherId}")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    List<Course> findByTeacherId(Long teacherId);
    
    @Select("SELECT * FROM course WHERE title LIKE CONCAT('%', #{title}, '%')")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    List<Course> findByTitleContaining(String title);
    
    @Select("SELECT * FROM course")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    List<Course> findAll();
    
    @Insert("INSERT INTO course(title, description, price, original_price, cover_image, teacher_id, status, student_count, duration, create_time, update_time) " +
            "VALUES(#{title}, #{description}, #{price}, #{originalPrice}, #{coverImage}, #{teacherId}, #{status}, #{studentCount}, #{duration}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Course course);
    
    @Update("UPDATE course SET title=#{title}, description=#{description}, price=#{price}, original_price=#{originalPrice}, " +
            "cover_image=#{coverImage}, teacher_id=#{teacherId}, status=#{status}, student_count=#{studentCount}, " +
            "duration=#{duration}, update_time=#{updateTime} WHERE id=#{id}")
    int update(Course course);
    
    @Delete("DELETE FROM course WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 统计总课程数
     */
    @Select("SELECT COUNT(*) FROM course")
    long countTotalCourses();
    
    /**
     * 分页查询课程
     */
    @Select("SELECT * FROM course ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    List<Course> findWithPagination(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据状态分页查询课程
     */
    @Select("SELECT * FROM course WHERE status = #{status} ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    List<Course> findByStatusWithPagination(@Param("status") String status, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据标题搜索课程（分页）
     */
    @Select("SELECT * FROM course WHERE title LIKE CONCAT('%', #{title}, '%') ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    @Results({
        @Result(property = "teacherId", column = "teacher_id"),
        @Result(property = "teacher", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.UserRepository.findById")),
        @Result(property = "teacherInfo", column = "teacher_id", 
                one = @One(select = "com.bafangwy.repository.TeacherRepository.findById"))
    })
    List<Course> findByTitleContainingWithPagination(@Param("title") String title, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 统计各状态课程数量
     */
    @Select("SELECT COUNT(*) FROM course WHERE status = #{status}")
    long countByStatus(String status);
    
    /**
     * 获取所有课程的封面图片文件名
     */
    @Select("SELECT cover_image FROM course WHERE cover_image IS NOT NULL AND cover_image != ''")
    List<String> findAllCoverImages();
}