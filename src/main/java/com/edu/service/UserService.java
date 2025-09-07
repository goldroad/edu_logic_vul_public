package com.edu.service;

import com.edu.entity.User;
import com.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 用户登录验证
     */
    public String login(String username, String password) {
        User user = userRepository.findByUsernameOrEmailOrPhone(username);
        
        if (user == null) {
            return "用户名不存在";
        }
        
        if (!user.getPassword().equals(password)) {
            return "密码错误";
        }
        
        if (!user.getEnabled()) {
            return "账户已被禁用";
        }
        
        return "登录成功";
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
        user.setPassword("123456"); // 弱口令
        user.setEmail(username + "@example.com");
        user.setPhone("13800000099");
        user.setRealName("弱口令用户");
        user.setRole(User.Role.STUDENT);
        user.setBalance(100.0);
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }
    
    /**
     * 用户注册处理
     */
    public User registerWithoutValidation(String username, String password, String email, String phone) {
        // 用户注册处理
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(User.Role.STUDENT);
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }
    
    /**
     * 忘记密码功能
     */
    public String resetPassword(String username, String newPassword) {
        // 密码重置处理
        User user = userRepository.findByUsernameOrEmailOrPhone(username);
        if (user != null) {
            user.setPassword(newPassword);
            user.setUpdateTime(LocalDateTime.now());
            userRepository.update(user);
            return "密码重置成功";
        }
        return "用户不存在";
    }
    
    /**
     * 根据ID获取用户信息
     */
    public User getUserById(Long id) {
        // 获取用户信息
        return userRepository.findById(id);
    }
    
    /**
     * 根据角色获取用户列表
     */
    public List<User> getUsersByRole(String role) {
        // 获取用户列表
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
                    boolean roleMatch = role == null || role.equals("ALL") || user.getRole().name().equals(role);
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
            if (realName != null && !realName.trim().isEmpty()) {
                user.setRealName(realName);
            }
            if (email != null && !email.trim().isEmpty()) {
                user.setEmail(email);
            }
            if (phone != null && !phone.trim().isEmpty()) {
                user.setPhone(phone);
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
     * 重置用户密码
     */
    public boolean resetUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setPassword(newPassword);
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
                    boolean roleMatch = role == null || role.equals("ALL") || user.getRole().name().equals(role);
                    boolean statusMatch = enabled == null || user.getEnabled().equals(enabled);
                    boolean searchMatch = search == null || search.trim().isEmpty() || 
                            (user.getUsername() != null && user.getUsername().toLowerCase().contains(search.toLowerCase())) ||
                            (user.getRealName() != null && user.getRealName().toLowerCase().contains(search.toLowerCase())) ||
                            (user.getEmail() != null && user.getEmail().toLowerCase().contains(search.toLowerCase()));
                    return roleMatch && statusMatch && searchMatch;
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
                    boolean roleMatch = role == null || role.equals("ALL") || user.getRole().name().equals(role);
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