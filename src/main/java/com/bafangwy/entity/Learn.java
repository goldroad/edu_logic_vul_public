package com.bafangwy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Learn {
    private Long id;
    private Long userId;        // 学生ID
    private Long courseId;      // 课程ID
    private Integer progress;   // 学习进度 (0-100)
    private LearnStatus status; // 学习状态
    private LocalDateTime startTime;    // 开始学习时间
    private LocalDateTime lastStudyTime; // 最后学习时间
    private LocalDateTime completeTime;  // 完成时间
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime updateTime = LocalDateTime.now();
    
    // 关联对象（用于查询时填充）
    private User user;
    private Course course;
    
    public enum LearnStatus {
        NOT_STARTED("未开始"),
        LEARNING("学习中"), 
        COMPLETED("已完成");
        
        private final String description;
        
        LearnStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
}