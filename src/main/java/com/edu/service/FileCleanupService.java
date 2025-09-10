package com.edu.service;

import com.edu.repository.CourseRepository;
import com.edu.repository.FileRepository;
import com.edu.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文件清理定时任务服务
 * 用于清理未在数据库中登记的文件
 * 
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Service
public class FileCleanupService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileCleanupService.class);
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // 文件目录路径
    private static final String FILES_DIR = "files";
    private static final String AVATARS_DIR = "uploads/avatars";
    
    /**
     * 定时任务：每天凌晨2点执行文件清理
     * 也可以在项目启动后5分钟执行一次
     */
    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
    // @Scheduled(fixedDelay = 300000, initialDelay = 300000) // 项目启动5分钟后执行，然后每5分钟执行一次（用于测试）
    public void cleanupUnregisteredFiles() {
        logger.info("开始执行文件清理任务...");
        
        try {
            // 清理files目录中的未登记文件
            cleanupFilesDirectory();
            
            // 清理uploads/avatars目录中的未登记头像文件
            cleanupAvatarsDirectory();
            
            logger.info("文件清理任务执行完成");
        } catch (Exception e) {
            logger.error("文件清理任务执行失败", e);
        }
    }
    
    /**
     * 清理files目录中的未登记文件
     */
    private void cleanupFilesDirectory() {
        logger.info("开始清理files目录...");
        
        try {
            // 获取数据库中所有已登记的文件名
            Set<String> registeredFiles = new HashSet<>();
            
            // 从course表获取cover_image字段
            List<String> coverImages = courseRepository.findAllCoverImages();
            if (coverImages != null) {
                registeredFiles.addAll(coverImages);
                logger.info("从course表获取到 {} 个封面图片文件", coverImages.size());
            }
            
            // 从files表获取stored_name字段
            List<String> storedNames = fileRepository.findAllStoredNames();
            if (storedNames != null) {
                registeredFiles.addAll(storedNames);
                logger.info("从files表获取到 {} 个存储文件", storedNames.size());
            }
            
            logger.info("数据库中共登记了 {} 个files目录相关文件", registeredFiles.size());
            
            // 扫描files目录
            Path filesPath = Paths.get(FILES_DIR);
            if (!Files.exists(filesPath)) {
                logger.warn("files目录不存在: {}", filesPath.toAbsolutePath());
                return;
            }
            
            int deletedCount = 0;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(filesPath)) {
                for (Path filePath : stream) {
                    if (Files.isRegularFile(filePath)) {
                        String fileName = filePath.getFileName().toString();
                        
                        // 如果文件不在数据库登记中，则删除
                        if (!registeredFiles.contains(fileName)) {
                            try {
                                Files.delete(filePath);
                                deletedCount++;
                                logger.info("删除未登记文件: {}", filePath.toAbsolutePath());
                            } catch (IOException e) {
                                logger.error("删除文件失败: {}", filePath.toAbsolutePath(), e);
                            }
                        }
                    }
                }
            }
            
            logger.info("files目录清理完成，共删除 {} 个未登记文件", deletedCount);
            
        } catch (Exception e) {
            logger.error("清理files目录时发生错误", e);
        }
    }
    
    /**
     * 清理uploads/avatars目录中的未登记头像文件
     */
    private void cleanupAvatarsDirectory() {
        logger.info("开始清理uploads/avatars目录...");
        
        try {
            // 获取数据库中所有已登记的头像文件名
            List<String> avatars = userRepository.findAllAvatars();
            Set<String> registeredAvatars = new HashSet<>();
            
            if (avatars != null) {
                // 提取文件名（去掉路径前缀）
                for (String avatar : avatars) {
                    if (avatar != null && !avatar.isEmpty()) {
                        // 如果avatar存储的是完整路径，提取文件名
                        String fileName = avatar;
                        if (avatar.contains("/")) {
                            fileName = avatar.substring(avatar.lastIndexOf("/") + 1);
                        }
                        if (avatar.contains("\\")) {
                            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                        }
                        registeredAvatars.add(fileName);
                    }
                }
                logger.info("从users表获取到 {} 个头像文件", registeredAvatars.size());
            }
            
            // 扫描uploads/avatars目录
            Path avatarsPath = Paths.get(AVATARS_DIR);
            if (!Files.exists(avatarsPath)) {
                logger.warn("uploads/avatars目录不存在: {}", avatarsPath.toAbsolutePath());
                return;
            }
            
            int deletedCount = 0;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(avatarsPath)) {
                for (Path filePath : stream) {
                    if (Files.isRegularFile(filePath)) {
                        String fileName = filePath.getFileName().toString();
                        
                        // 如果文件不在数据库登记中，则删除
                        if (!registeredAvatars.contains(fileName)) {
                            try {
                                Files.delete(filePath);
                                deletedCount++;
                                logger.info("删除未登记头像文件: {}", filePath.toAbsolutePath());
                            } catch (IOException e) {
                                logger.error("删除头像文件失败: {}", filePath.toAbsolutePath(), e);
                            }
                        }
                    }
                }
            }
            
            logger.info("uploads/avatars目录清理完成，共删除 {} 个未登记头像文件", deletedCount);
            
        } catch (Exception e) {
            logger.error("清理uploads/avatars目录时发生错误", e);
        }
    }
    
    /**
     * 手动触发文件清理（用于测试或管理员手动清理）
     */
    public void manualCleanup() {
        logger.info("手动触发文件清理任务");
        cleanupUnregisteredFiles();
    }
}