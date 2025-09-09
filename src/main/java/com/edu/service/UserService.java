/**
 * Copyright © 2023-2024 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-03-22
 */
package com.edu.service;

import com.edu.entity.User;
import com.edu.repository.UserRepository;
import com.edu.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 用户登录验证（前端已MD5加密）
     */
    public String login(String username, String password) {
        User user = userRepository.findByUsernameOrEmailOrPhone(username);
        
        if (user == null) {
            return "用户名不存在";
        }
        
        // 验证密码（前端已MD5加密，直接比较）
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            return "密码错误";
        }
        
        if (!user.getEnabled()) {
            return "账户已被禁用";
        }
        
        return "登录成功";
    }
    
    /**
     * 用户注册处理（前端已MD5加密）
     */
    public User registerWithoutValidation(String username, String password, String email, String phone) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 前端已MD5加密，直接存储
        user.setEmail(email != null && !email.trim().isEmpty() ? email : null);
        user.setPhone(phone != null && !phone.trim().isEmpty() ? phone : null);
        user.setRole(User.Role.STUDENT);
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }
    
    /**
     * 忘记密码功能（前端已MD5加密）
     */
    public String resetPassword(String username, String newPassword) {
        User user = userRepository.findByUsernameOrEmailOrPhone(username);
        if (user != null) {
            user.setPassword(newPassword); // 前端已MD5加密，直接存储
            user.setUpdateTime(LocalDateTime.now());
            userRepository.update(user);
            return "密码重置成功";
        }
        return "用户不存在";
    }
    
    /**
     * 修改用户密码（前端已MD5加密）
     */
    public String changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return "用户不存在";
        }
        
        // 验证当前密码
        if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
            return "当前密码错误";
        }
        
        // 设置新密码
        user.setPassword(newPassword); // 前端已MD5加密，直接存储
        user.setUpdateTime(LocalDateTime.now());
        userRepository.update(user);
        
        return "密码修改成功";
    }
    
    /**
     * 创建新用户
     */
    public User createUser(String username, String password, String realName, String email, String phone, String role) {
        User user = new User();
        user.setUsername(username);
        
        // 对明文密码进行MD5加密
        user.setPassword(PasswordUtil.hashPassword(password));
        
        user.setRealName(realName != null && !realName.trim().isEmpty() ? realName : null);
        user.setEmail(email != null && !email.trim().isEmpty() ? email : null);
        user.setPhone(phone != null && !phone.trim().isEmpty() ? phone : null);
        
        // 设置角色
        try {
            user.setRole(User.Role.valueOf(role));
        } catch (IllegalArgumentException e) {
            user.setRole(User.Role.STUDENT); // 默认为学生
        }
        
        user.setEnabled(true);
        user.setBalance(0.0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        userRepository.save(user);
        return user;
    }
    
    /**
     * 重置用户密码
     */
    public boolean resetUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId);
        if (user != null) {
            // 对明文密码进行MD5加密
            user.setPassword(PasswordUtil.hashPassword(newPassword));
            user.setUpdateTime(LocalDateTime.now());
            userRepository.update(user);
            return true;
        }
        return false;
    }
    
    /**
     * 弱口令检查 - 故意设置弱口令用户
     */
    public User createWeakPasswordUser(String username) {
        // 检查用户是否已存在
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            return existingUser;
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hashPassword("123456")); // 弱口令MD5加密
        user.setEmail(username + "@example.com");
        user.setPhone("13800000099");
        user.setRealName("弱口令用户");
        user.setRole(User.Role.STUDENT);
        user.setBalance(100.0);
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }
    
    // 其他基础方法
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> getUsersByRole(String role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().name().equals(role))
                .collect(java.util.stream.Collectors.toList());
    }
    
    public User save(User user) {
        user.setUpdateTime(LocalDateTime.now());
        if (user.getId() == null) {
            userRepository.save(user);
        } else {
            userRepository.update(user);
        }
        return user;
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User findById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findByUsernameOrEmailOrPhone(String username) {
        return userRepository.findByUsernameOrEmailOrPhone(username);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * 获取用户统计信息
     */
    public UserStatistics getUserStatistics() {
        List<User> allUsers = userRepository.findAll();
        
        long totalUsers = allUsers.size();
        long studentCount = allUsers.stream().filter(u -> u.getRole() == User.Role.STUDENT).count();
        long teacherCount = allUsers.stream().filter(u -> u.getRole() == User.Role.TEACHER).count();
        long adminCount = allUsers.stream().filter(u -> u.getRole() == User.Role.ADMIN).count();
        
        return new UserStatistics(totalUsers, studentCount, teacherCount, adminCount);
    }
    
    /**
     * 根据角色和状态筛选用户
     */
    public List<User> findUsersByRoleAndStatus(String role, Boolean enabled) {
        List<User> allUsers = userRepository.findAll();
        
        return allUsers.stream()
                .filter(user -> {
                    boolean roleMatch = role == null || role.trim().isEmpty() || role.equals("ALL") || user.getRole().name().equals(role);
                    boolean statusMatch = enabled == null || user.getEnabled().equals(enabled);
                    return roleMatch && statusMatch;
                })
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * 封禁用户
     */
    public boolean banUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setEnabled(false);
            user.setUpdateTime(LocalDateTime.now());
            userRepository.update(user);
            return true;
        }
        return false;
    }
    
    /**
     * 解封用户
     */
    public boolean unbanUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setEnabled(true);
            user.setUpdateTime(LocalDateTime.now());
            userRepository.update(user);
            return true;
        }
        return false;
    }
    
    /**
     * 更新用户信息
     */
    public boolean updateUser(Long userId, String realName, String email, String phone, String role) {
        User user = userRepository.findById(userId);
        if (user != null) {
            if (realName != null) {
                user.setRealName(realName.trim().isEmpty() ? null : realName);
            }
            if (email != null) {
                user.setEmail(email.trim().isEmpty() ? null : email);
            }
            if (phone != null) {
                user.setPhone(phone.trim().isEmpty() ? null : phone);
            }
            if (role != null && !role.trim().isEmpty()) {
                try {
                    user.setRole(User.Role.valueOf(role));
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
            user.setUpdateTime(LocalDateTime.now());
            userRepository.update(user);
            return true;
        }
        return false;
    }
    
    /**
     * 分页查询用户
     */
    public List<User> findUsersByRoleAndStatusWithPagination(String role, Boolean enabled, String search, int offset, int limit) {
        List<User> allUsers = userRepository.findAll();
        
        return allUsers.stream()
                .filter(user -> {
                    boolean roleMatch = role == null || role.trim().isEmpty() || role.equals("ALL") || user.getRole().name().equals(role);
                    boolean statusMatch = enabled == null || user.getEnabled().equals(enabled);
                    boolean searchMatch = search == null || search.trim().isEmpty() || 
                            (user.getUsername() != null && user.getUsername().toLowerCase().contains(search.toLowerCase())) ||
                            (user.getRealName() != null && user.getRealName().toLowerCase().contains(search.toLowerCase())) ||
                            (user.getEmail() != null && user.getEmail().toLowerCase().contains(search.toLowerCase()));
                    return roleMatch && statusMatch && searchMatch;
                })
                .sorted((u1, u2) -> {
                    // 按ID排序，确保分页结果稳定
                    return u1.getId().compareTo(u2.getId());
                })
                .skip(offset)
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * 统计符合条件的用户数量
     */
    public int countUsersByRoleAndStatus(String role, Boolean enabled, String search) {
        List<User> allUsers = userRepository.findAll();
        
        return (int) allUsers.stream()
                .filter(user -> {
                    boolean roleMatch = role == null || role.trim().isEmpty() || role.equals("ALL") || user.getRole().name().equals(role);
                    boolean statusMatch = enabled == null || user.getEnabled().equals(enabled);
                    boolean searchMatch = search == null || search.trim().isEmpty() || 
                            (user.getUsername() != null && user.getUsername().toLowerCase().contains(search.toLowerCase())) ||
                            (user.getRealName() != null && user.getRealName().toLowerCase().contains(search.toLowerCase())) ||
                            (user.getEmail() != null && user.getEmail().toLowerCase().contains(search.toLowerCase()));
                    return roleMatch && statusMatch && searchMatch;
                })
                .count();
    }
    
    /**
     * 用户统计信息类
     */
    public static class UserStatistics {
        private final long totalUsers;
        private final long studentCount;
        private final long teacherCount;
        private final long adminCount;
        
        public UserStatistics(long totalUsers, long studentCount, long teacherCount, long adminCount) {
            this.totalUsers = totalUsers;
            this.studentCount = studentCount;
            this.teacherCount = teacherCount;
            this.adminCount = adminCount;
        }
        
        public long getTotalUsers() { return totalUsers; }
        public long getStudentCount() { return studentCount; }
        public long getTeacherCount() { return teacherCount; }
        public long getAdminCount() { return adminCount; }
    }
}