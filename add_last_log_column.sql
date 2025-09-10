-- 添加last_log字段到users表
ALTER TABLE users ADD COLUMN last_log DATETIME NULL COMMENT '最后登录时间';

-- 可选：为现有用户设置默认值（使用update_time作为初始值）
-- UPDATE users SET last_log = update_time WHERE last_log IS NULL;