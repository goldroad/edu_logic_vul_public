package com.bafangwy.service;

import com.bafangwy.entity.LoginLog;
import com.bafangwy.repository.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Service
public class LoginLogService {
    
    @Autowired
    private LoginLogRepository loginLogRepository;
    
    /**
     * 记录登录日志
     */
    public void recordLoginLog(Long userId, String username, HttpServletRequest request, String status, String sessionId) {
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String operatingSystem = extractOperatingSystem(userAgent);
        String browser = extractBrowser(userAgent);
        String location = getLocationByIp(ipAddress);
        
        LoginLog loginLog = new LoginLog(userId, username, ipAddress, userAgent, 
                                       operatingSystem, browser, location, status, sessionId);
        
        loginLogRepository.save(loginLog);
    }
    
    /**
     * 获取用户的登录记录
     */
    public List<LoginLog> getUserLoginLogs(Long userId, int limit) {
        return loginLogRepository.findByUserIdOrderByLoginTimeDescWithLimit(userId, limit);
    }
    
    /**
     * 获取用户的所有登录记录
     */
    public List<LoginLog> getUserLoginLogs(Long userId) {
        return loginLogRepository.findByUserIdOrderByLoginTimeDesc(userId);
    }
    
    /**
     * 获取最近的登录记录
     */
    public List<LoginLog> getRecentLoginLogs(int limit) {
        return loginLogRepository.findAllOrderByLoginTimeDesc(limit);
    }
    
    /**
     * 获取用户登录统计
     */
    public LoginLogStatistics getUserLoginStatistics(Long userId) {
        int totalLogins = loginLogRepository.countByUserId(userId);
        int successLogins = loginLogRepository.countSuccessLoginsByUserId(userId);
        int failedLogins = loginLogRepository.countFailedLoginsByUserId(userId);
        
        return new LoginLogStatistics(totalLogins, successLogins, failedLogins);
    }
    
    /**
     * 获取客户端IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
    /**
     * 从User-Agent中提取操作系统信息
     */
    private String extractOperatingSystem(String userAgent) {
        if (userAgent == null) return "Unknown";
        
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("windows nt 10.0")) return "Windows 10";
        if (userAgent.contains("windows nt 6.3")) return "Windows 8.1";
        if (userAgent.contains("windows nt 6.2")) return "Windows 8";
        if (userAgent.contains("windows nt 6.1")) return "Windows 7";
        if (userAgent.contains("windows nt 6.0")) return "Windows Vista";
        if (userAgent.contains("windows nt 5.1")) return "Windows XP";
        if (userAgent.contains("windows")) return "Windows";
        
        if (userAgent.contains("mac os x")) return "macOS";
        if (userAgent.contains("linux")) return "Linux";
        if (userAgent.contains("android")) return "Android";
        if (userAgent.contains("iphone") || userAgent.contains("ipad")) return "iOS";
        
        return "Unknown";
    }
    
    /**
     * 从User-Agent中提取浏览器信息
     */
    private String extractBrowser(String userAgent) {
        if (userAgent == null) return "Unknown";
        
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("edg/")) return "Microsoft Edge";
        if (userAgent.contains("chrome/") && !userAgent.contains("edg/")) return "Google Chrome";
        if (userAgent.contains("firefox/")) return "Mozilla Firefox";
        if (userAgent.contains("safari/") && !userAgent.contains("chrome/")) return "Safari";
        if (userAgent.contains("opera/") || userAgent.contains("opr/")) return "Opera";
        if (userAgent.contains("msie") || userAgent.contains("trident/")) return "Internet Explorer";
        
        return "Unknown";
    }
    
    /**
     * 根据IP地址获取地理位置（简化版本）
     */
    private String getLocationByIp(String ipAddress) {
        // 这里可以集成第三方IP地址库或API来获取真实的地理位置
        // 为了演示，我们使用简化的逻辑
        if (ipAddress == null) return "未知";
        
        if (ipAddress.startsWith("192.168.") || ipAddress.startsWith("10.") || 
            ipAddress.startsWith("172.") || ipAddress.equals("127.0.0.1") || 
            ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("::1")) {
            return "本地网络";
        }
        
        // 可以在这里添加真实的IP地址解析逻辑
        return "中国";
    }
    
    /**
     * 登录日志统计信息类
     */
    public static class LoginLogStatistics {
        private final int totalLogins;
        private final int successLogins;
        private final int failedLogins;
        
        public LoginLogStatistics(int totalLogins, int successLogins, int failedLogins) {
            this.totalLogins = totalLogins;
            this.successLogins = successLogins;
            this.failedLogins = failedLogins;
        }
        
        public int getTotalLogins() { return totalLogins; }
        public int getSuccessLogins() { return successLogins; }
        public int getFailedLogins() { return failedLogins; }
        public double getSuccessRate() { 
            return totalLogins > 0 ? (double) successLogins / totalLogins * 100 : 0; 
        }
    }
}