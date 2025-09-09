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
@RequestMapping("/api/profile")
public class ProfileController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    public Map<String, Object> changePassword(@RequestBody Map<String, String> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return response;
        }
        
        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");
        
        // 验证输入
        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "请输入当前密码");
            return response;
        }
        
        if (newPassword == null || newPassword.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "请输入新密码");
            return response;
        }
        
        if (newPassword.length() < 6) {
            response.put("success", false);
            response.put("message", "新密码长度不能少于6位");
            return response;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            response.put("success", false);
            response.put("message", "新密码和确认密码不一致");
            return response;
        }
        
        // 修改密码（前端已MD5加密）
        String result = userService.changePassword(currentUser.getId(), currentPassword, newPassword);
        
        if ("密码修改成功".equals(result)) {
            response.put("success", true);
            response.put("message", result);
        } else {
            response.put("success", false);
            response.put("message", result);
        }
        
        return response;
    }
    
    /**
     * 更新个人信息
     */
    @PostMapping("/update")
    public Map<String, Object> updateProfile(@RequestBody Map<String, String> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return response;
        }
        
        String realName = request.get("realName");
        String email = request.get("email");
        String phone = request.get("phone");
        
        // 更新用户信息
        boolean success = userService.updateUser(currentUser.getId(), realName, email, phone, null);
        
        if (success) {
            // 更新session中的用户信息
            User updatedUser = userService.findById(currentUser.getId());
            session.setAttribute("user", updatedUser);
            
            response.put("success", true);
            response.put("message", "个人信息更新成功");
            response.put("user", updatedUser);
        } else {
            response.put("success", false);
            response.put("message", "更新失败");
        }
        
        return response;
    }
}