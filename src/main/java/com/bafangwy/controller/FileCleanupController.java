package com.bafangwy.controller;

import com.bafangwy.service.FileCleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件清理控制器
 * 提供手动触发文件清理的接口
 * 
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@RestController
@RequestMapping("/api/admin/file-cleanup")
public class FileCleanupController {
    
    @Autowired
    private FileCleanupService fileCleanupService;
    
    /**
     * 手动触发文件清理任务
     * 仅管理员可访问
     */
    @RequestMapping("/manual")
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
}