
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `create_time` datetime(6) NULL DEFAULT NULL,
                            `discount_value` decimal(10, 2) NULL DEFAULT NULL,
                            `enabled` tinyint(1) NULL DEFAULT 1,
                            `end_time` datetime(6) NULL DEFAULT NULL,
                            `min_amount` decimal(10, 2) NULL DEFAULT NULL,
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `start_time` datetime(6) NULL DEFAULT NULL,
                            `total_count` int(11) NULL DEFAULT NULL,
                            `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                            `used_count` int(11) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `UK_eplt0kkm9yf2of2lnx6c1oy9b`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `coupon` VALUES (1, 'NEW50', '2025-09-04 10:37:53.299429', 50.00, 1, '2025-10-04 10:37:53.299429', 100.00, '新用户专享', '2025-09-04 10:37:53.299429', 100, 'FIXED', 0);
INSERT INTO `coupon` VALUES (2, 'DISCOUNT20', '2025-09-04 10:37:53.315098', 0.20, 1, '2025-10-04 10:37:53.299429', 200.00, '限时8折', '2025-09-04 10:37:53.299429', 50, 'PERCENT', 0);
INSERT INTO `coupon` VALUES (3, 'CONCURRENT', '2025-09-04 10:37:53.331095', 10.00, 1, '2025-10-04 10:37:53.299429', 50.00, '并发测试券', '2025-09-04 10:37:53.299429', 5, 'FIXED', 0);

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                            `create_time` datetime(6) NULL DEFAULT NULL,
                            `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
                            `duration` int(11) NULL DEFAULT NULL,
                            `original_price` decimal(10, 2) NULL DEFAULT NULL,
                            `price` decimal(10, 2) NOT NULL,
                            `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                            `student_count` int(11) NULL DEFAULT NULL,
                            `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `update_time` datetime(6) NULL DEFAULT NULL,
                            `teacher_id` bigint(20) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `FKt4ba5fab1x56tmt4nsypv5lm5`(`teacher_id`) USING BTREE) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


INSERT INTO `course` VALUES (1, 'https://images.unsplash.com/photo-1517077304055-6e89abbf09b0?w=300&h=200&fit=crop', '2025-09-04 10:37:53.134427', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 10:37:53.147433', 1);
INSERT INTO `course` VALUES (2, 'https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=300&h=200&fit=crop', '2025-09-04 10:37:53.178160', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 10:37:53.191081', 1);
INSERT INTO `course` VALUES (3, 'https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=300&h=200&fit=crop', '2025-09-04 10:37:53.206798', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 10:37:53.222169', 5);
INSERT INTO `course` VALUES (4, 'https://images.unsplash.com/photo-1627398242454-45a1465c2479?w=300&h=200&fit=crop', '2025-09-04 10:37:53.238124', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 10:37:53.253123', 3);
INSERT INTO `course` VALUES (5, 'https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=300&h=200&fit=crop', '2025-09-04 10:37:53.268334', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 10:37:53.284773', 4);
-- 更新所有重复课程的teacher_id，按照课程类型分配给不同老师
-- Java课程分配给张伟老师(id=1)
-- Spring Boot课程分配给张伟老师(id=1) 
-- 数据库课程分配给刘娜老师(id=5)
-- 前端课程分配给王芳老师(id=3)
-- 网络安全课程分配给陈强老师(id=4)

INSERT INTO `course` VALUES (6, 'https://images.unsplash.com/photo-1517077304055-6e89abbf09b0?w=300&h=200&fit=crop', '2025-09-04 11:00:37.317947', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:00:37.495868', 1);
INSERT INTO `course` VALUES (7, 'https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=300&h=200&fit=crop', '2025-09-04 11:00:37.522205', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:00:37.535168', 1);
INSERT INTO `course` VALUES (8, 'https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=300&h=200&fit=crop', '2025-09-04 11:00:37.552123', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:00:37.567084', 5);
INSERT INTO `course` VALUES (9, 'https://images.unsplash.com/photo-1627398242454-45a1465c2479?w=300&h=200&fit=crop', '2025-09-04 11:00:37.583052', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:00:37.598000', 3);
INSERT INTO `course` VALUES (10, 'https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=300&h=200&fit=crop', '2025-09-04 11:00:37.615967', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:00:37.629915', 4);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `create_time` datetime(6) NULL DEFAULT NULL,
                           `discount_amount` decimal(10, 2) NOT NULL,
                           `final_amount` decimal(10, 2) NOT NULL,
                           `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                           `original_amount` decimal(10, 2) NOT NULL,
                           `pay_time` datetime(6) NULL DEFAULT NULL,
                           `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                           `payment_transaction_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                           `quantity` int(11) NULL DEFAULT NULL,
                           `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                           `shipping_fee` decimal(10, 2) NOT NULL,
                           `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                           `course_id` bigint(20) NULL DEFAULT NULL,
                           `user_id` bigint(20) NULL DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE INDEX `UK_g8pohnngqi5x1nask7nff2u7w`(`order_no`) USING BTREE,
                           INDEX `FK68snkj0g5gsjxllhjc3v5lm0r`(`course_id`) USING BTREE,
                           INDEX `FK32ql8ubntj5uh44ph9659tiih`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `order` VALUES (1, '2025-09-04 17:09:18.208000', 1.00, 0.00, 'ORD17569769582089296', 1.00, '2025-09-04 21:11:04.915153', 'BALANCE', '989a1c86-2cdc-45ca-ae09-fcb4b72f620c', 1, NULL, 0.00, 'PAID', 1, 3);
INSERT INTO `order` VALUES (2, '2025-09-05 10:30:00.000000', 100.00, 199.00, 'ORD202409050001', 299.00, '2025-09-05 10:35:00.000000', 'BALANCE', 'TXN-20240905-001', 1, NULL, 0.00, 'PAID', 1, 3);
INSERT INTO `order` VALUES (3, '2025-09-04 15:20:00.000000', 100.00, 299.00, 'ORD202409040002', 399.00, NULL, NULL, NULL, 1, NULL, 0.00, 'PENDING', 2, 3);
INSERT INTO `order` VALUES (4, '2025-09-03 09:15:00.000000', 60.00, 399.00, 'ORD202409030003', 459.00, '2025-09-03 09:20:00.000000', 'BALANCE', 'TXN-20240903-001', 1, NULL, 0.00, 'PAID', 5, 3);
INSERT INTO `order` VALUES (5, '2025-09-02 14:45:00.000000', 100.00, 199.00, 'ORD202409020004', 299.00, NULL, NULL, NULL, 1, NULL, 0.00, 'CANCELLED', 1, 3);
INSERT INTO `order` VALUES (6, '2025-09-01 11:30:00.000000', 100.00, 299.00, 'ORD202409010005', 399.00, '2025-09-01 11:35:00.000000', 'BALANCE', 'TXN-20240901-001', 1, NULL, 0.00, 'PAID', 2, 3);
INSERT INTO `order` VALUES (7, '2025-08-31 16:20:00.000000', 80.00, 179.00, 'ORD202408310006', 259.00, '2025-08-31 16:25:00.000000', 'BALANCE', 'TXN-20240831-001', 1, NULL, 0.00, 'PAID', 4, 3);
INSERT INTO `order` VALUES (8, '2025-08-30 13:10:00.000000', 60.00, 259.00, 'ORD202408300007', 319.00, NULL, NULL, NULL, 1, NULL, 0.00, 'PENDING', 3, 3);

DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon`  (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `receive_time` datetime(6) NULL DEFAULT NULL,
                                 `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                                 `use_time` datetime(6) NULL DEFAULT NULL,
                                 `coupon_id` bigint(20) NULL DEFAULT NULL,
                                 `order_id` bigint(20) NULL DEFAULT NULL,
                                 `user_id` bigint(20) NULL DEFAULT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `FK9oi3p5xyfe4j32xs54nn7mi20`(`coupon_id`) USING BTREE,
                                 INDEX `FK75uvyldhruqeeman5b0l35hnu`(`order_id`) USING BTREE,
                                 INDEX `FK654lvm2qu8l08pyg310mbd74h`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                            `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                            `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                            `speciality` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                            `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
                            `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                            `experience` int(11) NULL DEFAULT 0,
                            `enabled` tinyint(1) NULL DEFAULT 1,
                            `create_time` datetime(6) NULL DEFAULT NULL,
                            `update_time` datetime(6) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `UK_teacher_id_card`(`id_card`) USING BTREE,
                            UNIQUE INDEX `UK_teacher_phone`(`phone`) USING BTREE,
                            UNIQUE INDEX `UK_teacher_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `teacher` VALUES (1, '张伟', '110101198001011234', '13800000001', 'zhangwei@edu.com', '北京市朝阳区建国路88号', '高级讲师', 'Java后端开发', '10年Java开发经验，精通Spring Boot、微服务架构', NULL, 10, 1, '2025-09-04 10:35:29.000000', '2025-09-04 10:35:29.000000');
INSERT INTO `teacher` VALUES (2, '李明', '110101198502022345', '13800000002', 'liming@edu.com', '北京市海淀区中关村大街123号', '副教授', 'Python数据分析', '8年Python开发经验，专注数据科学和机器学习', NULL, 8, 1, '2025-09-04 10:35:30.000000', '2025-09-04 10:35:30.000000');
INSERT INTO `teacher` VALUES (3, '王芳', '110101198703033456', '13800000003', 'wangfang@edu.com', '上海市浦东新区陆家嘴金融区', '讲师', '前端开发', '6年前端开发经验，精通Vue.js、React等框架', NULL, 6, 1, '2025-09-04 10:35:31.000000', '2025-09-04 10:35:31.000000');
INSERT INTO `teacher` VALUES (4, '陈强', '110101198904044567', '13800000004', 'chenqiang@edu.com', '深圳市南山区科技园', '高级工程师', '网络安全', '12年网络安全经验，CISSP认证专家', NULL, 12, 1, '2025-09-04 10:35:32.000000', '2025-09-04 10:35:32.000000');
INSERT INTO `teacher` VALUES (5, '刘娜', '110101199005055678', '13800000005', 'liuna@edu.com', '广州市天河区珠江新城', '助理教授', '数据库技术', '7年数据库管理经验，Oracle、MySQL专家', NULL, 7, 1, '2025-09-04 10:35:33.000000', '2025-09-04 10:35:33.000000');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `balance` decimal(10, 2) NULL DEFAULT 0.00,
                          `create_time` datetime(6) NULL DEFAULT NULL,
                          `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `enabled` tinyint(1) NULL DEFAULT 1,
                          `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `update_time` datetime(6) NULL DEFAULT NULL,
                          `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6`(`username`) USING BTREE,
                          UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7`(`email`) USING BTREE,
                          UNIQUE INDEX `UK_du5v5sr43g5bfnji4vb8hg5s3`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


INSERT INTO `users` VALUES (1, NULL, 10000.00, '2025-09-04 10:35:29.870634', 'admin@edu.com', 1, '666666', '13800000001', '系统管理员', 'ADMIN', '2025-09-05 14:49:32.868000', 'admin');
INSERT INTO `users` VALUES (2, NULL, 5000.00, '2025-09-04 10:35:29.917526', 'teacher@edu.com', 1, 'teacher123', '13800000002', '张老师', 'TEACHER', '2025-09-04 10:35:29.917526', 'teacher');
INSERT INTO `users` VALUES (3, NULL, 10.00, '2025-09-04 10:35:29.937387', 'student@edu.com', 1, '1', '13800000003', '李同学', 'STUDENT', '2025-09-04 21:11:04.853467', 'student');
INSERT INTO `users` VALUES (4, NULL, 1000.00, '2025-09-04 10:35:29.949279', 'weakuser@example.com', 1, '123456', '13800000004', '弱密码用户', 'STUDENT', '2025-09-04 10:35:29.949279', 'weakuser');
INSERT INTO `users` VALUES (8, NULL, 500.00, '2025-09-04 10:37:53.000765', 'user1@test.com', 1, '123456', '13800000101', '测试用户1', 'STUDENT', '2025-09-04 10:37:53.000765', 'user1');
INSERT INTO `users` VALUES (9, NULL, 500.00, '2025-09-04 10:37:53.072906', 'user2@test.com', 1, '123456', '13800000102', '测试用户2', 'STUDENT', '2025-09-04 10:37:53.072906', 'user2');
INSERT INTO `users` VALUES (10, NULL, 500.00, '2025-09-04 10:37:53.087709', 'user3@test.com', 1, '123456', '13800000103', '测试用户3', 'STUDENT', '2025-09-04 10:37:53.087709', 'user3');
INSERT INTO `users` VALUES (11, NULL, 500.00, '2025-09-04 10:37:53.102993', 'user4@test.com', 1, '123456', '13800000104', '测试用户4', 'STUDENT', '2025-09-04 10:37:53.102993', 'user4');
INSERT INTO `users` VALUES (12, NULL, 500.00, '2025-09-04 10:37:53.118782', 'user5@test.com', 1, '123456', '13800000105', '测试用户5', 'STUDENT', '2025-09-04 10:37:53.118782', 'user5');

DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `stored_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_size` bigint(20) NOT NULL,
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mime_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `upload_time` datetime(6) NOT NULL,
  `download_count` int(11) NOT NULL DEFAULT 0,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id` (`user_id`) USING BTREE,
  INDEX `idx_stored_name` (`stored_name`) USING BTREE,
  INDEX `idx_upload_time` (`upload_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `login_logs`;
CREATE TABLE `login_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_agent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `operating_system` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `login_time` datetime(6) NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'SUCCESS',
  `session_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id` (`user_id`) USING BTREE,
  INDEX `idx_login_time` (`login_time`) USING BTREE,
  INDEX `idx_status` (`status`) USING BTREE,
  INDEX `idx_ip_address` (`ip_address`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- 插入一些示例登录记录
INSERT INTO `login_logs` VALUES 
(1, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 09:10:22.000000', 'SUCCESS', 'session-001'),
(2, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-06 14:15:30.000000', 'SUCCESS', 'session-002'),
(3, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-05 16:22:15.000000', 'SUCCESS', 'session-003'),
(4, 1, 'admin', '192.168.1.101', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-05 10:30:45.000000', 'FAILED', 'session-004'),
(5, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-04 11:45:12.000000', 'SUCCESS', 'session-005');

-- 创建学习记录表
DROP TABLE IF EXISTS `learn`;
CREATE TABLE `learn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '学生ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `progress` int(11) NOT NULL DEFAULT 0 COMMENT '学习进度(0-100)',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'NOT_STARTED' COMMENT '学习状态',
  `start_time` datetime(6) NULL DEFAULT NULL COMMENT '开始学习时间',
  `last_study_time` datetime(6) NULL DEFAULT NULL COMMENT '最后学习时间',
  `complete_time` datetime(6) NULL DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_course` (`user_id`, `course_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_course_id` (`course_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学习记录表' ROW_FORMAT = Dynamic;

-- 插入测试数据 - 基于HTML页面中的模拟数据
INSERT INTO `learn` VALUES 
-- 学生ID=3 (student用户) 的学习记录
(1, 3, 1, 75, 'LEARNING', '2025-09-01 10:00:00.000000', '2025-09-08 15:30:00.000000', NULL, '2025-09-01 10:00:00.000000', '2025-09-08 15:30:00.000000'),
(2, 3, 2, 100, 'COMPLETED', '2025-08-15 09:00:00.000000', '2025-08-30 16:45:00.000000', '2025-08-30 16:45:00.000000', '2025-08-15 09:00:00.000000', '2025-08-30 16:45:00.000000'),
(3, 3, 7, 45, 'LEARNING', '2025-08-20 14:00:00.000000', '2025-09-07 11:20:00.000000', NULL, '2025-08-20 14:00:00.000000', '2025-09-07 11:20:00.000000'),
(4, 3, 4, 0, 'NOT_STARTED', NULL, NULL, NULL, '2025-09-05 12:00:00.000000', '2025-09-05 12:00:00.000000'),
(5, 3, 9, 30, 'LEARNING', '2025-08-25 16:30:00.000000', '2025-09-06 14:15:00.000000', NULL, '2025-08-25 16:30:00.000000', '2025-09-06 14:15:00.000000'),
(6, 3, 10, 100, 'COMPLETED', '2025-07-10 10:00:00.000000', '2025-07-25 17:30:00.000000', '2025-07-25 17:30:00.000000', '2025-07-10 10:00:00.000000', '2025-07-25 17:30:00.000000'),
-- 其他学生的学习记录
(7, 8, 1, 60, 'LEARNING', '2025-08-28 09:15:00.000000', '2025-09-08 10:45:00.000000', NULL, '2025-08-28 09:15:00.000000', '2025-09-08 10:45:00.000000'),
(8, 8, 3, 100, 'COMPLETED', '2025-08-01 11:00:00.000000', '2025-08-20 15:30:00.000000', '2025-08-20 15:30:00.000000', '2025-08-01 11:00:00.000000', '2025-08-20 15:30:00.000000'),
(9, 9, 2, 85, 'LEARNING', '2025-08-10 13:20:00.000000', '2025-09-07 16:10:00.000000', NULL, '2025-08-10 13:20:00.000000', '2025-09-07 16:10:00.000000'),
(10, 9, 4, 100, 'COMPLETED', '2025-07-15 08:30:00.000000', '2025-08-05 14:20:00.000000', '2025-08-05 14:20:00.000000', '2025-07-15 08:30:00.000000', '2025-08-05 14:20:00.000000'),
(11, 10, 1, 25, 'LEARNING', '2025-09-01 15:45:00.000000', '2025-09-08 09:30:00.000000', NULL, '2025-09-01 15:45:00.000000', '2025-09-08 09:30:00.000000'),
(12, 11, 5, 90, 'LEARNING', '2025-08-05 12:15:00.000000', '2025-09-08 13:45:00.000000', NULL, '2025-08-05 12:15:00.000000', '2025-09-08 13:45:00.000000');

-- 创建短信记录表
DROP TABLE IF EXISTS `sms_records`;
CREATE TABLE `sms_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信内容',
  `send_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '发送时间',
  `is_used` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已使用',
  `expire_time` datetime(6) NOT NULL COMMENT '过期时间',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'REGISTER' COMMENT '短信类型',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_phone` (`phone`) USING BTREE,
  INDEX `idx_send_time` (`send_time`) USING BTREE,
  INDEX `idx_expire_time` (`expire_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信记录表' ROW_FORMAT = Dynamic;

-- 插入一些测试短信记录
INSERT INTO `sms_records` VALUES 
(1, '13800000001', '123456', '您的验证码是：123456，有效期1分钟，请勿泄露给他人。', '2025-09-09 10:30:00.000000', 1, '2025-09-09 10:31:00.000000', 'REGISTER'),
(2, '13800000002', '654321', '您的验证码是：654321，有效期1分钟，请勿泄露给他人。', '2025-09-09 11:15:00.000000', 0, '2025-09-09 11:16:00.000000', 'REGISTER'),
(3, '13800000003', '789012', '您的验证码是：789012，有效期1分钟，请勿泄露给他人。', '2025-09-09 12:00:00.000000', 1, '2025-09-09 12:01:00.000000', 'REGISTER'),
(4, '13800000001', '345678', '您的验证码是：345678，有效期1分钟，请勿泄露给他人。', '2025-09-08 14:20:00.000000', 0, '2025-09-08 14:21:00.000000', 'REGISTER'),
(5, '13800000004', '901234', '您的验证码是：901234，有效期1分钟，请勿泄露给他人。', '2025-09-08 16:45:00.000000', 1, '2025-09-08 16:46:00.000000', 'REGISTER');