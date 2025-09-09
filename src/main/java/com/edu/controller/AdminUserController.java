package com.edu.controller;

import com.edu.entity.User;
import com.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getUserDetail(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != User.Role.ADMIN) {
            response.put("success", false);
            response.put("message", "权限不足");
            return response;
        }
        
        User user = userService.findById(id);
        if (user != null) {
            response.put("success", true);
            response.put("user", user);
        } else {
            response.put("success", false);
            response.put("message", "用户不存在");
        }
        
        return response;
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateUser(@PathVariable Long id, 
                                        @RequestBody Map<String, String> request,
                                        HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != User.Role.ADMIN) {
            response.put("success", false);
            response.put("message", "权限不足");
            return response;
        }
        
        String realName = request.get("realName");
        String email = request.get("email");
        String phone = request.get("phone");
        String role = request.get("role");
        
        boolean success = userService.updateUser(id, realName, email, phone, role);
        
        if (success) {
            response.put("success", true);
            response.put("message", "更新成功");
        } else {
            response.put("success", false);
            response.put("message", "更新失败");
        }
        
        return response;
    }
    
    /**
     * 封禁用户
     */
    @PostMapping("/{id}/ban")
    public Map<String, Object> banUser(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != User.Role.ADMIN) {
            response.put("success", false);
            response.put("message", "权限不足");
            return response;
        }
        
        boolean success = userService.banUser(id);
        
        if (success) {
            response.put("success", true);
            response.put("message", "用户已封禁");
        } else {
            response.put("success", false);
            response.put("message", "封禁失败");
        }
        
        return response;
    }
    
    /**
     * 解封用户
     */
    @PostMapping("/{id}/unban")
    public Map<String, Object> unbanUser(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != User.Role.ADMIN) {
            response.put("success", false);
            response.put("message", "权限不足");
            return response;
        }
        
        boolean success = userService.unbanUser(id);
        
        if (success) {
            response.put("success", true);
            response.put("message", "用户已解封");
        } else {
            response.put("success", false);
            response.put("message", "解封失败");
        }
        
        return response;
    }
    
    /**
     * 创建新用户
     */
    @PostMapping
    public Map<String, Object> createUser(@RequestBody Map<String, String> request,
                                        HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != User.Role.ADMIN) {
            response.put("success", false);
            response.put("message", "权限不足");
            return response;
        }
        
        String username = request.get("username");
        String realName = request.get("realName");
        String email = request.get("email");
        String phone = request.get("phone");
        String role = request.get("role");
        String password = request.get("password");
        
        // 验证必填字段
        if (username == null || username.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "用户名不能为空");
            return response;
        }
        
        if (password == null || password.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "密码不能为空");
            return response;
        }
        
        // 检查用户名是否已存在
        if (userService.findByUsername(username) != null) {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return response;
        }
        
        // 检查邮箱是否已存在
        if (email != null && !email.trim().isEmpty() && userService.findByEmail(email) != null) {
            response.put("success", false);
            response.put("message", "邮箱已存在");
            return response;
        }
        
        try {
            User newUser = userService.createUser(username, password, realName, email, phone, role);
            if (newUser != null) {
                response.put("success", true);
                response.put("message", "用户创建成功");
                response.put("user", newUser);
            } else {
                response.put("success", false);
                response.put("message", "创建失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "创建失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 重置用户密码
     */
    @PostMapping("/{id}/reset-password")
    public Map<String, Object> resetPassword(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != User.Role.ADMIN) {
            response.put("success", false);
            response.put("message", "权限不足");
            return response;
        }
        
        // 生成随机密码
        String newPassword = generateRandomPassword();
        boolean success = userService.resetUserPassword(id, newPassword);
        
        if (success) {
            response.put("success", true);
            response.put("message", "密码重置成功");
            response.put("newPassword", newPassword);
        } else {
            response.put("success", false);
            response.put("message", "重置失败");
        }
        
        return response;
    }
    
    /**
     * 生成随机密码
     */
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return password.toString();
    }
}