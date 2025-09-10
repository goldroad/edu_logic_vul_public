/**
 * Copyright © 2023-2024 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-03-22
 */
package com.bafangwy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private Role role = Role.STUDENT;
    private String avatar;
    private Double balance = 0.0;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime updateTime = LocalDateTime.now();
    private LocalDateTime lastLog;
    private Boolean enabled = true;
    
    public enum Role {
        STUDENT, TEACHER, ADMIN
    }
}