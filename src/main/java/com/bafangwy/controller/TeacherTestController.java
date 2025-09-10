package com.bafangwy.controller;

import com.bafangwy.entity.Course;
import com.bafangwy.entity.Teacher;
import com.bafangwy.service.CourseService;
import com.bafangwy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Controller
@RequestMapping("/edu/test")
public class TeacherTestController {
    
    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private CourseService courseService;
    
    /**
     * 测试页面 - 显示所有老师和课程信息
     */
    @GetMapping("/teachers")
    public String testTeachers(Model model) {
        try {
            // 获取所有老师
            List<Teacher> teachers = teacherService.findAll();
            model.addAttribute("teachers", teachers);
            
            // 获取所有课程（包含老师信息）
            List<Course> courses = courseService.findAll();
            model.addAttribute("courses", courses);
            
            return "test/teachers";
        } catch (Exception e) {
            model.addAttribute("error", "获取数据失败：" + e.getMessage());
            return "test/teachers";
        }
    }
}