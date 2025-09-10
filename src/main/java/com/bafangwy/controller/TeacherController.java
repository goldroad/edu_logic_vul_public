package com.bafangwy.controller;

import com.bafangwy.entity.Teacher;
import com.bafangwy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@RestController
@RequestMapping("/edu/api/teachers")
public class TeacherController {
    
    @Autowired
    private TeacherService teacherService;
    
    /**
     * 获取所有启用的老师
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEnabledTeachers() {
        try {
            List<Teacher> teachers = teacherService.findAllEnabled();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("teachers", teachers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取老师列表失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    /**
     * 根据ID获取老师信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTeacherById(@PathVariable Long id) {
        try {
            Teacher teacher = teacherService.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (teacher != null) {
                response.put("success", true);
                response.put("teacher", teacher);
            } else {
                response.put("success", false);
                response.put("message", "老师不存在");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取老师信息失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    /**
     * 创建新老师
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher savedTeacher = teacherService.save(teacher);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("teacher", savedTeacher);
            response.put("message", "老师创建成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "创建老师失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    /**
     * 更新老师信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        try {
            teacher.setId(id);
            Teacher updatedTeacher = teacherService.update(teacher);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("teacher", updatedTeacher);
            response.put("message", "老师信息更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "更新老师信息失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    /**
     * 删除老师
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "老师删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "删除老师失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    /**
     * 搜索老师
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTeachers(@RequestParam String name) {
        try {
            List<Teacher> teachers = teacherService.findByNameContaining(name);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("teachers", teachers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "搜索老师失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}