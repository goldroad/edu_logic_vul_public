package com.bafangwy.controller;

import com.bafangwy.entity.User;
import com.bafangwy.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@RestController
@RequestMapping("/admin/api/course-files")
public class CourseFileController {
    
    @Autowired
    private FileService fileService;
    
    /**
     * 上传课程封面
     */
    @PostMapping("/upload-cover")
    public ResponseEntity<Map<String, Object>> uploadCourseCover(
            @RequestParam("file") MultipartFile file,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        User admin = (User) session.getAttribute("user");
        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            response.put("success", false);
            response.put("message", "无权限访问");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        
        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            response.put("success", false);
            response.put("message", "只能上传图片文件");
            return ResponseEntity.badRequest().body(response);
        }
        
        // 检查文件大小（限制为5MB）
        if (file.getSize() > 5 * 1024 * 1024) {
            response.put("success", false);
            response.put("message", "文件大小不能超过5MB");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            Map<String, Object> result = fileService.uploadFile(file, admin.getId());
            if ((Boolean) result.get("success")) {
                // 获取上传的文件信息
                com.bafangwy.entity.File fileEntity = (com.bafangwy.entity.File) result.get("file");
                response.put("success", true);
                response.put("message", "文件上传成功");
                response.put("filePath", fileEntity.getStoredName()); // 返回存储的文件名
                response.put("fileUrl", "/bafangwy/files/" + fileEntity.getStoredName()); // 返回访问URL
            } else {
                response.put("success", false);
                response.put("message", result.get("message"));
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "文件上传失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}