package com.bafangwy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 * 描述：密码工具类
 * 提供MD5哈希功能（简化版，不使用盐值）
 */
public class PasswordUtil {
    
    /**
     * 对密码进行MD5哈希
     * @param password 密码
     * @return MD5哈希值
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }
    
    /**
     * 验证密码
     * @param inputPassword 输入的密码（前端已MD5加密）
     * @param storedPassword 存储的MD5密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        return inputPassword != null && inputPassword.equals(storedPassword);
    }
}