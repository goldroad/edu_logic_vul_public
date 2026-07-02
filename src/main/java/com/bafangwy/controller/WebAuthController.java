package com.bafangwy.controller;

import com.bafangwy.entity.User;
import com.bafangwy.service.UserService;
import com.bafangwy.service.SimpleCaptchaService;
import com.bafangwy.service.LoginLogService;
import com.bafangwy.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Controller
@RequestMapping("/auth")
public class WebAuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SimpleCaptchaService simpleCaptchaService;
    
    @Autowired
    private LoginLogService loginLogService;
    
    @Autowired
    private SmsService smsService;
    
    /**
     * 用户登录处理
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       @RequestParam String captcha,
                       HttpSession session,
                       HttpServletRequest request,
                       RedirectAttributes redirectAttributes) {
        
        // 验证码验证
        if (!simpleCaptchaService.verifyCaptcha(session.getId(), captcha)) {
            // 记录登录失败日志 - 验证码错误
            User user = userService.findByUsernameOrEmailOrPhone(username);
            if (user != null) {
                loginLogService.recordLoginLog(user.getId(), user.getUsername(), 
                                             request, "FAILED", session.getId());
            }
            redirectAttributes.addFlashAttribute("error", "验证码错误");
            return "redirect:/auth/login";
        }
        
        User user = userService.findByUsernameOrEmailOrPhone(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "用户名不存在");
            return "redirect:/auth/login";
        }
        
        // 密码验证
        if (!user.getPassword().equals(password)) {
            // 记录登录失败日志 - 密码错误
            loginLogService.recordLoginLog(user.getId(), user.getUsername(), 
                                         request, "FAILED", session.getId());
            redirectAttributes.addFlashAttribute("error", "密码错误");
            return "redirect:/auth/login";
        }
        
        // 登录成功，设置会话
        session.setAttribute("user", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("userRole", user.getRole().name());
        
        // 记录登录成功日志
        loginLogService.recordLoginLog(user.getId(), user.getUsername(), 
                                     request, "SUCCESS", session.getId());
        
        // 根据用户角色跳转到不同页面
        switch (user.getRole()) {
            case ADMIN:
                return "redirect:/admin/dashboard";
            case TEACHER:
                return "redirect:/teacher/dashboard";
            case STUDENT:
            default:
                return "redirect:/student/dashboard";
        }
    }
    
    /**
     * 检查用户名和手机号是否匹配（发送短信前验证）
     */
    @PostMapping("/check-user-phone")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUserPhone(@RequestParam String username,
                                                            @RequestParam String phone) {
        Map<String, Object> response = new HashMap<>();
        
        // 验证用户名是否存在
        User user = userService.findByUsername(username);
        if (user == null) {
            response.put("success", false);
            response.put("message", "用户名不存在");
            return ResponseEntity.ok(response);
        }
        
        // 验证手机号是否匹配
        if (phone == null || phone.isEmpty() || !user.getPhone().equals(phone)) {
            response.put("success", false);
            response.put("message", "手机号与用户名不匹配");
            return ResponseEntity.ok(response);
        }
        
        response.put("success", true);
        response.put("message", "用户名和手机号匹配");
        return ResponseEntity.ok(response);
    }
    
    /**
     * 身份验证（忘记密码第一步）
     */
    @PostMapping("/verify-identity")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyIdentity(@RequestParam String username,
                                                            @RequestParam(required = false) String phone,
                                                            @RequestParam(required = false) String smsCode,
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // 验证用户名是否存在
        User user = userService.findByUsername(username);
        if (user == null) {
            response.put("success", false);
            response.put("message", "用户名不存在");
            return ResponseEntity.ok(response);
        }
        
        // 验证手机号是否匹配
        if (phone != null && !phone.isEmpty() && !user.getPhone().equals(phone)) {
            response.put("success", false);
            response.put("message", "手机号与用户名不匹配");
            return ResponseEntity.ok(response);
        }
        
        // 验证短信验证码
        if (smsCode == null || smsCode.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "请输入短信验证码");
            return ResponseEntity.ok(response);
        }
        
        // 验证短信验证码
        if (!smsService.verifyCode(phone, smsCode)) {
            response.put("success", false);
            response.put("message", "短信验证码错误或已过期");
            return ResponseEntity.ok(response);
        }
        
        // 身份验证成功，将用户名存储到会话中
        session.setAttribute("verifiedUsername", username);
        
        response.put("success", true);
        response.put("message", "身份验证成功");
        return ResponseEntity.ok(response);
    }
    
    /**
     * 重置密码（忘记密码第二步）
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                               @RequestParam String newPassword,
                               @RequestParam String confirmNewPassword,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("error", "两次输入的密码不一致");
            return "redirect:/auth/forgot-password";
        }
        
        // 修复：验证会话中的用户名，防止抓包修改
/*        String verifiedUsername = (String) session.getAttribute("verifiedUsername");
        if (verifiedUsername == null || !verifiedUsername.equals(username)) {
            redirectAttributes.addFlashAttribute("error", "身份验证已过期，请重新验证");
            return "redirect:/auth/forgot-password";
        }*/
        
        User user = userService.findByUsername(username);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "用户不存在");
            return "redirect:/auth/forgot-password";
        }
        user.setPassword(newPassword);
        userService.save(user);
        
        // 清除会话中的验证信息
        session.removeAttribute("verifiedUsername");
        
        redirectAttributes.addFlashAttribute("success", "密码重置成功，请使用新密码登录");
        return "redirect:/auth/login";
    }
    
    /**
     * 用户注册处理
     */
    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String confirmPassword,
                          @RequestParam String email,
                          @RequestParam String phone,
                          @RequestParam String realName,
                          @RequestParam(defaultValue = "STUDENT") String role,
                          RedirectAttributes redirectAttributes) {
        
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "两次输入的密码不一致");
            return "redirect:/auth/register";
        }
        
        // 检查用户名是否已存在
        if (userService.findByUsername(username) != null) {
            redirectAttributes.addFlashAttribute("error", "用户名已存在");
            return "redirect:/auth/register";
        }
        
        User user = userService.registerWithoutValidation(username, password, email, phone);
        if (user != null) {
            try {
                user.setRole(User.Role.valueOf(role.toUpperCase()));
                user.setRealName(realName);
                userService.save(user);
                
                redirectAttributes.addFlashAttribute("success", "注册成功，请登录");
            } catch (IllegalArgumentException e) {
                user.setRole(User.Role.STUDENT);
                user.setRealName(realName);
                userService.save(user);
                redirectAttributes.addFlashAttribute("success", "注册成功，角色设置为学生，请登录");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "注册失败");
            return "redirect:/auth/register";
        }
        
        return "redirect:/auth/login";
    }
    
    /**
     * 用户退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "已成功退出登录");
        return "redirect:/auth/login";
    }
}