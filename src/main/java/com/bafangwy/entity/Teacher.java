package com.bafangwy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private Long id;
    private String name;           // 老师姓名
    private String idCard;         // 身份证号
    private String phone;          // 手机号
    private String email;          // 邮箱号
    private String address;        // 家庭住址
    private String title;          // 职称
    private String speciality;     // 专业领域
    private String introduction;   // 个人简介
    private String avatar;         // 头像
    private Integer experience;    // 教学经验（年）
    private Boolean enabled = true; // 是否启用
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime updateTime = LocalDateTime.now();
}