package com.edu.controller;

import com.edu.entity.User;
import com.edu.entity.LoginLog;
import com.edu.service.UserService;
import com.edu.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@RestController
@RequestMapping("/admin/api")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LoginLogService loginLogService;
    
    /**
     * 获取用户列表API
     */
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        Boolean enabled = null;
        if ("active".equals(status)) {
            enabled = true;
        } else if ("inactive".equals(status)) {
            enabled = false;
        }
        
        List<User> users = userService.findUsersByRoleAndStatus(role, enabled);
        UserService.UserStatistics statistics = userService.getUserStatistics();
        
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("statistics", statistics);
        response.put("success", true);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 更新用户信息API
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Long userId,
            @RequestBody Map<String, String> updateData,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        String realName = updateData.get("realName");
        String email = updateData.get("email");
        String phone = updateData.get("phone");
        String role = updateData.get("role");
        
        boolean success = userService.updateUser(userId, realName, email, phone, role);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "用户信息更新成功");
        } else {
            response.put("success", false);
            response.put("message", "用户信息更新失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 封禁用户API
     */
    @PostMapping("/users/{userId}/ban")
    public ResponseEntity<Map<String, Object>> banUser(
            @PathVariable Long userId,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        // 不能封禁自己
        if (admin.getId().equals(userId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "不能封禁自己"));
        }
        
        boolean success = userService.banUser(userId);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "用户封禁成功");
        } else {
            response.put("success", false);
            response.put("message", "用户封禁失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 解封用户API
     */
    @PostMapping("/users/{userId}/unban")
    public ResponseEntity<Map<String, Object>> unbanUser(
            @PathVariable Long userId,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        boolean success = userService.unbanUser(userId);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "用户解封成功");
        } else {
            response.put("success", false);
            response.put("message", "用户解封失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 重置用户密码API
     */
    @PostMapping("/users/{userId}/reset-password")
    public ResponseEntity<Map<String, Object>> resetUserPassword(
            @PathVariable Long userId,
            @RequestBody Map<String, String> data,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        String newPassword = data.get("newPassword");
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "新密码不能为空"));
        }
        
        boolean success = userService.resetUserPassword(userId, newPassword);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "密码重置成功");
        } else {
            response.put("success", false);
            response.put("message", "密码重置失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取用户详情API
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<Map<String, Object>> getUserDetail(
            @PathVariable Long userId,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", user);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 更新管理员个人信息API
     */
    @PostMapping("/profile/update")
    public ResponseEntity<Map<String, Object>> updateProfile(
            @RequestBody Map<String, String> updateData,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        String realName = updateData.get("realName");
        String email = updateData.get("email");
        String phone = updateData.get("phone");
        String bio = updateData.get("bio");
        
        boolean success = userService.updateUser(admin.getId(), realName, email, phone, null);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            // 更新session中的用户信息
            User updatedUser = userService.findById(admin.getId());
            session.setAttribute("user", updatedUser);
            
            response.put("success", true);
            response.put("message", "个人信息更新成功");
            response.put("user", updatedUser);
        } else {
            response.put("success", false);
            response.put("message", "个人信息更新失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 修改管理员密码API
     */
    @PostMapping("/profile/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(
            @RequestBody Map<String, String> passwordData,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        String currentPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");
        String confirmPassword = passwordData.get("confirmPassword");
        
        Map<String, Object> response = new HashMap<>();
        
        // 验证当前密码
        if (!admin.getPassword().equals(currentPassword)) {
            response.put("success", false);
            response.put("message", "当前密码错误");
            return ResponseEntity.ok(response);
        }
        
        // 验证新密码和确认密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            response.put("success", false);
            response.put("message", "新密码和确认密码不一致");
            return ResponseEntity.ok(response);
        }
        
        // 验证新密码长度
        if (newPassword.length() < 6) {
            response.put("success", false);
            response.put("message", "新密码长度不能少于6位");
            return ResponseEntity.ok(response);
        }
        
        boolean success = userService.resetUserPassword(admin.getId(), newPassword);
        
        if (success) {
            // 更新session中的用户信息
            User updatedUser = userService.findById(admin.getId());
            session.setAttribute("user", updatedUser);
            
            response.put("success", true);
            response.put("message", "密码修改成功");
        } else {
            response.put("success", false);
            response.put("message", "密码修改失败");
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取管理员登录记录API
     */
    @GetMapping("/profile/login-logs")
    public ResponseEntity<Map<String, Object>> getLoginLogs(
            @RequestParam(defaultValue = "10") int limit,
            HttpSession session) {
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "无权限访问"));
        }
        
        List<LoginLog> loginLogs = loginLogService.getUserLoginLogs(admin.getId(), limit);
        LoginLogService.LoginLogStatistics statistics = loginLogService.getUserLoginStatistics(admin.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("loginLogs", loginLogs);
        response.put("statistics", statistics);
        
        return ResponseEntity.ok(response);
    }
}