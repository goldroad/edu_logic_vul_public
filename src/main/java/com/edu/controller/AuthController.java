package com.edu.controller;

import com.edu.entity.User;
import com.edu.service.SimpleCaptchaService;
import com.edu.service.UserService;
import com.edu.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SimpleCaptchaService simpleCaptchaService;
    
    @Autowired
    private LoginLogService loginLogService;
    
    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request, 
                                   HttpSession session, HttpServletRequest httpRequest) {
        String username = request.get("username");
        String password = request.get("password");
        String captcha = request.get("captcha");
        
        Map<String, Object> response = new HashMap<>();
        
        // 验证码验证
        if (!simpleCaptchaService.verifyCaptcha(session.getId(), captcha)) {
            response.put("success", false);
            response.put("message", "验证码错误");
            
            // 记录登录失败日志
            User user = userService.findByUsernameOrEmailOrPhone(username);
            if (user != null) {
                loginLogService.recordLoginLog(user.getId(), user.getUsername(), 
                                             httpRequest, "FAILED", session.getId());
            }
            
            return response;
        }
        
        // 用户登录验证
        String result = userService.login(username, password);
        
        if ("登录成功".equals(result)) {
            User user = userService.findByUsernameOrEmailOrPhone(username);
            session.setAttribute("user", user);
            
            // 记录登录成功日志
            loginLogService.recordLoginLog(user.getId(), user.getUsername(), 
                                         httpRequest, "SUCCESS", session.getId());
            
            response.put("success", true);
            response.put("message", result);
            response.put("user", user);
        } else {
            // 记录登录失败日志
            User user = userService.findByUsernameOrEmailOrPhone(username);
            if (user != null) {
                loginLogService.recordLoginLog(user.getId(), user.getUsername(), 
                                             httpRequest, "FAILED", session.getId());
            }
            
            response.put("success", false);
            response.put("message", result);
        }
        
        return response;
    }
    
    /**
     * 注册接口
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");
        String phone = request.get("phone");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 用户注册处理
            User user = userService.registerWithoutValidation(username, password, email, phone);
            response.put("success", true);
            response.put("message", "注册成功");
            response.put("user", user);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "注册失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 忘记密码接口
     */
    @PostMapping("/forgot-password")
    public Map<String, Object> forgotPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("newPassword");
        
        Map<String, Object> response = new HashMap<>();
        
        // 密码重置处理
        String result = userService.resetPassword(username, newPassword);
        
        response.put("success", "密码重置成功".equals(result));
        response.put("message", result);
        
        return response;
    }
    
    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public Map<String, Object> getCaptcha(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // 获取已存在的验证码，如果不存在则生成新的
        // String code = simpleCaptchaService.getCaptchaCode(session.getId());
        // if (code == null || code.isEmpty()) {
        //     code = simpleCaptchaService.generateCaptcha(session.getId());
        // }

        String code = simpleCaptchaService.generateCaptcha(session.getId());
        
        response.put("success", true);
        response.put("sessionId", session.getId());
        response.put("hiddenCode", code); // 隐藏字段中的验证码
        
        return response;
    }
    

    

    
    /**
     * 登出
     */
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "登出成功");
        
        return response;
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/current-user")
    public Map<String, Object> getCurrentUser(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user != null) {
            response.put("success", true);
            response.put("user", user);
        } else {
            response.put("success", false);
            response.put("message", "未登录");
        }
        
        return response;
    }
}