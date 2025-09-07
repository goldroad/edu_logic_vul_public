
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


INSERT INTO `course` VALUES (1, NULL, '2025-09-04 10:37:53.134427', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 10:37:53.147433', 2);
INSERT INTO `course` VALUES (2, NULL, '2025-09-04 10:37:53.178160', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 10:37:53.191081', 2);
INSERT INTO `course` VALUES (3, NULL, '2025-09-04 10:37:53.206798', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 10:37:53.222169', 2);
INSERT INTO `course` VALUES (4, NULL, '2025-09-04 10:37:53.238124', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 10:37:53.253123', 2);
INSERT INTO `course` VALUES (5, NULL, '2025-09-04 10:37:53.268334', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 10:37:53.284773', 2);
INSERT INTO `course` VALUES (6, NULL, '2025-09-04 11:00:37.317947', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:00:37.495868', 2);
INSERT INTO `course` VALUES (7, NULL, '2025-09-04 11:00:37.522205', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:00:37.535168', 2);
INSERT INTO `course` VALUES (8, NULL, '2025-09-04 11:00:37.552123', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:00:37.567084', 2);
INSERT INTO `course` VALUES (9, NULL, '2025-09-04 11:00:37.583052', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:00:37.598000', 2);
INSERT INTO `course` VALUES (10, NULL, '2025-09-04 11:00:37.615967', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:00:37.629915', 2);
INSERT INTO `course` VALUES (11, NULL, '2025-09-04 11:01:16.074000', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:01:16.223000', 2);
INSERT INTO `course` VALUES (12, NULL, '2025-09-04 11:01:16.263000', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:01:16.278000', 2);
INSERT INTO `course` VALUES (13, NULL, '2025-09-04 11:01:16.294000', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:01:16.308000', 2);
INSERT INTO `course` VALUES (14, NULL, '2025-09-04 11:01:16.325000', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:01:16.340000', 2);
INSERT INTO `course` VALUES (15, NULL, '2025-09-04 11:01:16.358000', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:01:16.374000', 2);
INSERT INTO `course` VALUES (16, NULL, '2025-09-04 11:02:41.514363', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:02:41.696412', 2);
INSERT INTO `course` VALUES (17, NULL, '2025-09-04 11:02:41.736721', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:02:41.749695', 2);
INSERT INTO `course` VALUES (18, NULL, '2025-09-04 11:02:41.764363', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:02:41.780034', 2);
INSERT INTO `course` VALUES (19, NULL, '2025-09-04 11:02:41.796389', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:02:41.810623', 2);
INSERT INTO `course` VALUES (20, NULL, '2025-09-04 11:02:41.825837', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:02:41.841282', 2);
INSERT INTO `course` VALUES (21, NULL, '2025-09-04 11:05:16.982628', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:05:17.110196', 2);
INSERT INTO `course` VALUES (22, NULL, '2025-09-04 11:05:17.181857', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:05:17.210780', 2);
INSERT INTO `course` VALUES (23, NULL, '2025-09-04 11:05:17.243692', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:05:17.268229', 2);
INSERT INTO `course` VALUES (24, NULL, '2025-09-04 11:05:17.284187', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:05:17.309119', 2);
INSERT INTO `course` VALUES (25, NULL, '2025-09-04 11:05:17.346021', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:05:17.360980', 2);
INSERT INTO `course` VALUES (26, NULL, '2025-09-04 11:10:22.711971', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:10:22.849207', 2);
INSERT INTO `course` VALUES (27, NULL, '2025-09-04 11:10:22.894085', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:10:22.907066', 2);
INSERT INTO `course` VALUES (28, NULL, '2025-09-04 11:10:22.923009', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:10:22.936971', 2);
INSERT INTO `course` VALUES (29, NULL, '2025-09-04 11:10:22.953928', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:10:22.968885', 2);
INSERT INTO `course` VALUES (30, NULL, '2025-09-04 11:10:22.985840', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:10:23.000801', 2);
INSERT INTO `course` VALUES (31, NULL, '2025-09-04 11:22:07.125000', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:22:07.240000', 2);
INSERT INTO `course` VALUES (32, NULL, '2025-09-04 11:22:07.266000', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:22:07.280000', 2);
INSERT INTO `course` VALUES (33, NULL, '2025-09-04 11:22:07.296000', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:22:07.313000', 2);
INSERT INTO `course` VALUES (34, NULL, '2025-09-04 11:22:07.329000', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:22:07.344000', 2);
INSERT INTO `course` VALUES (35, NULL, '2025-09-04 11:22:07.360000', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:22:07.377000', 2);
INSERT INTO `course` VALUES (36, NULL, '2025-09-04 11:36:04.357000', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 11:36:04.518000', 2);
INSERT INTO `course` VALUES (37, NULL, '2025-09-04 11:36:04.544000', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 11:36:04.560000', 2);
INSERT INTO `course` VALUES (38, NULL, '2025-09-04 11:36:04.575000', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 11:36:04.591000', 2);
INSERT INTO `course` VALUES (39, NULL, '2025-09-04 11:36:04.608000', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 11:36:04.622000', 2);
INSERT INTO `course` VALUES (40, NULL, '2025-09-04 11:36:04.639000', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 11:36:04.653000', 2);
INSERT INTO `course` VALUES (41, NULL, '2025-09-04 12:03:12.435000', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 12:03:12.592000', 2);
INSERT INTO `course` VALUES (42, NULL, '2025-09-04 12:03:12.620000', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 12:03:12.633000', 2);
INSERT INTO `course` VALUES (43, NULL, '2025-09-04 12:03:12.648000', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 12:03:12.663000', 2);
INSERT INTO `course` VALUES (44, NULL, '2025-09-04 12:03:12.679000', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 12:03:12.696000', 2);
INSERT INTO `course` VALUES (45, NULL, '2025-09-04 12:03:12.710000', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 12:03:12.724000', 2);
INSERT INTO `course` VALUES (46, NULL, '2025-09-04 23:48:11.595000', '从零开始学习Java编程语言，掌握面向对象编程思想', NULL, 199.00, 199.00, 'PUBLISHED', 0, 'Java基础编程', '2025-09-04 23:48:11.688000', 2);
INSERT INTO `course` VALUES (47, NULL, '2025-09-04 23:48:11.746000', '深入学习Spring Boot框架，快速构建企业级应用', NULL, 299.00, 299.00, 'PUBLISHED', 0, 'Spring Boot实战', '2025-09-04 23:48:11.760000', 2);
INSERT INTO `course` VALUES (48, NULL, '2025-09-04 23:48:11.776000', '学习数据库设计原理，掌握SQL优化技巧', NULL, 259.00, 259.00, 'PUBLISHED', 0, '数据库设计与优化', '2025-09-04 23:48:11.792000', 2);
INSERT INTO `course` VALUES (49, NULL, '2025-09-04 23:48:11.808000', '学习HTML、CSS、JavaScript，成为前端开发工程师', NULL, 179.00, 179.00, 'PUBLISHED', 0, '前端开发入门', '2025-09-04 23:48:11.823000', 2);
INSERT INTO `course` VALUES (50, NULL, '2025-09-04 23:48:11.839000', '了解网络安全基础知识，学习常见攻击防护方法', NULL, 399.00, 399.00, 'PUBLISHED', 0, '网络安全基础', '2025-09-04 23:48:11.854000', 2);

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
INSERT INTO `users` VALUES (4, NULL, 0.00, '2025-09-04 10:35:29.949279', 'weakuser@example.com', 1, '123456', NULL, NULL, 'STUDENT', '2025-09-04 10:35:29.949279', 'weakuser');
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