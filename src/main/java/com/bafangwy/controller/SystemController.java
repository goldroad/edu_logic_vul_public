package com.bafangwy.controller;

import com.bafangwy.config.DataInitializer;
import com.bafangwy.service.FileCleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@RestController
@RequestMapping("/api/system")
public class SystemController {
    
    @Autowired
    private DataInitializer dataInitializer;
    
    @Autowired
    private FileCleanupService fileCleanupService;
    
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
    
    /**
     * 手动触发文件清理任务
     * 仅管理员可访问
     */
    @RequestMapping("/file-cleanup/manual")
    public ResponseEntity<Map<String, Object>> manualCleanup() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            fileCleanupService.manualCleanup();
            response.put("success", true);
            response.put("message", "文件清理任务已成功执行，请查看日志了解详细信息");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "文件清理任务执行失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

        
    /**
     * 获取系统信息
     */
    @GetMapping("/info")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();
        
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
    
}