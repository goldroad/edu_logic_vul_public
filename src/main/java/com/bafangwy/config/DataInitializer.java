package com.bafangwy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import java.nio.charset.StandardCharsets;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        // 延迟执行，确保表已经创建
        // Thread.sleep(2000);
        // 启动时不需要再初始化
        // initializeDataFromSql();

    }
    
    private void initializeDataFromSql() {
        try {
            ClassPathResource resource = new ClassPathResource("init-data.sql");
            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String sql = new String(bdata, StandardCharsets.UTF_8);
            
            // 使用更智能的SQL语句分割方法
            String[] statements = splitSqlStatements(sql);
            for (String statement : statements) {
                statement = statement.trim();
                if (!statement.isEmpty() && !statement.startsWith("--")) {
                    jdbcTemplate.execute(statement);
                }
            }
        } catch (Exception e) {
            System.err.println("初始化数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 智能分割SQL语句，正确处理字符串中的分号
     */
    private String[] splitSqlStatements(String sql) {
        java.util.List<String> statements = new java.util.ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean inBacktick = false;
        
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            
            // 处理转义字符
            if (c == '\\' && i + 1 < sql.length()) {
                currentStatement.append(c);
                currentStatement.append(sql.charAt(i + 1));
                i++; // 跳过下一个字符
                continue;
            }
            
            // 处理引号状态
            if (c == '\'' && !inDoubleQuote && !inBacktick) {
                inSingleQuote = !inSingleQuote;
            } else if (c == '"' && !inSingleQuote && !inBacktick) {
                inDoubleQuote = !inDoubleQuote;
            } else if (c == '`' && !inSingleQuote && !inDoubleQuote) {
                inBacktick = !inBacktick;
            }
            
            // 如果遇到分号且不在引号内，则认为是语句结束
            if (c == ';' && !inSingleQuote && !inDoubleQuote && !inBacktick) {
                String statement = currentStatement.toString().trim();
                if (!statement.isEmpty()) {
                    statements.add(statement);
                }
                currentStatement = new StringBuilder();
            } else {
                currentStatement.append(c);
            }
        }
        
        // 添加最后一个语句（如果有的话）
        String lastStatement = currentStatement.toString().trim();
        if (!lastStatement.isEmpty()) {
            statements.add(lastStatement);
        }
        
        return statements.toArray(new String[0]);
    }
    
    /**
     * 重置数据到初始状态
     */
    public void resetToInitialState() {
        try {
            // 重新初始化数据
            initializeDataFromSql();
        } catch (Exception e) {
            System.err.println("重置数据失败: " + e.getMessage());
            throw new RuntimeException("重置数据失败", e);
        }
    }
    
}