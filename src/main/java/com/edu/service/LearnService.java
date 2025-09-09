package com.edu.service;

import com.edu.entity.Course;
import com.edu.entity.Learn;
import com.edu.entity.User;
import com.edu.repository.LearnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@Service
public class LearnService {
    
    @Autowired
    private LearnRepository learnRepository;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private UserService userService;
    
    public Learn save(Learn learn) {
        return learnRepository.save(learn);
    }
    
    public Learn findById(Long id) {
        return learnRepository.findById(id);
    }
    
    public List<Learn> findByUserId(Long userId) {
        List<Learn> learns = learnRepository.findByUserId(userId);
        // 填充课程信息
        for (Learn learn : learns) {
            Course course = courseService.findById(learn.getCourseId());
            learn.setCourse(course);
        }
        return learns;
    }
    
    public List<Learn> findByUserIdAndStatus(Long userId, Learn.LearnStatus status) {
        List<Learn> learns = learnRepository.findByUserIdAndStatus(userId, status);
        // 填充课程信息
        for (Learn learn : learns) {
            Course course = courseService.findById(learn.getCourseId());
            learn.setCourse(course);
        }
        return learns;
    }
    
    public Learn findByUserIdAndCourseId(Long userId, Long courseId) {
        return learnRepository.findByUserIdAndCourseId(userId, courseId);
    }
    
    /**
     * 开始学习课程
     */
    public Learn startLearning(Long userId, Long courseId) {
        Learn existingLearn = learnRepository.findByUserIdAndCourseId(userId, courseId);
        
        if (existingLearn != null) {
            // 如果已存在学习记录，更新最后学习时间
            existingLearn.setLastStudyTime(LocalDateTime.now());
            if (existingLearn.getStatus() == Learn.LearnStatus.NOT_STARTED) {
                existingLearn.setStatus(Learn.LearnStatus.LEARNING);
                existingLearn.setStartTime(LocalDateTime.now());
            }
            return learnRepository.save(existingLearn);
        } else {
            // 创建新的学习记录
            Learn learn = new Learn();
            learn.setUserId(userId);
            learn.setCourseId(courseId);
            learn.setProgress(0);
            learn.setStatus(Learn.LearnStatus.LEARNING);
            learn.setStartTime(LocalDateTime.now());
            learn.setLastStudyTime(LocalDateTime.now());
            return learnRepository.save(learn);
        }
    }
    
    /**
     * 更新学习进度
     */
    public Learn updateProgress(Long userId, Long courseId, Integer progress) {
        Learn learn = learnRepository.findByUserIdAndCourseId(userId, courseId);
        if (learn != null) {
            learn.setProgress(progress);
            learn.setLastStudyTime(LocalDateTime.now());
            
            // 如果进度达到100%，标记为已完成
            if (progress >= 100) {
                learn.setStatus(Learn.LearnStatus.COMPLETED);
                learn.setCompleteTime(LocalDateTime.now());
            } else if (learn.getStatus() == Learn.LearnStatus.NOT_STARTED) {
                learn.setStatus(Learn.LearnStatus.LEARNING);
                if (learn.getStartTime() == null) {
                    learn.setStartTime(LocalDateTime.now());
                }
            }
            
            return learnRepository.save(learn);
        }
        return null;
    }
    
    /**
     * 获取用户学习统计信息
     */
    public Map<String, Integer> getUserLearnStats(Long userId) {
        Map<String, Integer> stats = new HashMap<>();
        
        int totalCourses = learnRepository.countByUserId(userId);
        int learningCourses = learnRepository.countByUserIdAndStatus(userId, Learn.LearnStatus.LEARNING);
        int completedCourses = learnRepository.countByUserIdAndStatus(userId, Learn.LearnStatus.COMPLETED);
        int notStartedCourses = learnRepository.countByUserIdAndStatus(userId, Learn.LearnStatus.NOT_STARTED);
        
        stats.put("total", totalCourses);
        stats.put("learning", learningCourses);
        stats.put("completed", completedCourses);
        stats.put("notStarted", notStartedCourses);
        
        return stats;
    }
    
    /**
     * 创建学习记录（当用户购买课程后调用）
     */
    public Learn createLearnRecord(Long userId, Long courseId) {
        // 检查是否已存在学习记录
        Learn existingLearn = learnRepository.findByUserIdAndCourseId(userId, courseId);
        if (existingLearn != null) {
            return existingLearn;
        }
        
        // 创建新的学习记录
        Learn learn = new Learn();
        learn.setUserId(userId);
        learn.setCourseId(courseId);
        learn.setProgress(0);
        learn.setStatus(Learn.LearnStatus.NOT_STARTED);
        
        return learnRepository.save(learn);
    }
    
    /**
     * 删除学习记录
     */
    public void deleteById(Long id) {
        learnRepository.deleteById(id);
    }
    
    /**
     * 获取所有学习记录（管理员用）
     */
    public List<Learn> findAll() {
        List<Learn> learns = learnRepository.findAll();
        // 填充用户和课程信息
        for (Learn learn : learns) {
            User user = userService.findById(learn.getUserId());
            Course course = courseService.findById(learn.getCourseId());
            learn.setUser(user);
            learn.setCourse(course);
        }
        return learns;
    }
    
    /**
     * 根据状态筛选学习记录
     */
    public List<Learn> filterByStatus(Long userId, String statusStr) {
        if ("all".equals(statusStr)) {
            return findByUserId(userId);
        }
        
        Learn.LearnStatus status;
        switch (statusStr.toLowerCase()) {
            case "learning":
                status = Learn.LearnStatus.LEARNING;
                break;
            case "completed":
                status = Learn.LearnStatus.COMPLETED;
                break;
            case "not_started":
                status = Learn.LearnStatus.NOT_STARTED;
                break;
            default:
                return findByUserId(userId);
        }
        
        return findByUserIdAndStatus(userId, status);
    }
}