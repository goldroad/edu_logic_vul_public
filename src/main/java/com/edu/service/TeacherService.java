package com.edu.service;

import com.edu.entity.Teacher;
import com.edu.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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