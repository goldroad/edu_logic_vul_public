package com.bafangwy.service;

import com.bafangwy.entity.Teacher;
import com.bafangwy.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Service
public class TeacherService {
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    public Teacher findById(Long id) {
        return teacherRepository.findById(id);
    }
    
    public List<Teacher> findAllEnabled() {
        return teacherRepository.findAllEnabled();
    }
    
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
    
    public List<Teacher> findByNameContaining(String name) {
        return teacherRepository.findByNameContaining(name);
    }
    
    public Teacher save(Teacher teacher) {
        teacher.setCreateTime(LocalDateTime.now());
        teacher.setUpdateTime(LocalDateTime.now());
        teacherRepository.save(teacher);
        return teacher;
    }
    
    public Teacher update(Teacher teacher) {
        teacher.setUpdateTime(LocalDateTime.now());
        teacherRepository.update(teacher);
        return teacher;
    }
    
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
    
    public long countTotal() {
        return teacherRepository.countTotal();
    }
    
    public long countEnabled() {
        return teacherRepository.countEnabled();
    }
}