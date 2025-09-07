package com.edu.service;

import com.edu.repository.UserRepository;
import com.edu.repository.CourseRepository;
import com.edu.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     * 获取管理员仪表盘统计数据
     */
    public DashboardStatistics getDashboardStatistics() {
        long totalUsers = userRepository.countTotalUsers();
        long totalCourses = courseRepository.countTotalCourses();
        long totalOrders = orderRepository.countTotalOrders();
        double totalRevenue = orderRepository.getTotalRevenue();
        
        return new DashboardStatistics(totalUsers, totalCourses, totalOrders, totalRevenue);
    }
    
    /**
     * 仪表盘统计数据类
     */
    public static class DashboardStatistics {
        private final long totalUsers;
        private final long totalCourses;
        private final long totalOrders;
        private final double totalRevenue;
        
        public DashboardStatistics(long totalUsers, long totalCourses, long totalOrders, double totalRevenue) {
            this.totalUsers = totalUsers;
            this.totalCourses = totalCourses;
            this.totalOrders = totalOrders;
            this.totalRevenue = totalRevenue;
        }
        
        public long getTotalUsers() { return totalUsers; }
        public long getTotalCourses() { return totalCourses; }
        public long getTotalOrders() { return totalOrders; }
        public double getTotalRevenue() { return totalRevenue; }
    }
}