package com.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.edu.repository")
@EnableScheduling
public class EduLogicVulApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduLogicVulApplication.class, args);
    }
}