# 在线教育系统

## 项目简介

这是一个基于Spring Boot + MySQL开发的在线教育系统，包含学员端和教师/管理员端功能。

## 技术栈

- **后端**: Spring Boot 2.7.14, MyBatis 2.3.1, MySQL 8.0
- **前端**: Thymeleaf, HTML5, CSS3, JavaScript
- **数据库**: MySQL 8.0
- **构建工具**: Maven
- **Java版本**: JDK 8+

## 快速开始

### 1. 环境准备

- JDK 8 或更高版本
- Maven 3.6+
- MySQL 8.0
- Git

### 2. 数据库配置

创建数据库：
```sql
CREATE DATABASE edu_logic_vul CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 克隆项目

```bash
git clone https://github.com/goldroad/edu_logic_vul.git
cd edu_logic_vul
```

### 4. 配置数据库连接

编辑 `src/main/resources/application.yml`，确认数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/edu_logic_vul?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: root
```

### 5. 运行项目

```bash
mvn spring-boot:run
```

或者：
```bash
mvn clean package
java -jar target/edu-logic-vul-1.0.0.jar
```

### 6. 访问系统

打开浏览器访问：http://localhost:8081/edu


密码自动填充的值在 login.html里面

MD5加密：
http://localhost:8081/edu/md5



## 功能特性

### 用户管理
- 用户注册和登录
- 多角色支持（学生、教师、管理员）
- 个人资料管理
- 密码重置功能

### 课程管理
- 课程创建和发布
- 课程分类和搜索
- 课程详情展示
- 学习进度跟踪

### 订单系统
- 课程购买
- 订单管理
- 支付处理
- 订单历史查询

### 优惠券系统
- 优惠券创建
- 优惠券领取
- 折扣计算
- 使用记录

### 文件管理
- 文件上传下载
- 文件信息查询
- 目录浏览
- 配置文件管理

## 测试账号

| 角色 | 用户名 | 密码 | 说明 |
|------|--------|------|------|
| 管理员 | admin | admin123 | 系统管理员 |
| 教师 | teacher | teacher123 | 课程教师 |
| 学生 | student | student123 | 普通学生 |
| 弱口令用户 | weakuser | 123456 | 弱口令测试 |
| 测试用户 | user1-user5 | 123456 | 批量测试账号 |

## 数据恢复功能

系统提供了数据恢复功能，可以将所有数据重置到初始状态：

1. 在登录页面点击"恢复数据到初始状态"按钮
2. 确认操作后，系统将清空所有用户数据、订单、优惠券等信息
3. 重新初始化默认的测试数据

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/edu/
│   │       ├── config/          # 配置类
│   │       ├── controller/      # 控制器
│   │       ├── entity/          # 实体类
│   │       ├── repository/      # 数据访问层
│   │       └── service/         # 业务逻辑层
│   └── resources/
│       ├── templates/           # 页面模板
│       ├── init-data.sql        # 初始化数据脚本
│       └── application.yml      # 配置文件
```

# TODO
顶部间隙问题
√ 导航栏的重复定义：已完成学生页面、管理员页面
√ CSS代码的抽取：已完成学生页面、管理员页面
学生：订单状态显示：待处理（管理员处的订单管理是正常的状态）；课程封面不显示
学生：修改资料、上传头像功能实现
管理员：更换头像
管理员：用户管理：最后登录时间
管理员：课程管理，封面显示不完整

## 优化
√ 前端JS对密码增加密码MD5加密（不加盐）
注册：模拟发送短信验证码（增加短信表）

## 暂时不考虑的
增加签到获取积分功能（逻辑漏洞），展示签到日历，积分兑换奖品
实现设置章节、上传视频（关联视频）和学习功能（记录学习进度）
教师后台，基本上没有实现，需要跟学习功能一起实现
实现提问功能（内部社区）
实现模拟扫码支付充值功能（好像扫不了局域网）

##  最后需要做的
修改包名为bafangwy，添加ico 、 logo，修改类说明
自动填充默认密码
整理数据库脚本，请迁移到MySQL5.7
制作Docker容器配置文件
打包需要包含课程封面图片