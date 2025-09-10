# 管理员头像上传功能修复

## 问题描述
管理员个人资料页面 (`/admin/profile`) 中的"更换头像"按钮没有实现功能，需要参考学生版本的实现来修复。

## 修复内容

### 1. 前端模板修改 (`src/main/resources/templates/admin/profile.html`)

#### 1.1 头像显示修复
- 修改头像img标签，使其能够正确显示用户头像
- 添加id属性以便JavaScript操作

#### 1.2 按钮功能绑定
- 为"更换头像"按钮添加onclick事件处理

#### 1.3 JavaScript功能添加
- 添加`changeAvatar()`函数：创建头像上传模态框
- 添加`previewAvatar()`函数：预览选择的头像文件
- 添加`uploadAvatar()`函数：处理头像上传逻辑

### 2. 后端API实现 (`src/main/java/com/edu/controller/AdminController.java`)

#### 2.1 添加必要的import
```java
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
```

#### 2.2 添加头像上传API
- 路径：`POST /admin/api/upload-avatar`
- 功能：
  - 验证管理员权限
  - 检查文件类型和大小
  - 保存文件到`uploads/avatars/`目录
  - 更新数据库中的头像路径
  - 更新session中的用户信息

## 功能特性

### 文件验证
- 只允许上传图片文件（image/*）
- 文件大小限制：2MB
- 支持常见图片格式：JPG、PNG等

### 文件管理
- 自动创建上传目录
- 生成唯一文件名避免冲突
- 自动删除旧头像文件

### 用户体验
- 实时预览选择的头像
- 上传进度提示
- 成功/失败消息反馈
- 自动刷新页面显示新头像

## 测试步骤

1. **访问管理员个人资料页面**
   ```
   http://localhost:8081/edu/admin/profile
   ```

2. **点击"更换头像"按钮**
   - 应该弹出头像上传模态框

3. **选择头像文件**
   - 选择一个图片文件（JPG/PNG）
   - 应该显示预览图片

4. **上传头像**
   - 点击"上传头像"按钮
   - 应该显示"上传中..."状态
   - 成功后显示成功消息并刷新页面

5. **验证结果**
   - 页面刷新后应该显示新的头像
   - 头像文件应该保存在`uploads/avatars/`目录
   - 数据库中用户的avatar字段应该更新

## 注意事项

1. **权限验证**：只有管理员角色才能访问上传接口
2. **文件安全**：严格验证文件类型，防止上传恶意文件
3. **存储管理**：自动清理旧头像文件，避免磁盘空间浪费
4. **错误处理**：完善的错误处理和用户提示

## API接口

### 上传头像
- **URL**: `POST /edu/admin/api/upload-avatar`
- **参数**: `avatar` (MultipartFile)
- **返回**: 
  ```json
  {
    "success": true,
    "message": "头像上传成功",
    "avatarUrl": "/edu/uploads/avatars/xxx.jpg"
  }
  ```

## 文件结构
```
uploads/
└── avatars/
    ├── uuid1.jpg
    ├── uuid2.png
    └── ...
```

修复完成后，管理员头像上传功能应该与学生版本功能一致，提供完整的头像管理体验。