package com.edu.controller;

import com.edu.entity.Course;
import com.edu.entity.User;
import com.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@RestController
@RequestMapping("/admin/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    /**
     * 获取课程列表API（分页）
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCourses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        CourseService.CoursePageResult pageResult = courseService.getCoursesByPage(page, size, status, search);
        CourseService.CourseStatistics statistics = courseService.getCourseStatistics();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("courses", pageResult.getCourses());
        response.put("totalCount", pageResult.getTotalCount());
        response.put("totalPages", pageResult.getTotalPages());
        response.put("currentPage", pageResult.getCurrentPage());
        response.put("pageSize", pageResult.getPageSize());
        response.put("hasPrevious", pageResult.isHasPrevious());
        response.put("hasNext", pageResult.isHasNext());
        response.put("statistics", statistics);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 更新课程信息API
     */
    @PutMapping("/{courseId}")
    public ResponseEntity<Map<String, Object>> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Map<String, Object> updateData,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        String title = (String) updateData.get("title");
        String description = (String) updateData.get("description");
        String coverImage = (String) updateData.get("coverImage");
        BigDecimal price = null;
        
        if (updateData.get("price") != null) {
            try {
                price = new BigDecimal(updateData.get("price").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(Map.of("error", "价格格式不正确"));
            }
        }
        
        boolean success = courseService.updateCourse(courseId, title, description, price, coverImage);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "课程信息更新成功");
        } else {
            response.put("success", false);
            response.put("message", "课程信息更新失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 更新课程状态API
     */
    @PutMapping("/{courseId}/status")
    public ResponseEntity<Map<String, Object>> updateCourseStatus(
            @PathVariable Long courseId,
            @RequestBody Map<String, String> statusData,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        String statusStr = statusData.get("status");
        Course.CourseStatus status;
        
        try {
            status = Course.CourseStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "状态参数不正确"));
        }
        
        boolean success = courseService.updateCourseStatus(courseId, status);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "课程状态更新成功");
        } else {
            response.put("success", false);
            response.put("message", "课程状态更新失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 删除课程API
     */
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Map<String, Object>> deleteCourse(
            @PathVariable Long courseId,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        boolean success = courseService.deleteCourse(courseId);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "课程删除成功");
        } else {
            response.put("success", false);
            response.put("message", "课程删除失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取课程详情API
     */
    @GetMapping("/{courseId}")
    public ResponseEntity<Map<String, Object>> getCourseDetail(
            @PathVariable Long courseId,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        Course course = courseService.findById(courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("course", course);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 创建新课程API
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCourse(
            @RequestBody Map<String, Object> courseData,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        String title = (String) courseData.get("title");
        String description = (String) courseData.get("description");
        String coverImage = (String) courseData.get("coverImage");
        BigDecimal price = null;
        
        if (courseData.get("price") != null) {
            try {
                price = new BigDecimal(courseData.get("price").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(Map.of("error", "价格格式不正确"));
            }
        }
        
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "课程标题不能为空"));
        }
        
        if (description == null || description.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "课程描述不能为空"));
        }
        
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "课程价格必须大于0"));
        }
        
        Course course = courseService.createCourse(title, description, price, coverImage, admin);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "课程创建成功");
        response.put("course", course);
        
        return ResponseEntity.ok(response);
    }
}