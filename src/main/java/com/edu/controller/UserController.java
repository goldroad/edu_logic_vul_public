package com.edu.controller;

import com.edu.entity.User;
import com.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public Map<String, Object> getUserInfo(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // 漏洞：不验证当前用户是否有权限查看该用户信息
        User user = userService.getUserById(id);
        
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
     * 根据角色获取用户列表
     */
    @GetMapping("/list")
    public Map<String, Object> getUserList(@RequestParam(required = false) String role, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // 漏洞：不验证当前用户是否有管理员权限
        List<User> users;
        if (role != null) {
            users = userService.getUsersByRole(role);
        } else {
            users = userService.findAll();
        }
        
        response.put("success", true);
        response.put("users", users);
        response.put("count", users.size());
        
        return response;
    }
    
    /**
     * 修改用户信息
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return response;
        }
        
        // 漏洞：不验证用户是否有权限修改该用户信息
        User user = userService.getUserById(id);
        if (user == null) {
            response.put("success", false);
            response.put("message", "用户不存在");
            return response;
        }
        
        // 更新用户信息
        if (request.containsKey("realName")) {
            user.setRealName((String) request.get("realName"));
        }
        if (request.containsKey("email")) {
            user.setEmail((String) request.get("email"));
        }
        if (request.containsKey("phone")) {
            user.setPhone((String) request.get("phone"));
        }
        // 漏洞：允许修改角色
        if (request.containsKey("role")) {
            user.setRole(User.Role.valueOf((String) request.get("role")));
        }
        // 漏洞：允许修改余额
        if (request.containsKey("balance")) {
            user.setBalance(((Number) request.get("balance")).doubleValue());
        }
        
        userService.save(user);
        
        response.put("success", true);
        response.put("message", "更新成功");
        response.put("user", user);
        
        return response;
    }
    
    /**
     * 获取管理员信息
     */
    @GetMapping("/admin/info")
    public Map<String, Object> getAdminInfo(@RequestParam(required = false) String role, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // 漏洞：不验证当前用户是否为管理员，通过修改role参数可以查看管理员信息
        String targetRole = role != null ? role : "ADMIN";
        List<User> adminUsers = userService.getUsersByRole(targetRole);
        
        response.put("success", true);
        response.put("adminUsers", adminUsers);
        response.put("systemInfo", getSystemInfo());
        
        return response;
    }
    
    /**
     * 删除用户 - 未授权访问
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        // 漏洞：接口未鉴权，任何人都可以删除用户
        User user = userService.getUserById(id);
        if (user != null) {
            user.setEnabled(false);
            userService.save(user);
            response.put("success", true);
            response.put("message", "用户已禁用");
        } else {
            response.put("success", false);
            response.put("message", "用户不存在");
        }
        
        return response;
    }
    
    /**
     * 获取系统敏感信息 - 未授权访问
     */
    @GetMapping("/system/info")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        // 漏洞：敏感信息未授权访问
        systemInfo.put("javaVersion", System.getProperty("java.version"));
        systemInfo.put("osName", System.getProperty("os.name"));
        systemInfo.put("osVersion", System.getProperty("os.version"));
        systemInfo.put("userHome", System.getProperty("user.home"));
        systemInfo.put("userDir", System.getProperty("user.dir"));
        systemInfo.put("totalMemory", Runtime.getRuntime().totalMemory());
        systemInfo.put("freeMemory", Runtime.getRuntime().freeMemory());
        systemInfo.put("maxMemory", Runtime.getRuntime().maxMemory());
        
        return systemInfo;
    }
    
    /**
     * 重置用户密码 - 未授权访问
     */
    @PostMapping("/{id}/reset-password")
    public Map<String, Object> resetUserPassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        String newPassword = request.get("newPassword");
        
        // 漏洞：接口未鉴权，任何人都可以重置他人密码
        User user = userService.getUserById(id);
        if (user != null) {
            user.setPassword(newPassword);
            userService.save(user);
            response.put("success", true);
            response.put("message", "密码重置成功");
        } else {
            response.put("success", false);
            response.put("message", "用户不存在");
        }
        
        return response;
    }
}