package com.bafangwy.controller;

import com.bafangwy.config.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@RestController
@RequestMapping("/api/system")
public class SystemController {
    
    @Autowired
    private DataInitializer dataInitializer;
    
    /**
     * 重置数据到初始状态
     */
    @PostMapping("/reset-data")
    public Map<String, Object> resetData() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            dataInitializer.resetToInitialState();
            response.put("success", true);
            response.put("message", "数据重置成功");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "数据重置失败: " + e.getMessage());
        }
        
        return response;
    }
}