package com.bafangwy.service;

import com.bafangwy.entity.File;
import com.bafangwy.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private static final String UPLOAD_DIR = "files";
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

    /**
     * 上传文件
     */
    public Map<String, Object> uploadFile(MultipartFile file, Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 验证文件
            if (file.isEmpty()) {
                result.put("success", false);
                result.put("message", "文件不能为空");
                return result;
            }
            
            if (file.getSize() > MAX_FILE_SIZE) {
                result.put("success", false);
                result.put("message", "文件大小不能超过50MB");
                return result;
            }
            
            // 创建上传目录
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // 生成唯一文件名
            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }
            String storedName = UUID.randomUUID().toString() + extension;
            String filePath = UPLOAD_DIR + "/" + storedName;
            
            // 保存文件到磁盘
            Path targetPath = uploadPath.resolve(storedName);
            Files.copy(file.getInputStream(), targetPath);
            
            // 确定文件类型
            String fileType = getFileType(originalName, file.getContentType());
            
            // 保存文件信息到数据库
            File fileEntity = new File(
                userId,
                originalName,
                storedName,
                filePath,
                file.getSize(),
                fileType,
                file.getContentType()
            );
            
            fileRepository.save(fileEntity);
            
            result.put("success", true);
            result.put("message", "文件上传成功");
            result.put("file", fileEntity);
            
        } catch (IOException e) {
            result.put("success", false);
            result.put("message", "文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "系统错误: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取用户文件列表
     */
    public List<File> getUserFiles(Long userId) {
        return fileRepository.findByUserId(userId);
    }

    /**
     * 搜索用户文件
     */
    public List<File> searchUserFiles(Long userId, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getUserFiles(userId);
        }
        return fileRepository.searchByUserIdAndKeyword(userId, keyword.trim());
    }

    /**
     * 按文件类型筛选
     */
    public List<File> filterUserFilesByType(Long userId, String fileType) {
        if (fileType == null || fileType.trim().isEmpty() || "ALL".equals(fileType)) {
            return getUserFiles(userId);
        }
        return fileRepository.findByUserIdAndFileType(userId, fileType);
    }

    /**
     * 获取文件详情
     */
    public File getFileById(Long fileId, Long userId) {
        return fileRepository.findByIdAndUserId(fileId, userId);
    }

    /**
     * 删除文件
     */
    public Map<String, Object> deleteFile(Long fileId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 没有查询改文件是否属于当前用户
            File file = fileRepository.findById(fileId);
            if (file == null) {
                result.put("success", false);
                result.put("message", "文件不存在");
                return result;
            }
            
            // 软删除：标记为已删除
            // 直接根据文件id删除
            int updated = fileRepository.deleteById(fileId);
            if (updated > 0) {
                // 可选：删除物理文件
                try {
                    Path filePath = Paths.get(file.getFilePath());
                    if (Files.exists(filePath)) {
                        Files.delete(filePath);
                    }
                } catch (IOException e) {
                    // 物理文件删除失败不影响逻辑删除
                    System.err.println("删除物理文件失败: " + e.getMessage());
                }
                
                result.put("success", true);
                result.put("message", "文件删除成功");
            } else {
                result.put("success", false);
                result.put("message", "文件删除失败");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除文件时发生错误: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 下载文件（增加下载次数）
     */
    public File downloadFile(Long fileId, Long userId) {
        File file = fileRepository.findByIdAndUserId(fileId, userId);
        if (file != null) {
            // 增加下载次数
            fileRepository.incrementDownloadCount(fileId);
            // 重新获取更新后的文件信息
            file = fileRepository.findByIdAndUserId(fileId, userId);
        }
        return file;
    }

    /**
     * 获取用户文件统计信息
     */
    public Map<String, Object> getUserFileStats(Long userId) {
        Map<String, Object> stats = new HashMap<>();
        
        int fileCount = fileRepository.countByUserId(userId);
        long totalSize = fileRepository.getTotalSizeByUserId(userId);
        int totalDownloads = fileRepository.getTotalDownloadsByUserId(userId);
        
        stats.put("fileCount", fileCount);
        stats.put("totalSize", totalSize);
        stats.put("totalDownloads", totalDownloads);
        stats.put("formattedSize", formatFileSize(totalSize));
        
        return stats;
    }

    /**
     * 根据文件名和MIME类型确定文件类型
     */
    private String getFileType(String fileName, String mimeType) {
        if (fileName == null) return "OTHER";
        
        String extension = "";
        if (fileName.contains(".")) {
            extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        
        // 根据扩展名判断
        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
            case "webp":
                return "IMAGE";
            case "mp4":
            case "avi":
            case "mov":
            case "wmv":
            case "flv":
            case "mkv":
                return "VIDEO";
            case "mp3":
            case "wav":
            case "flac":
            case "aac":
            case "ogg":
                return "AUDIO";
            case "pdf":
                return "PDF";
            case "doc":
            case "docx":
                return "WORD";
            case "xls":
            case "xlsx":
                return "EXCEL";
            case "ppt":
            case "pptx":
                return "PPT";
            case "txt":
                return "TEXT";
            case "zip":
            case "rar":
            case "7z":
            case "tar":
            case "gz":
                return "ARCHIVE";
            default:
                return "OTHER";
        }
    }

    /**
     * 分页获取用户文件列表
     */
    public Map<String, Object> getUserFilesWithPagination(Long userId, String search, String type, int page, int size) {
        Map<String, Object> result = new HashMap<>();
        
        // 计算偏移量
        int offset = (page - 1) * size;
        
        List<File> files;
        int totalCount;
        
        if (search != null && !search.trim().isEmpty()) {
            // 搜索文件
            files = fileRepository.searchUserFilesPaginated(userId, search.trim(), offset, size);
            totalCount = fileRepository.countSearchUserFiles(userId, search.trim());
        } else if (type != null && !type.trim().isEmpty() && !"ALL".equals(type)) {
            // 按类型筛选
            files = fileRepository.getUserFilesByTypePaginated(userId, type, offset, size);
            totalCount = fileRepository.countUserFilesByType(userId, type);
        } else {
            // 获取所有文件
            files = fileRepository.getUserFilesPaginated(userId, offset, size);
            totalCount = fileRepository.countByUserId(userId);
        }
        
        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalCount / size);
        
        result.put("files", files);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("totalCount", totalCount);
        result.put("pageSize", size);
        result.put("hasNext", page < totalPages);
        result.put("hasPrev", page > 1);
        
        return result;
    }

    /**
     * 格式化文件大小
     */
    private String formatFileSize(long size) {
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        if (size < 1024 * 1024 * 1024) return String.format("%.1f MB", size / (1024.0 * 1024));
        return String.format("%.1f GB", size / (1024.0 * 1024 * 1024));
    }
}