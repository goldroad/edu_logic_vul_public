package com.bafangwy.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 * 
 * 数据库创建器 - 在Spring Boot启动前确保数据库存在
 */
public class DatabaseCreator implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();
        
        // 从配置中获取数据库连接信息
        String fullUrl = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        
        if (fullUrl != null && fullUrl.contains("edu_logic_vul")) {
            createDatabaseIfNotExists(fullUrl, username, password);
        }
    }
    
    private void createDatabaseIfNotExists(String fullUrl, String username, String password) {
        try {
            // 解析URL，获取不包含数据库名的连接URL
            // 原URL: jdbc:mysql://localhost:3306/edu_logic_vul?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
            // 目标URL: jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
            
            String baseUrl;
            String databaseName = "edu_logic_vul";
            
            System.out.println("原始URL: " + fullUrl);
            
            // 使用正则表达式替换数据库名
            // 匹配 /数据库名? 或 /数据库名$ 的模式，替换为 ? 或空字符串
            baseUrl = fullUrl.replaceFirst("/edu_logic_vul\\?", "?");
            if (baseUrl.equals(fullUrl)) {
                // 如果没有参数，直接移除数据库名
                baseUrl = fullUrl.replaceFirst("/edu_logic_vul$", "");
            }
            
            System.out.println("尝试连接到MySQL服务器: " + baseUrl);
            
            // 连接到MySQL服务器（不指定数据库）
            try (Connection connection = DriverManager.getConnection(baseUrl, username, password);
                 Statement statement = connection.createStatement()) {
                
                // 检查数据库是否存在
                String checkDbSql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + databaseName + "'";
                ResultSet resultSet = statement.executeQuery(checkDbSql);
                
                if (resultSet.next()) {
                    System.out.println("数据库 " + databaseName + " 已存在");
                } else {
                    // 数据库不存在，创建数据库
                    System.out.println("数据库 " + databaseName + " 不存在，正在创建...");
                    String createDbSql = "CREATE DATABASE " + databaseName + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
                    statement.execute(createDbSql);
                    System.out.println("数据库 " + databaseName + " 创建成功");
                }
                
                resultSet.close();
            }
            
        } catch (Exception e) {
            System.err.println("检查或创建数据库失败: " + e.getMessage());
            e.printStackTrace();
            // 不抛出异常，让应用继续启动
        }
    }
}