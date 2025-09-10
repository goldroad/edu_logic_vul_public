# 用户最后登录时间功能更新

## 更新内容

本次更新为用户管理系统添加了正确的"最后登录时间"显示功能。

### 1. 数据库更改

- 在 `users` 表中添加了 `last_log` 字段（DATETIME类型）
- 执行 `add_last_log_column.sql` 脚本来应用数据库更改

### 2. 后端代码更改

#### User实体类 (`src/main/java/com/edu/entity/User.java`)
- 添加了 `lastLog` 字段（LocalDateTime类型）

#### UserRepository (`src/main/java/com/edu/repository/UserRepository.java`)
- 更新了 `save` 和 `update` 方法以包含 `last_log` 字段
- 添加了 `updateLastLog` 方法用于高效更新最后登录时间
- 添加了必要的import语句

#### UserService (`src/main/java/com/edu/service/UserService.java`)
- 修改了 `login` 方法，在登录成功时更新 `lastLog` 字段
- 使用新的 `updateLastLog` 方法提高性能

### 3. 前端模板更改

#### 用户管理页面 (`src/main/resources/templates/admin/users.html`)
- 将"最后登录"列从显示 `user.updateTime` 改为显示 `user.lastLog`
- 当用户从未登录时显示"从未登录"

## 部署步骤

1. **执行数据库脚本**：
   ```sql
   -- 在数据库中执行
   ALTER TABLE users ADD COLUMN last_log DATETIME NULL COMMENT '最后登录时间';
   ```

2. **重新编译并部署应用**：
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```

3. **验证功能**：
   - 访问 `http://localhost:8081/edu/admin/users`
   - 进行用户登录操作
   - 检查用户管理页面中的"最后登录"列是否正确显示

## 预期效果

- 新注册的用户在首次登录前，"最后登录"列显示"从未登录"
- 用户登录成功后，"最后登录"列显示实际的登录时间
- 每次用户登录都会更新最后登录时间

## 注意事项

- 现有用户的 `last_log` 字段初始为 NULL，首次登录后会被设置
- 如果需要为现有用户设置初始值，可以执行：
  ```sql
  UPDATE users SET last_log = update_time WHERE last_log IS NULL;