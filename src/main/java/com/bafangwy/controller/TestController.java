package com.bafangwy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 * 测试控制器
 * 用于测试MD5哈希加盐功能
 */
@Controller
@RequestMapping("/")
public class TestController {

    /**
     * 密码加密测试页面
     */
    @GetMapping("/md5")
    public String passwordTest() {
        return "md5";
    }
}