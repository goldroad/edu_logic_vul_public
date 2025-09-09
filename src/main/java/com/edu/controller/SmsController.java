package com.edu.controller;

import com.edu.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SmsController {
    
    @Autowired
    private SmsService smsService;
    
    /**
     * 发送验证码接口 - 故意设计的漏洞：无限制调用
     */
    @PostMapping("/api/sms/send")
    @ResponseBody
    public Map<String, Object> sendVerificationCode(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String content = request.get("content");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 直接发送验证码，无任何限制
            String code = smsService.sendVerificationCode(phone);
            
            response.put("success", true);
            response.put("message", "验证码发送成功");
            response.put("code", code); // 返回验证码用于测试
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "验证码发送失败：" + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 验证验证码接口
     */
    @PostMapping("/api/sms/verify")
    @ResponseBody
    public Map<String, Object> verifyCode(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String code = request.get("code");
        
        Map<String, Object> response = new HashMap<>();
        
        boolean isValid = smsService.verifyCode(phone, code);
        
        response.put("success", isValid);
        response.put("message", isValid ? "验证码正确" : "验证码错误或已过期");
        
        return response;
    }
    
    /**
     * 查看短信发送记录页面
     */
    @GetMapping("/sms/records")
    public String smsRecords(Model model) {
        model.addAttribute("records", smsService.getAllRecords());
        model.addAttribute("todayCount", smsService.getTodayCount());
        model.addAttribute("monthCount", smsService.getThisMonthCount());
        model.addAttribute("totalCount", smsService.getTotalCount());
        
        return "sms-records";
    }
}