package com.edu.controller;

import com.edu.entity.User;
import com.edu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "文件上传失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}