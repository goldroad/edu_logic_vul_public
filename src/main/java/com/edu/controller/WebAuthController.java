package com.edu.controller;

import com.edu.entity.User;
import com.edu.service.UserService;
import com.edu.service.SimpleCaptchaService;
import com.edu.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class WebAuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SimpleCaptchaService simpleCaptchaService;
    
    @Autowired
    private LoginLogService loginLogService;
    
    /**
     * 用户登录处理 - 包含用户名枚举漏洞
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       @RequestParam String captcha,
                       HttpSession session,
                       HttpServletRequest request,
                       RedirectAttributes redirectAttributes) {
        
        // 验证码验证（包含多个漏洞）
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
        
        // 用户名枚举漏洞
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
     * 身份验证（忘记密码第一步）
     */
    @PostMapping("/verify-identity")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyIdentity(@RequestParam String username,
                                                            @RequestParam(required = false) String email,
                                                            @RequestParam(required = false) String phone,
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // 修复：验证用户名和邮箱是否匹配
        User user = userService.findByUsername(username);
        if (user == null) {
            response.put("success", false);
            response.put("message", "用户名不存在");
            return ResponseEntity.ok(response);
        }
        
        // 验证邮箱是否匹配
        if (email != null && !email.isEmpty() && !user.getEmail().equals(email)) {
            response.put("success", false);
            response.put("message", "邮箱与用户名不匹配");
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
     * 用户注册处理 - 任意用户注册漏洞
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
        
        // 漏洞：不验证邮箱和手机号是否已被其他用户使用
        User user = userService.registerWithoutValidation(username, password, email, phone);
        if (user != null) {
            // 漏洞：允许用户自己选择角色，包括管理员角色
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