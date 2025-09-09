# Teacher表设计与实现文档

## 概述
本次实现为在线教育系统添加了完整的Teacher（老师）表功能，包括数据库设计、后端API、前端显示等。

## 实现内容

### 1. 数据库设计

#### Teacher表结构
```sql
CREATE TABLE `teacher` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,                    -- 老师姓名
    `id_card` varchar(18) NOT NULL,                  -- 身份证号
    `phone` varchar(20) NOT NULL,                    -- 手机号
    `email` varchar(100) NOT NULL,                   -- 邮箱号
    `address` varchar(255) DEFAULT NULL,             -- 家庭住址
    `title` varchar(50) DEFAULT NULL,                -- 职称
    `speciality` varchar(100) DEFAULT NULL,          -- 专业领域
    `introduction` text DEFAULT NULL,                -- 个人简介
    `avatar` varchar(255) DEFAULT NULL,              -- 头像
    `experience` int(11) DEFAULT 0,                  -- 教学经验（年）
    `enabled` tinyint(1) DEFAULT 1,                  -- 是否启用
    `create_time` datetime(6) DEFAULT NULL,          -- 创建时间
    `update_time` datetime(6) DEFAULT NULL,          -- 更新时间
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_teacher_id_card` (`id_card`),
    UNIQUE KEY `UK_teacher_phone` (`phone`),
    UNIQUE KEY `UK_teacher_email` (`email`)
);
```

#### 测试数据
插入了5位老师的测试数据：
- 张伟 - Java后端开发专家
- 李明 - Python数据分析专家
- 王芳 - 前端开发专家
- 陈强 - 网络安全专家
- 刘娜 - 数据库技术专家

### 2. 后端实现

#### 核心文件
- `src/main/java/com/edu/entity/Teacher.java` - Teacher实体类
- `src/main/java/com/edu/repository/TeacherRepository.java` - Teacher数据访问层
- `src/main/java/com/edu/service/TeacherService.java` - Teacher业务逻辑层
- `src/main/java/com/edu/controller/TeacherController.java` - Teacher API控制器

#### Course实体修改
- 添加了`teacherInfo`字段，用于关联Teacher信息
- 保留了原有的`teacher`字段以确保兼容性

#### CourseRepository修改
- 所有查询方法都添加了Teacher信息的关联查询
- 使用MyBatis的`@One`注解实现一对一关联

### 3. 前端实现

#### 修改的页面
1. **学生Dashboard页面** (`src/main/resources/templates/student/dashboard.html`)
   - 在课程卡片中显示老师姓名和职称
   - 隐藏的div包含老师详细信息（身份证号、手机号、邮箱、地址）

2. **学生课程页面** (`src/main/resources/templates/student/courses.html`)
   - 为每个课程卡片添加隐藏的老师详细信息

3. **管理员课程页面** (`src/main/resources/templates/admin/courses.html`)
   - 在课程信息中显示老师姓名和职称
   - 隐藏的div包含老师详细信息

### 4. API接口

#### Teacher管理API (`/edu/api/teachers`)
- `GET /` - 获取所有启用的老师
- `GET /{id}` - 根据ID获取老师信息
- `POST /` - 创建新老师
- `PUT /{id}` - 更新老师信息
- `DELETE /{id}` - 删除老师
- `GET /search?name={name}` - 搜索老师

### 5. 测试功能

#### 测试页面
- 访问路径：`/edu/test/teachers`
- 功能：显示所有老师信息和课程与老师的关联关系
- 文件：`src/main/resources/templates/test/teachers.html`

## 安全特性

### 信息泄露测试
按照需求，老师的敏感信息（身份证号、手机号、邮箱、家庭住址）通过以下方式实现：
1. **前端不显示**：这些信息在页面上不可见
2. **抓包可见**：信息存储在隐藏的HTML元素的data属性中
3. **API返回**：通过API接口可以获取完整的老师信息

### 数据结构示例
```html
<!-- 隐藏的老师详细信息，抓包可见 -->
<div style="display: none;" 
     data-teacher-id="1"
     data-teacher-idcard="110101198001011234"
     data-teacher-phone="13800000001"
     data-teacher-email="zhangwei@edu.com"
     data-teacher-address="北京市朝阳区建国路88号">
</div>
```

## 使用方法

### 1. 数据库初始化
运行应用程序时，`init-data.sql`会自动创建teacher表并插入测试数据。

### 2. 查看课程与老师关联
- 访问学生Dashboard：`/edu/student/dashboard`
- 访问学生课程页面：`/edu/student/courses`
- 访问管理员课程页面：`/edu/admin/courses`

### 3. 测试老师功能
- 访问测试页面：`/edu/test/teachers`
- 查看所有老师信息和课程关联关系

### 4. API测试
```bash
# 获取所有老师
curl -X GET http://localhost:8080/edu/api/teachers

# 获取指定老师信息
curl -X GET http://localhost:8080/edu/api/teachers/1

# 搜索老师
curl -X GET "http://localhost:8080/edu/api/teachers/search?name=张"
```

### 5. 抓包测试
使用浏览器开发者工具或抓包工具（如Burp Suite）查看页面请求，可以在HTML响应中看到隐藏的老师详细信息。

## 技术特点

1. **完整的CRUD操作**：支持老师信息的增删改查
2. **关联查询优化**：使用MyBatis一对一关联查询
3. **数据完整性**：身份证号、手机号、邮箱唯一约束
4. **兼容性保证**：保留原有teacher字段，确保系统兼容性
5. **安全测试友好**：敏感信息隐藏但可通过抓包获取

## 注意事项

1. 所有课程的teacher_id已更新为对应的Teacher表ID
2. 原有的User表中的teacher角色用户仍然保留，用于登录认证
3. Teacher表专门用于存储老师的详细信息和课程关联
4. 敏感信息的处理符合安全测试需求，既不在前端显示，又可通过技术手段获取