# 在线教育系统

## 一、项目简介

这是一个基于Spring Boot + MySQL开发的在线教育系统，包含学员端和教师/管理员端功能。

作者：无涯老师，版权归属于 [八方网域教育](https://www.bafangwy.com/)。

## 二、技术栈

- **后端**: Spring Boot 2.7.14, MyBatis 2.3.1, MySQL 5.7
- **前端**: Thymeleaf, HTML5, CSS3, JavaScript
- **数据库**: MySQL 5.7 或者8.0
- **构建工具**: Maven
- **Java版本**: JDK 11+，在JDK11中测试通过

## 三、快速开始

### 1. 环境准备

- JDK 11+或更高版本
- Maven 3.6+
- MySQL 5.7 或者8.0，建议用小皮面板启动MySQL

### 2. 数据库配置

创建数据库：

```sql
CREATE DATABASE edu_logic_vul CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置数据库连接

编辑 `src/main/resources/application.yml`，确认数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/edu_logic_vul?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: root
```

### 4. 运行项目

1、启动项目之前需要:

1）配置好JDK

<https://wiki.bafangwy.com/doc/694/>

2）配置好Maven

<https://wiki.bafangwy.com/doc/608/>

3）启动MySQL数据库，建议使用小皮面板启动MySQL

2、导入到IDEA中，使用EduLogicVulApplication.java 启动类启动

其他启动方式：

终端使用Maven命令启动：

```bash
mvn spring-boot:run
```

或者打包以后运行jar包

```bash
mvn clean package
java -jar target/bafangwy-logic-vul-1.0.0.jar
```

### 5. 访问系统

打开浏览器访问：<http://localhost:8081/edu> ，首页有漏洞描述

密码自动填充的值在 login.html 里面配置。

如果需要直接在数据库修改密码，可以用MD5加密工具页：
<http://localhost:8081/edu/md5>

## 四、功能特性

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
- 15分钟未支付自动关闭订单
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

### 定时任务

- 超过15分钟未支付的订单，自动关闭
- 清理在数据库中未登记的文件

### 数据恢复功能

系统提供了数据恢复功能，可以将所有数据重置到初始状态：

1. 在登录页面点击"恢复数据到初始状态"按钮
2. 确认操作后，系统将清空所有用户数据、订单、优惠券等信息
3. 重新初始化默认的测试数据

## 五、漏洞列表

> ⚠️ **安全警告**：本系统包含多种业务逻辑漏洞，仅用于安全测试和教学目的，不可用于生产环境。

### 5.1 认证相关漏洞

| 漏洞名称 | 位置/接口 | 说明 |
|---------|----------|------|
| 用户名枚举漏洞 | 登录界面 `/api/auth/login` | 通过不同用户名返回的不同错误信息判断用户名是否存在 |
| 弱口令问题 | 登录界面 | 系统允许使用简单弱口令，测试账号 student/1 |
| 万能验证码 | 登录界面 `/api/auth/login` | 存在万能验证码可绕过验证 |
| 验证码前端回显 | `/api/auth/captcha` | 验证码明文返回到前端，可被直接获取 |
| 任意用户注册 | 注册界面 `/api/auth/register` | 可注册任意用户，包含管理员账号 |
| 短信轰炸 | 注册界面 `/api/sms/send` | 无频率限制，可无限制发送短信 |
| 短信验证码暴破 | 注册界面 `/api/sms/verify` | 验证码可以被暴力破解 |
| 密码重置验证码暴破 | 密码重置界面 `/api/auth/forgot-password` | 验证码可被暴力破解 |
| 任意密码重置 | 密码重置界面 `/api/auth/forgot-password` | 可重置任意用户密码 |

### 5.2 权限控制漏洞

| 漏洞名称 | 位置/接口 | 说明 |
|---------|----------|------|
| 接口未授权访问 | 参考下方接口列表 | 部分敏感接口未做权限验证 |
| 敏感信息泄露 | 接口返回数据 | 接口返回过多敏感信息给前端 |
| 返回过多信息 | 课程查询接口 | 查询课程时返回关联的教师信息 |
| 水平越权 - 查看用户信息 | `/api/user/{id}` | 可查看其他用户个人信息 |
| 水平越权 - 删除用户文件 | `/api/files/{fileId}` | 可删除其他用户上传的文件 |
| 垂直越权 | `/api/coupons`, `/api/user/admin/info` | 普通用户可访问管理员后台功能 |

### 5.3 支付逻辑漏洞

| 漏洞名称 | 位置/接口 | 说明 |
|---------|----------|------|
| 金额篡改 | 支付界面 `/api/order/create`, `/api/order/{orderNo}/amount` | 可篡改订单金额 |
| 数量小数绕过 | 支付界面 | 数量支持小数，如购买0.001门课程 |
| 数量负数绕过 | 支付界面 | 数量支持负数，可导致余额增加 |
| 折扣任意修改 | 支付界面 | 可修改折扣值，实现低价购买 |
| 并发超额领取优惠券 | 优惠券管理 `/api/coupons` | 并发请求可超额领取优惠券（使用concurrency.py测试） |
| 并发重复使用优惠券 | 优惠券管理 | 并发请求可重复使用同一优惠券 |

### 5.4 其他漏洞

| 漏洞名称 | 位置/接口 | 说明 |
|---------|----------|------|
| 任意文件读取 | `/api/files/view/{fileName}` | 可读取服务器上的任意文件 |

### 5.5 测试账号

> 如果密码不正确，可以使用 MD5 密码生成工具修改密码：<http://localhost:8081/edu/md5>

| 角色 | 用户名 | 密码 |
|-----|-------|------|
| 管理员 | `admin` | `123456` |
| 教师 | `teacher` | `teacher123` |
| 学生 | `student` | `1` |
| 弱口令用户 | `weakuser` | `123456` |

### 5.6 API 接口列表

#### /api/auth - 认证相关接口

| 方法 | 路径 | 漏洞标签 | 说明 |
|-----|------|---------|------|
| POST | `/api/auth/login` | 用户名枚举、弱口令、万能验证码 | 用户登录 |
| POST | `/api/auth/register` | 任意用户注册、短信轰炸 | 用户注册 |
| POST | `/api/auth/forgot-password` | 任意密码重置、验证码暴破 | 忘记密码 |
| GET | `/api/auth/captcha` | 前端回显 | 获取验证码 |
| GET | `/api/auth/current-user` | - | 获取当前用户信息 |
| POST | `/api/auth/logout` | - | 用户登出 |
| GET | `/api/auth/captcha/image` | - | 获取验证码图片 |

#### /admin/api - 用户管理接口（管理员）

| 方法 | 路径 | 说明 |
|-----|------|------|
| GET | `/admin/api/users` | 获取用户列表 |
| GET | `/admin/api/users/{userId}` | 获取用户详情 |
| PUT | `/admin/api/users/{userId}` | 更新用户信息 |
| POST | `/admin/api/users` | 创建用户 |
| POST | `/admin/api/users/{userId}/ban` | 封禁用户 |
| POST | `/admin/api/users/{userId}/unban` | 解封用户 |
| POST | `/admin/api/users/{userId}/reset-password` | 重置用户密码 |
| POST | `/api/files/course-files/upload-cover` | 上传课程封面 |

#### /api/user - 用户管理接口

| 方法 | 路径 | 漏洞标签 | 说明 |
|-----|------|---------|------|
| GET | `/api/user/{id}` | 水平越权 | 获取用户信息 |
| GET | `/api/user/list` | 未授权访问 | 获取用户列表 |
| PUT | `/api/user/{id}` | 水平越权、角色提升 | 修改用户信息 |
| GET | `/api/user/admin/info` | 未授权访问 | 获取管理员信息 |
| DELETE | `/api/user/{id}` | 未授权访问 | 删除用户 |
| POST | `/api/user/{id}/reset-password` | 未授权访问 | 重置用户密码 |

#### /api/courses - 课程管理接口

| 方法 | 路径 | 说明 |
|-----|------|------|
| GET | `/api/courses/{id}` | 获取课程详情 |
| PUT | `/api/courses/{id}` | 更新课程信息 |
| PUT | `/api/courses/{courseId}/status` | 更新课程状态 |
| POST | `/api/courses` | 创建新课程 |
| DELETE | `/api/courses/{courseId}` | 删除课程 |

#### /api/order - 订单管理接口

| 方法 | 路径 | 漏洞标签 | 说明 |
|-----|------|---------|------|
| POST | `/api/order/create` | 支付逻辑漏洞、金额篡改 | 创建订单 |
| POST | `/api/order/{orderNo}/pay` | - | 支付订单 |
| GET | `/api/order/my` | - | 获取我的订单 |
| GET | `/api/order/{orderNo}` | 水平越权 | 获取订单详情 |
| GET | `/api/order/all` | 未授权访问 | 获取所有订单 |
| PUT | `/api/order/{orderNo}/amount` | 支付逻辑漏洞 | 修改订单金额 |

#### /api/coupons - 优惠券相关接口

| 方法 | 路径 | 漏洞标签 | 说明 |
|-----|------|---------|------|
| GET | `/api/coupons` | 垂直越权 | 获取优惠券列表 |
| POST | `/api/coupons` | - | 创建优惠券 |
| PUT | `/api/coupons/{couponId}` | - | 更新优惠券 |
| DELETE | `/api/coupons/{couponId}` | - | 删除优惠券 |
| GET | `/api/coupons/{couponId}` | - | 获取优惠券详情 |
| GET | `/api/coupons/statistics` | - | 获取优惠券统计信息 |
| PUT | `/api/coupons/{couponId}/status` | - | 更新优惠券状态 |
| GET | `/api/coupons/generate-code` | - | 生成优惠券代码 |

#### /api/teachers - 教师相关接口

| 方法 | 路径 | 说明 |
|-----|------|------|
| GET | `/api/teachers/{id}` | 获取教师详情 |
| PUT | `/api/teachers/{id}` | 更新教师信息 |
| DELETE | `/api/teachers/{id}` | 删除教师 |
| GET | `/api/teachers/search` | 搜索教师 |

#### /api/files - 文件管理接口

| 方法 | 路径 | 漏洞标签 | 说明 |
|-----|------|---------|------|
| POST | `/api/files/upload` | - | 上传文件 |
| GET | `/api/files/list` | - | 获取文件列表 |
| GET | `/api/files/search` | - | 搜索文件 |
| GET | `/api/files/download/{fileId}` | 水平越权 | 下载文件 |
| DELETE | `/api/files/{fileId}` | 水平越权 | 删除文件 |
| GET | `/api/files/view/{fileName}` | 任意文件读取 | 查看文件 |
| GET | `/api/files/stats` | - | 获取文件统计信息 |

#### /api/sms - 短信验证码接口

| 方法 | 路径 | 漏洞标签 | 说明 |
|-----|------|---------|------|
| POST | `/api/sms/send` | 短信轰炸 | 发送验证码 |
| POST | `/api/sms/verify` | 验证码暴破 | 验证验证码 |

#### /api/profile - 个人资料接口

| 方法 | 路径 | 说明 |
|-----|------|------|
| POST | `/api/profile/change-password` | 修改密码 |
| POST | `/api/profile/update` | 更新个人信息 |

#### /api/system - 系统管理接口

| 方法 | 路径 | 漏洞标签 | 说明 |
|-----|------|---------|------|
| GET | `/api/system/info` | 未授权访问、敏感信息泄露 | 获取系统信息 |
| POST | `/api/system/reset-data` | - | 重置数据到初始状态 |
| GET | `/api/system/file-cleanup/manual` | - | 手动清理文件 |

## 六、项目结构

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

