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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `coupon` VALUES (1, 'NEW50', '2025-09-04 10:37:53.299429', 50.00, 1, '2026-05-04 10:37:53.299429', 100.00, '新用户专享', '2025-09-04 10:37:53.299429', 100, 'FIXED', 0);
INSERT INTO `coupon` VALUES (2, 'DISCOUNT20', '2025-09-04 10:37:53.315098', 0.20, 1, '2026-05-04 10:37:53.299429', 200.00, '限时8折', '2025-09-04 10:37:53.299429', 50, 'PERCENT', 0);
INSERT INTO `coupon` VALUES (3, 'CONCURRENT', '2025-09-04 10:37:53.331095', 10.00, 1, '2026-05-04 10:37:53.299429', 50.00, '并发测试券', '2025-09-04 10:37:53.299429', 5, 'FIXED', 0);
INSERT INTO `coupon` VALUES (5, 'NEWUSER2024', '2025-09-06 16:14:37.000000', 20.00, 1, '2026-05-04 10:37:53.299429', 100.00, '新用户专享', '2025-09-06 16:14:37.000000', 100, 'FIXED', 0);
INSERT INTO `coupon` VALUES (6, 'DISCOUNT15', '2025-09-06 16:14:37.000000', 15.00, 1, '2026-05-04 10:37:53.299429', 200.00, '折扣券', '2025-09-06 16:14:37.000000', 50, 'PERCENT', 0);
INSERT INTO `coupon` VALUES (7, 'CODING50', '2025-09-06 16:14:37.000000', 50.00, 1, '2026-05-04 10:37:53.299429', 300.00, '编程类目特惠', '2025-09-06 16:14:37.000000', 30, 'FIXED', 1);
INSERT INTO `coupon` VALUES (8, 'WEEKEND20', '2025-09-06 16:14:37.000000', 20.00, 1, '2026-05-04 10:37:53.299429', 150.00, '周末特惠', '2025-09-06 16:14:37.000000', 80, 'PERCENT', 0);
INSERT INTO `coupon` VALUES (9, 'VIP100', '2025-09-06 16:14:37.000000', 100.00, 1, '2026-05-04 10:37:53.299429', 500.00, 'VIP专享', '2025-09-06 16:14:37.000000', 5, 'FIXED', 1);
INSERT INTO `coupon` VALUES (10, 'FREE', '2025-09-06 18:18:57.000000', 100.00, 1, '2026-06-27 18:19:07.000000', 1.00, '免单', '2025-09-06 18:19:39.000000', 20, 'PERCENT', 2);
INSERT INTO `coupon` VALUES (11, '5Y3FLKWR', '2025-09-07 18:03:11.754385', 100.00, 1, '2026-01-09 18:02:00.000000', 150.00, 'WUYA', '2025-09-08 18:02:00.000000', 10, 'FIXED', 0);

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
                           INDEX `FKt4ba5fab1x56tmt4nsypv5lm5`(`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `course` VALUES (1, 'd5102708-8818-4aa9-8670-a3f4ea1bf1a9.jfif', '2025-09-04 10:37:53.134427', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'DRAFT', 0, 'Java基础编程', '2025-09-07 17:35:08.458461', 2);
INSERT INTO `course` VALUES (2, 'eddbfedb-e22b-4cd9-b381-2ef855ac5be6.png', '2025-09-04 10:37:53.178160', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-07 17:35:01.185310', 3);
INSERT INTO `course` VALUES (3, 'd752dbca-c8bf-45d1-b938-32a58134f50f.png', '2025-09-04 10:37:53.206798', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-07 17:34:54.816950', 5);
INSERT INTO `course` VALUES (4, 'ef057ba6-400b-49fe-9ff1-5673fa1d8d77.jfif', '2025-09-04 10:37:53.238124', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-07 17:34:47.437145', 2);
INSERT INTO `course` VALUES (5, 'd68c48d0-84ed-4612-8c05-de5c6e10c72f.png', '2025-09-04 10:37:53.268334', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-07 17:34:34.987919', 4);
INSERT INTO `course` VALUES (51, 'bc7480f4-0faf-46e7-b6c7-691a55303f3a.jpg', '2025-09-07 17:01:53.689656', 'Vue前端开发', NULL, 899.00, 899.00, 'PUBLISHED', 0, 'Vue前端开发', '2025-09-07 18:02:23.049109', 1);

DROP TABLE IF EXISTS `files`;
CREATE TABLE `files`  (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `user_id` bigint(20) NOT NULL,
                          `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          `stored_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          `file_size` bigint(20) NOT NULL,
                          `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `mime_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `upload_time` datetime(6) NULL DEFAULT NULL,
                          `download_count` int(11) NOT NULL DEFAULT 0,
                          `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `idx_user_id`(`user_id`) USING BTREE,
                          INDEX `idx_stored_name`(`stored_name`) USING BTREE,
                          INDEX `idx_upload_time`(`upload_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `files` VALUES (1, 3, 'CTF战队名称.pdf', '0a5e17f2-a5b6-4188-b133-c53d4a1502ef.pdf', 'files\\0a5e17f2-a5b6-4188-b133-c53d4a1502ef.pdf', 598660, 'PDF', 'application/pdf', '2025-09-06 13:50:42.490337', 0, 1);
INSERT INTO `files` VALUES (2, 3, 'bootstrap.min.css', '8cb21a20-8302-46f5-aa35-1aec2904884e.css', 'files/8cb21a20-8302-46f5-aa35-1aec2904884e.css', 163873, 'OTHER', 'text/css', '2025-09-06 13:58:00.551308', 0, 1);
INSERT INTO `files` VALUES (21, 1, '640 (1).jfif', 'b75e6a92-3de0-4dfd-9bf6-7075c66e267d.jfif', 'files/b75e6a92-3de0-4dfd-9bf6-7075c66e267d.jfif', 44853, 'OTHER', 'image/jpeg', '2025-09-07 17:01:53.567400', 0, 0);
INSERT INTO `files` VALUES (22, 1, '640_看图王.jpg', 'd8733858-c9eb-436b-a380-30f9d5494bde.jpg', 'files/d8733858-c9eb-436b-a380-30f9d5494bde.jpg', 37251, 'IMAGE', 'image/jpeg', '2025-09-07 17:25:26.812798', 0, 0);
INSERT INTO `files` VALUES (23, 1, '640_看图王.jpg', 'bc7480f4-0faf-46e7-b6c7-691a55303f3a.jpg', 'files/bc7480f4-0faf-46e7-b6c7-691a55303f3a.jpg', 37251, 'IMAGE', 'image/jpeg', '2025-09-07 17:34:24.622908', 0, 0);
INSERT INTO `files` VALUES (24, 1, '640 (8).png', 'd68c48d0-84ed-4612-8c05-de5c6e10c72f.png', 'files/d68c48d0-84ed-4612-8c05-de5c6e10c72f.png', 377903, 'IMAGE', 'image/png', '2025-09-07 17:34:34.911972', 0, 0);
INSERT INTO `files` VALUES (25, 1, '640 (4).jfif', 'ef057ba6-400b-49fe-9ff1-5673fa1d8d77.jfif', 'files/ef057ba6-400b-49fe-9ff1-5673fa1d8d77.jfif', 40795, 'OTHER', 'image/jpeg', '2025-09-07 17:34:47.290350', 0, 0);
INSERT INTO `files` VALUES (26, 1, '640 (9).png', 'd752dbca-c8bf-45d1-b938-32a58134f50f.png', 'files/d752dbca-c8bf-45d1-b938-32a58134f50f.png', 97140, 'IMAGE', 'image/png', '2025-09-07 17:34:54.783464', 0, 0);
INSERT INTO `files` VALUES (27, 1, '640 (7).png', 'eddbfedb-e22b-4cd9-b381-2ef855ac5be6.png', 'files/eddbfedb-e22b-4cd9-b381-2ef855ac5be6.png', 28305, 'IMAGE', 'image/png', '2025-09-07 17:35:01.134692', 0, 0);
INSERT INTO `files` VALUES (28, 1, '640 (5).jfif', 'd5102708-8818-4aa9-8670-a3f4ea1bf1a9.jfif', 'files/d5102708-8818-4aa9-8670-a3f4ea1bf1a9.jfif', 30848, 'OTHER', 'image/jpeg', '2025-09-07 17:35:08.398202', 0, 0);
INSERT INTO `files` VALUES (29, 3, '640 (1).png', '51cefe3a-6cc1-44ca-bc49-4464023dc0ea.png', 'files/51cefe3a-6cc1-44ca-bc49-4464023dc0ea.png', 270245, 'IMAGE', 'image/png', '2025-09-08 15:43:33.923709', 0, 0);
INSERT INTO `files` VALUES (30, 3, 'CVE-2024-53677-s2-067漏洞.txt', '1f130f5e-e019-43df-b490-b424a946dbc8.txt', 'files/1f130f5e-e019-43df-b490-b424a946dbc8.txt', 49, 'TEXT', 'text/plain', '2025-09-08 16:00:17.672757', 0, 0);

DROP TABLE IF EXISTS `learn`;
CREATE TABLE `learn`  (
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
                          UNIQUE INDEX `uk_user_course`(`user_id`, `course_id`) USING BTREE,
                          INDEX `idx_user_id`(`user_id`) USING BTREE,
                          INDEX `idx_course_id`(`course_id`) USING BTREE,
                          INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学习记录表' ROW_FORMAT = Dynamic;

INSERT INTO `learn` VALUES (1, 3, 1, 75, 'LEARNING', '2025-09-01 10:00:00.000000', '2025-09-09 11:30:54.006342', NULL, '2025-09-01 10:00:00.000000', '2025-09-09 11:30:54.006342');
INSERT INTO `learn` VALUES (2, 3, 2, 100, 'COMPLETED', '2025-08-15 09:00:00.000000', '2025-09-09 11:30:48.474972', '2025-08-30 16:45:00.000000', '2025-08-15 09:00:00.000000', '2025-09-09 11:30:48.474972');
INSERT INTO `learn` VALUES (3, 3, 3, 45, 'LEARNING', '2025-08-20 14:00:00.000000', '2025-09-07 11:20:00.000000', NULL, '2025-08-20 14:00:00.000000', '2025-09-09 11:06:31.461714');
INSERT INTO `learn` VALUES (4, 3, 4, 0, 'NOT_STARTED', NULL, NULL, NULL, '2025-09-05 12:00:00.000000', '2025-09-05 12:00:00.000000');
INSERT INTO `learn` VALUES (5, 3, 5, 30, 'LEARNING', '2025-08-25 16:30:00.000000', '2025-09-09 11:30:51.189445', NULL, '2025-08-25 16:30:00.000000', '2025-09-09 11:30:51.189445');
INSERT INTO `learn` VALUES (7, 8, 1, 60, 'LEARNING', '2025-08-28 09:15:00.000000', '2025-09-08 10:45:00.000000', NULL, '2025-08-28 09:15:00.000000', '2025-09-08 10:45:00.000000');
INSERT INTO `learn` VALUES (8, 8, 3, 100, 'COMPLETED', '2025-08-01 11:00:00.000000', '2025-08-20 15:30:00.000000', '2025-08-20 15:30:00.000000', '2025-08-01 11:00:00.000000', '2025-08-20 15:30:00.000000');
INSERT INTO `learn` VALUES (9, 9, 2, 85, 'LEARNING', '2025-08-10 13:20:00.000000', '2025-09-07 16:10:00.000000', NULL, '2025-08-10 13:20:00.000000', '2025-09-07 16:10:00.000000');
INSERT INTO `learn` VALUES (10, 9, 4, 100, 'COMPLETED', '2025-07-15 08:30:00.000000', '2025-08-05 14:20:00.000000', '2025-08-05 14:20:00.000000', '2025-07-15 08:30:00.000000', '2025-08-05 14:20:00.000000');
INSERT INTO `learn` VALUES (11, 10, 1, 25, 'LEARNING', '2025-09-01 15:45:00.000000', '2025-09-08 09:30:00.000000', NULL, '2025-09-01 15:45:00.000000', '2025-09-08 09:30:00.000000');
INSERT INTO `learn` VALUES (12, 11, 5, 90, 'LEARNING', '2025-08-05 12:15:00.000000', '2025-09-08 13:45:00.000000', NULL, '2025-08-05 12:15:00.000000', '2025-09-08 13:45:00.000000');

DROP TABLE IF EXISTS `login_logs`;
CREATE TABLE `login_logs`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `user_id` bigint(20) NOT NULL,
                               `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                               `ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                               `user_agent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
                               `operating_system` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                               `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                               `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                               `login_time` datetime(6) NULL DEFAULT NULL,
                               `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'SUCCESS',
                               `session_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `idx_user_id`(`user_id`) USING BTREE,
                               INDEX `idx_login_time`(`login_time`) USING BTREE,
                               INDEX `idx_status`(`status`) USING BTREE,
                               INDEX `idx_ip_address`(`ip_address`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 185 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `login_logs` VALUES (1, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 09:10:22.000000', 'SUCCESS', 'session-001');
INSERT INTO `login_logs` VALUES (2, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-06 14:15:30.000000', 'SUCCESS', 'session-002');
INSERT INTO `login_logs` VALUES (3, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-05 16:22:15.000000', 'SUCCESS', 'session-003');
INSERT INTO `login_logs` VALUES (4, 1, 'admin', '192.168.1.101', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-05 10:30:45.000000', 'FAILED', 'session-004');
INSERT INTO `login_logs` VALUES (5, 1, 'admin', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-04 11:45:12.000000', 'SUCCESS', 'session-005');
INSERT INTO `login_logs` VALUES (6, 1, 'admin', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 18:36:12.752094', 'SUCCESS', '3D346AEE578D8EEA561035455F42919F');
INSERT INTO `login_logs` VALUES (7, 3, 'student', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 18:36:28.306041', 'FAILED', '4B295D94C16BD72C163B071900AB1CBB');
INSERT INTO `login_logs` VALUES (8, 1, 'admin', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 18:36:37.773149', 'SUCCESS', '4B295D94C16BD72C163B071900AB1CBB');
INSERT INTO `login_logs` VALUES (9, 1, 'admin', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 18:57:33.445240', 'SUCCESS', '507A6CF6C5DDD2A77880D460F400B725');
INSERT INTO `login_logs` VALUES (10, 3, 'student', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 18:59:36.430234', 'SUCCESS', '18AEB97DE0D90FC74A53052FCF3B7EDD');
INSERT INTO `login_logs` VALUES (11, 3, 'student', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 19:05:28.029489', 'SUCCESS', 'AC32B8A54A28745BA3FA350B22ADBF8D');
INSERT INTO `login_logs` VALUES (12, 3, 'student', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 19:10:14.233165', 'SUCCESS', '0829A6235209A0527D7B10967AB3DAAC');
INSERT INTO `login_logs` VALUES (13, 1, 'admin', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 19:11:00.944205', 'SUCCESS', '6D03825C0E37AEB986719B53645D3332');
INSERT INTO `login_logs` VALUES (14, 3, 'student', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 19:11:09.088186', 'SUCCESS', '3DACD3E6B5103E0BEB2FD43B919C8E88');
INSERT INTO `login_logs` VALUES (15, 3, 'student', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 19:11:51.994733', 'SUCCESS', 'D436D269B0617CBF018DE41B7C2420F5');
INSERT INTO `login_logs` VALUES (16, 3, 'student', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 19:12:30.698971', 'SUCCESS', 'EF43DCB37A954DC88E7B77F1A87251F7');
INSERT INTO `login_logs` VALUES (17, 1, 'admin', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36', 'Windows 10', 'Google Chrome', '本地网络', '2025-09-07 19:13:01.626228', 'SUCCESS', '0149DC8A1167A8E46E51898C4BA5F47E');


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
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `order` VALUES (15, '2025-09-06 18:16:27.185855', 0.00, 199.00, 'ORD17571537871854923', 199.00, '2025-09-06 18:16:34.166305', 'BALANCE', 'cb5d3350-5abf-4531-9f4b-def62565da18', 1, NULL, 0.00, 'PAID', 1, 3);
INSERT INTO `order` VALUES (16, '2025-09-06 18:27:32.446983', 0.00, 299.00, 'ORD17571544524465655', 299.00, NULL, NULL, NULL, 1, NULL, 0.00, 'CANCELLED', 2, 3);
INSERT INTO `order` VALUES (17, '2025-09-06 18:28:25.210001', 0.00, 399.00, 'ORD17571545052107864', 399.00, NULL, NULL, NULL, 1, NULL, 0.00, 'CANCELLED', 5, 3);
INSERT INTO `order` VALUES (18, '2025-09-06 18:29:23.599348', 0.00, 399.00, 'ORD17571545635996707', 399.00, NULL, NULL, NULL, 1, NULL, 0.00, 'CANCELLED', 5, 3);
INSERT INTO `order` VALUES (19, '2025-09-07 19:01:17.206014', 0.00, 299.00, 'ORD17572428772061700', 299.00, '2025-09-07 19:01:23.499123', 'BALANCE', '6b16f8d5-e7d0-4391-9cc6-b186c7c307d8', 1, NULL, 0.00, 'PAID', 2, 3);
INSERT INTO `order` VALUES (20, '2025-09-09 11:03:17.283110', 0.00, 899.00, 'ORD17573869972833004', 899.00, '2025-09-09 11:03:32.569200', 'BALANCE', '6ca171c0-4d98-4045-b6cf-067b5e3b1fe4', 1, NULL, 0.00, 'PAID', 51, 3);
INSERT INTO `order` VALUES (21, '2025-09-09 11:26:05.144773', 0.00, 399.00, 'ORD17573883651438127', 399.00, '2025-09-09 11:26:08.147436', 'BALANCE', '5d319b25-24e8-4459-b1a3-d8d8f8c29544', 1, NULL, 0.00, 'PAID', 5, 3);

DROP TABLE IF EXISTS `sms_records`;
CREATE TABLE `sms_records`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
                                `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
                                `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信内容',
                                `send_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '发送时间',
                                `is_used` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已使用',
                                `expire_time` datetime(6) NOT NULL COMMENT '过期时间',
                                `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'REGISTER' COMMENT '短信类型',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `idx_phone`(`phone`) USING BTREE,
                                INDEX `idx_send_time`(`send_time`) USING BTREE,
                                INDEX `idx_expire_time`(`expire_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1014 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信记录表' ROW_FORMAT = Dynamic;

INSERT INTO `sms_records` VALUES (1013, '13800007777', '4942', '您的验证码是：4942，有效期1分钟，请勿泄露给他人。', '2026-06-16 14:42:04.371369', 1, '2026-06-16 14:43:04.371369', 'REGISTER');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `teacher` VALUES (1, '张伟', '110101198001011234', '13800000001', 'zhangwei@bafangwy.com', '北京市朝阳区建国路88号', '高级讲师', 'Java后端开发', '10年Java开发经验，精通Spring Boot、微服务架构', NULL, 10, 1, '2025-09-04 10:35:29.000000', '2025-09-04 10:35:29.000000');
INSERT INTO `teacher` VALUES (2, '李明', '110101198502022345', '13800000002', 'liming@bafangwy.com', '北京市海淀区中关村大街123号', '副教授', 'Python数据分析', '8年Python开发经验，专注数据科学和机器学习', NULL, 8, 1, '2025-09-04 10:35:30.000000', '2025-09-04 10:35:30.000000');
INSERT INTO `teacher` VALUES (3, '王芳', '110101198703033456', '13800000003', 'wangfang@bafangwy.com', '上海市浦东新区陆家嘴金融区', '讲师', '前端开发', '6年前端开发经验，精通Vue.js、React等框架', NULL, 6, 1, '2025-09-04 10:35:31.000000', '2025-09-04 10:35:31.000000');
INSERT INTO `teacher` VALUES (4, '无涯', '110101198904044567', '13800000004', 'wuya@bafangwy.com', '深圳市南山区科技园', '安全研究院院长', '网络安全', '15年网络安全经验，CISSP认证专家', NULL, 15, 1, '2025-09-04 10:35:32.000000', '2025-09-04 10:35:32.000000');
INSERT INTO `teacher` VALUES (5, '刘娜', '110101199005055678', '13800000005', 'liuna@bafangwy.com', '广州市天河区珠江新城', '助理教授', '数据库技术', '7年数据库管理经验，Oracle、MySQL专家', NULL, 7, 1, '2025-09-04 10:35:33.000000', '2025-09-04 10:35:33.000000');

DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `user_id` bigint(20) NOT NULL,
                                `coupon_id` bigint(20) NULL DEFAULT NULL,
                                `coupon_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                `coupon_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
                                `type` enum('FIXED','PERCENT') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                `discount_value` decimal(10, 2) NOT NULL,
                                `min_amount` decimal(10, 2) NULL DEFAULT 0.00,
                                `status` enum('UNUSED','USED','EXPIRED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'UNUSED',
                                `receive_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
                                `use_time` datetime(0) NULL DEFAULT NULL,
                                `expire_time` datetime(0) NULL DEFAULT NULL,
                                `usage_restriction` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                `applicable_category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                `order_id` bigint(20) NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `idx_user_id`(`user_id`) USING BTREE,
                                INDEX `idx_status`(`status`) USING BTREE,
                                INDEX `idx_expire_time`(`expire_time`) USING BTREE,
                                INDEX `idx_coupon_code`(`coupon_code`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user_coupon` VALUES (1, 1, 1, '新用户优惠', 'NEW50', '新用户专项，首次下单使用', 'PERCENT', 50.00, 0.00, 'UNUSED', '2024-09-01 10:00:00', NULL, '2024-12-31 23:59:59', 'First purchase only', 'All courses', NULL);
INSERT INTO `user_coupon` VALUES (2, 1, 2, '满200减30', 'SAVE30', '满200减30', 'FIXED', 30.00, 200.00, 'UNUSED', '2024-09-02 14:30:00', NULL, '2024-10-15 23:59:59', 'Min spend 200', 'All courses', NULL);
INSERT INTO `user_coupon` VALUES (3, 1, 3, '编程类课程优惠', 'CODE20', '仅编程类课程特惠', 'PERCENT', 20.00, 0.00, 'UNUSED', '2024-09-03 09:15:00', NULL, '2024-11-30 23:59:59', 'Programming courses only', 'Programming', NULL);
INSERT INTO `user_coupon` VALUES (4, 1, 4, '满300减50', 'BIG50', '满200减50', 'FIXED', 50.00, 300.00, 'UNUSED', '2024-09-04 16:20:00', NULL, '2024-09-30 23:59:59', 'Min spend 300', 'All courses', NULL);
INSERT INTO `user_coupon` VALUES (5, 1, 5, '周末学习优惠', 'WEEKEND15', '周末学习优惠', 'PERCENT', 15.00, 0.00, 'UNUSED', '2024-09-05 11:45:00', NULL, '2024-10-31 23:59:59', 'Weekend purchase only', 'All courses', NULL);
INSERT INTO `user_coupon` VALUES (6, 1, 6, '满100减20', 'FIRST20', 'Used on 2024-09-01', 'FIXED', 20.00, 100.00, 'USED', '2024-08-25 10:00:00', '2024-09-01 15:30:00', '2024-12-31 23:59:59', 'Min spend 100', 'All courses', 1001);
INSERT INTO `user_coupon` VALUES (7, 1, 7, '学生优惠', 'STUDENT10', 'Used on 2024-08-28', 'PERCENT', 10.00, 0.00, 'USED', '2024-08-20 14:00:00', '2024-08-28 10:15:00', '2024-12-31 23:59:59', 'Student only', 'All courses', 1002);
INSERT INTO `user_coupon` VALUES (8, 1, 8, '暑假优惠', 'SUMMER25', 'Expired on 2024-08-31', 'PERCENT', 25.00, 0.00, 'EXPIRED', '2024-07-01 10:00:00', NULL, '2024-08-31 23:59:59', 'Summer special', 'All courses', NULL);
INSERT INTO `user_coupon` VALUES (9, 2, 1, '新用户优惠', 'NEW50_U2', 'For all courses, first purchase only', 'PERCENT', 50.00, 0.00, 'UNUSED', '2024-09-01 10:00:00', NULL, '2024-12-31 23:59:59', 'First purchase only', 'All courses', NULL);
INSERT INTO `user_coupon` VALUES (10, 2, 2, '满200减30', 'SAVE30_U2', '满200减30', 'FIXED', 30.00, 200.00, 'USED', '2024-09-02 14:30:00', '2024-09-05 16:20:00', '2024-10-15 23:59:59', 'Min spend 200', 'All courses', 1003);
INSERT INTO `user_coupon` VALUES (11, 3, 1, '特定课程优惠', 'DESIGN30', '特定课程优惠', 'PERCENT', 30.00, 0.00, 'EXPIRED', '2024-09-01 10:00:00', NULL, '2025-09-03 10:00:00', 'Design courses only', 'Design', NULL);
INSERT INTO `user_coupon` VALUES (12, 3, 2, '满150减25', 'SAVE25', '满150减25', 'FIXED', 25.00, 150.00, 'USED', '2024-09-02 14:30:00', '2025-09-06 15:46:36', '2025-12-02 14:30:00', 'Min spend 150', 'All courses', 17571447438127778);
INSERT INTO `user_coupon` VALUES (13, 3, 3, '早鸟券', 'EARLY20', '前100位用户下单特惠', 'PERCENT', 20.00, 0.00, 'USED', '2024-09-01 10:00:00', '2025-09-06 16:28:20', '2025-12-01 10:00:00', 'Early purchase only', 'All courses', 17571472678626266);
INSERT INTO `user_coupon` VALUES (14, 3, 7, '编程类优惠', 'CODING50', '通过兑换码获得：Programming Course Coupon', 'FIXED', 50.00, 300.00, 'UNUSED', '2025-09-06 16:25:47', NULL, '2025-11-05 16:14:37', '通过兑换码获得', 'All courses', NULL);
INSERT INTO `user_coupon` VALUES (29, 3, 10, '免单', 'FREE', '通过兑换码获得：免单', 'PERCENT', 100.00, 1.00, 'UNUSED', '2025-09-06 18:29:15', NULL, '2026-06-27 18:19:07', '通过兑换码获得', 'All courses', NULL);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `balance` decimal(10, 2) NULL DEFAULT 0.00,
                          `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                          `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                          `enabled` tinyint(1) NULL DEFAULT 1,
                          `create_time` datetime(6) NULL DEFAULT NULL,
                          `update_time` datetime(6) NULL DEFAULT NULL,
                          `last_log` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6`(`username`) USING BTREE,
                          UNIQUE INDEX `UK_6dotkott2kjsp8vw4d0m25fb7`(`email`) USING BTREE,
                          UNIQUE INDEX `UK_du5v5sr43g5bfnji4vb8hg5s3`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

INSERT INTO `users` VALUES (1, 'admin', '系统管理员', 'ADMIN', 10000.00, 'admin@bafangwy.com', 'e10adc3949ba59abbe56e057f20f883e', '13800000001', 'uploads/avatars/d04a82c5-1f57-4e8c-9f3c-9dc7d0214f77.jpeg', 1, '2025-09-04 10:35:29.870634', '2025-09-10 14:46:02.828697', '2025-09-10 14:45:54');
INSERT INTO `users` VALUES (2, 'teacher', '张老师', 'TEACHER', 5000.00, 'teacher@bafangwy.com', 'a426dcf72ba25d046591f81a5495eab7', '13800000002', NULL, 1, '2025-09-04 10:35:29.917526', '2025-09-04 10:35:29.917526', '2025-09-04 10:35:30');
INSERT INTO `users` VALUES (3, 'student', '无同学', 'STUDENT', 8702.00, 'student@bafangwy.com', 'c4ca4238a0b923820dcc509a6f75849b', '13800000006', 'uploads/avatars/41451d53-4253-4d0b-ac3f-b8274e3a9e0d.jpg', 1, '2025-09-04 10:35:29.937387', '2025-09-10 14:38:51.686293', '2025-09-10 14:38:52');
INSERT INTO `users` VALUES (4, 'weakuser', '弱口令', 'STUDENT', 666.00, 'weakuser@example.com', 'e10adc3949ba59abbe56e057f20f883e', '13800000007', NULL, 1, '2025-09-04 10:35:29.949279', '2025-09-04 10:35:29.949279', '2025-09-04 10:35:30');
INSERT INTO `users` VALUES (8, 'user1', '测试用户1', 'STUDENT', 500.00, 'user1@test.com', 'e10adc3949ba59abbe56e057f20f883e', '13800000101', NULL, 1, '2025-09-04 10:37:53.000765', '2025-09-04 10:37:53.000765', '2025-09-04 10:37:53');
INSERT INTO `users` VALUES (9, 'user2', '测试用户2', 'STUDENT', 500.00, 'user2@test.com', 'e10adc3949ba59abbe56e057f20f883e', '13800000102', NULL, 1, '2025-09-04 10:37:53.072906', '2025-09-04 10:37:53.072906', '2025-09-04 10:37:53');
INSERT INTO `users` VALUES (10, 'user3', '测试用户3', 'STUDENT', 500.00, 'user3@test.com', 'e10adc3949ba59abbe56e057f20f883e', '13800000103', NULL, 1, '2025-09-04 10:37:53.087709', '2025-09-04 10:37:53.087709', '2025-09-04 10:37:53');
INSERT INTO `users` VALUES (11, 'user4', '测试用户4', 'STUDENT', 500.00, 'user4@test.com', 'e10adc3949ba59abbe56e057f20f883e', '13800000104', NULL, 1, '2025-09-04 10:37:53.102993', '2025-09-04 10:37:53.102993', '2025-09-04 10:37:53');
INSERT INTO `users` VALUES (12, 'user5', '测试用户5', 'STUDENT', 500.00, 'user5@test.com', 'e10adc3949ba59abbe56e057f20f883e', '13800000105', NULL, 1, '2025-09-04 10:37:53.118782', '2025-09-04 10:37:53.118782', '2025-09-04 10:37:53');
INSERT INTO `users` VALUES (13, 'fds', '发的扫', 'STUDENT', 0.00, '432432@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '432432', NULL, 0, '2025-09-06 13:14:11.005343', '2025-09-07 18:58:21.410553', '2025-09-07 18:58:21');
INSERT INTO `users` VALUES (14, 'jkjjhk', '空间', 'STUDENT', 0.00, '432fdsfd@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '4324321', NULL, 1, '2025-09-07 19:26:39.979487', '2025-09-07 19:26:39.979487', '2025-09-07 19:26:40');
INSERT INTO `users` VALUES (18, 'vfdfdsfdsffds', 'fdsafds', 'STUDENT', 0.00, 'fdsaf@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '4324322', NULL, 1, '2025-09-07 19:51:13.755197', '2025-09-07 19:51:13.755197', '2025-09-07 19:51:14');
INSERT INTO `users` VALUES (19, 'sms', 'fdsafds', 'STUDENT', 0.00, '3784@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '13144445555', NULL, 1, '2025-09-09 14:21:11.863452', '2025-09-09 14:21:11.863452', '2025-09-09 14:21:12');
INSERT INTO `users` VALUES (20, 'lakehouse', 'fdsafds', 'STUDENT', 0.00, '1fdsf432432@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '13800007777', NULL, 1, '2025-09-09 14:42:35.159937', '2025-09-09 14:42:35.159937', '2025-09-09 14:42:35');