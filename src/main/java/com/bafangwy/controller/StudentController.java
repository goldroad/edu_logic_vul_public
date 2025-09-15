package com.bafangwy.controller;

import com.bafangwy.entity.Course;
import com.bafangwy.entity.Learn;
import com.bafangwy.entity.Order;
import com.bafangwy.entity.User;
import com.bafangwy.entity.UserCoupon;
import com.bafangwy.service.CourseService;
import com.bafangwy.service.LearnService;
import com.bafangwy.service.OrderService;
import com.bafangwy.service.UserService;
import com.bafangwy.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserCouponService userCouponService;
    
    @Autowired
    private LearnService learnService;
    
    /**
     * 个人资料页面 - 支持通过ID查询用户信息
     */
    @GetMapping("/profile")
    public String profile(@RequestParam(required = false) Long id, 
                         HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/auth/login";
        }
        
        User targetUser;
        if (id != null) {
            // 根据ID从数据库查询用户信息
            targetUser = userService.findById(id);
            if (targetUser == null) {
                targetUser = currentUser; // 如果查询不到，显示当前用户信息
            }
        } else {
            targetUser = currentUser;
        }
        
        model.addAttribute("user", currentUser);
        model.addAttribute("targetUser", targetUser);
        model.addAttribute("isOwnProfile", targetUser.getId().equals(currentUser.getId()));
        return "student/profile";
    }
    
    /**
     * 更新个人资料
     */
    @PostMapping("/update-profile")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody Map<String, Object> request,
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            // 从数据库获取最新用户信息
            User currentUser = userService.findById(user.getId());
            if (currentUser == null) {
                response.put("success", false);
                response.put("message", "用户不存在");
                return ResponseEntity.ok(response);
            }
            
            // 更新用户信息
            if (request.containsKey("realName")) {
                currentUser.setRealName(request.get("realName").toString());
            }
            if (request.containsKey("email")) {
                currentUser.setEmail(request.get("email").toString());
            }
            if (request.containsKey("phone")) {
                currentUser.setPhone(request.get("phone").toString());
            }
            if (request.containsKey("avatar")) {
                currentUser.setAvatar(request.get("avatar").toString());
            }
            
            // 保存更新
            userService.save(currentUser);
            
            // 更新session中的用户信息
            session.setAttribute("user", currentUser);
            
            response.put("success", true);
            response.put("message", "资料更新成功");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 上传头像
     */
    @PostMapping("/upload-avatar")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadAvatar(@RequestParam("avatar") MultipartFile file,
                                                           HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        if (file.isEmpty()) {
            response.put("success", false);
            response.put("message", "请选择要上传的头像文件");
            return ResponseEntity.ok(response);
        }
        
        try {
            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("success", false);
                response.put("message", "只能上传图片文件");
                return ResponseEntity.ok(response);
            }
            
            // 检查文件大小（限制为2MB）
            if (file.getSize() > 2 * 1024 * 1024) {
                response.put("success", false);
                response.put("message", "头像文件大小不能超过2MB");
                return ResponseEntity.ok(response);
            }
            
            // 创建上传目录
            String uploadDir = "uploads/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + extension;
            
            // 保存文件
            Path filePath = Paths.get(uploadDir + filename);
            Files.copy(file.getInputStream(), filePath);
            
            // 更新用户头像路径
            User currentUser = userService.findById(user.getId());
            if (currentUser != null) {
                // 删除旧头像文件（如果存在）
                if (currentUser.getAvatar() != null && !currentUser.getAvatar().isEmpty()) {
                    try {
                        Path oldFilePath = Paths.get(currentUser.getAvatar());
                        Files.deleteIfExists(oldFilePath);
                    } catch (Exception e) {
                        // 忽略删除旧文件的错误
                    }
                }
                
                currentUser.setAvatar(uploadDir + filename);
                userService.save(currentUser);
                
                // 更新session中的用户信息
                session.setAttribute("user", currentUser);
                
                response.put("success", true);
                response.put("message", "头像上传成功");
                response.put("avatarUrl", "/bafangwy/" + uploadDir + filename);
            } else {
                response.put("success", false);
                response.put("message", "用户不存在");
            }
            
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "上传失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, Object> request,
                                                             HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            String currentPassword = request.get("currentPassword").toString();
            String newPassword = request.get("newPassword").toString();
            String confirmPassword = request.get("confirmPassword").toString();
            
            // 从数据库获取最新用户信息
            User currentUser = userService.findById(user.getId());
            if (currentUser == null) {
                response.put("success", false);
                response.put("message", "用户不存在");
                return ResponseEntity.ok(response);
            }
            
            // 验证当前密码
            if (!currentUser.getPassword().equals(currentPassword)) {
                response.put("success", false);
                response.put("message", "当前密码错误");
                return ResponseEntity.ok(response);
            }
            
            // 验证新密码确认
            if (!newPassword.equals(confirmPassword)) {
                response.put("success", false);
                response.put("message", "新密码与确认密码不匹配");
                return ResponseEntity.ok(response);
            }
            
            // 密码长度验证
            if (newPassword.length() < 6) {
                response.put("success", false);
                response.put("message", "新密码长度至少6位");
                return ResponseEntity.ok(response);
            }
            
            // 更新密码
            currentUser.setPassword(newPassword);
            userService.save(currentUser);
            
            // 更新session中的用户信息
            session.setAttribute("user", currentUser);
            
            response.put("success", true);
            response.put("message", "密码修改成功");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "修改失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 购买课程 - 创建订单
     */
    @PostMapping("/buy-course")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> buyCourse(@RequestBody Map<String, Object> request,
                                                        HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            Long courseId = Long.valueOf(request.get("courseId").toString());
            Course course = courseService.findById(courseId);
            
            if (course == null) {
                response.put("success", false);
                response.put("message", "课程不存在");
                return ResponseEntity.ok(response);
            }
            
            // 创建订单（使用正确的价格，不允许客户端修改）
            Order order = orderService.createOrderSecure(user.getId(), courseId);
            
            response.put("success", true);
            response.put("message", "订单创建成功");
            response.put("orderNo", order.getOrderNo());
            response.put("amount", order.getFinalAmount());
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "创建订单失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 支付页面
     */
    @GetMapping("/payment/{orderNo}")
    public String paymentPage(@PathVariable String orderNo, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        Order order = orderService.findByOrderNo(orderNo);
        if (order == null) {
            model.addAttribute("error", "订单不存在");
            return "student/orders";
        }
        
        // 检查订单是否属于当前用户
        if (!order.getUserId().equals(user.getId())) {
            model.addAttribute("error", "无权访问此订单");
            return "student/orders";
        }
        
        // 检查订单状态
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            model.addAttribute("error", "订单状态不正确");
            return "student/orders";
        }
        
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        return "student/payment";
    }
    
    /**
     * 确认支付 - 支持抓包修改金额的漏洞版本
     */
    @PostMapping("/confirm-payment")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> confirmPayment(@RequestBody Map<String, Object> request,
                                                             HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            String orderNo = request.get("orderNo").toString();
            String paymentMethod = request.get("paymentMethod").toString();
            
            // 漏洞：如果请求中包含金额，直接修改订单金额
            if (request.containsKey("amount")) {
                BigDecimal clientAmount = new BigDecimal(request.get("amount").toString());
                Order order = orderService.findByOrderNo(orderNo);
                if (order != null && order.getStatus() == Order.OrderStatus.PENDING) {
                    order.setFinalAmount(clientAmount);
                    orderService.save(order);
                }
            }
            
            // 处理优惠券使用
            if (request.containsKey("couponId") && request.get("couponId") != null) {
                Long couponId = ((Number) request.get("couponId")).longValue();
                userCouponService.useCoupon(couponId, Long.parseLong(orderNo.replaceAll("\\D", "")));
            }
            
            Order.PaymentMethod method = Order.PaymentMethod.valueOf(paymentMethod.toUpperCase());
            boolean paymentResult = orderService.payOrder(orderNo, method);
            
            if (paymentResult) {
                response.put("success", true);
                response.put("message", "支付成功");
            } else {
                response.put("success", false);
                response.put("message", "支付失败，余额不足或订单状态异常");
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "支付失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取用户优惠券数据
     */
    @GetMapping("/coupons/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCouponsData(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            // 获取统计信息
            Map<String, Integer> stats = userCouponService.getUserCouponStats(user.getId());
            
            // 获取各状态的优惠券
            List<UserCoupon> availableCoupons = userCouponService.getAvailableCoupons(user.getId());
            List<UserCoupon> usedCoupons = userCouponService.getUsedCoupons(user.getId());
            List<UserCoupon> expiredCoupons = userCouponService.getExpiredCoupons(user.getId());
            
            response.put("success", true);
            response.put("stats", stats);
            response.put("availableCoupons", availableCoupons);
            response.put("usedCoupons", usedCoupons);
            response.put("expiredCoupons", expiredCoupons);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取优惠券数据失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 根据状态筛选优惠券
     */
    @GetMapping("/coupons/filter")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> filterCoupons(@RequestParam String status, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            List<UserCoupon> coupons;
            
            switch (status.toLowerCase()) {
                case "available":
                    coupons = userCouponService.getAvailableCoupons(user.getId());
                    break;
                case "used":
                    coupons = userCouponService.getUsedCoupons(user.getId());
                    break;
                case "expired":
                    coupons = userCouponService.getExpiredCoupons(user.getId());
                    break;
                default:
                    coupons = userCouponService.getUserCoupons(user.getId());
                    break;
            }
            
            response.put("success", true);
            response.put("coupons", coupons);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "筛选优惠券失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取可用优惠券（用于支付页面）
     */
    @GetMapping("/available-coupons")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAvailableCoupons(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "用户未登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            List<UserCoupon> availableCoupons = userCouponService.getAvailableCoupons(user.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", availableCoupons);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取可用优惠券失败: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    /**
     * 获取优惠券详情
     */
    @GetMapping("/coupon-detail/{couponId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCouponDetail(
            @PathVariable Long couponId,
            HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "用户未登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            List<UserCoupon> userCoupons = userCouponService.getUserCoupons(user.getId());
            UserCoupon coupon = userCoupons.stream()
                .filter(c -> c.getId().equals(couponId))
                .findFirst()
                .orElse(null);
                
            if (coupon == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "优惠券不存在或不属于当前用户");
                return ResponseEntity.ok(response);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", coupon);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取优惠券详情失败: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
    
    /**
     * 我的课程页面
     */
    @GetMapping("/courses")
    public String courses(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        // 获取用户的学习记录
        List<Learn> learns = learnService.findByUserId(user.getId());
        
        // 获取学习统计信息
        Map<String, Integer> stats = learnService.getUserLearnStats(user.getId());
        
        model.addAttribute("user", user);
        model.addAttribute("learns", learns);
        model.addAttribute("stats", stats);
        return "student/courses";
    }
    
    /**
     * 获取课程数据API
     */
    @GetMapping("/courses/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCoursesData(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            // 获取用户的学习记录
            List<Learn> learns = learnService.findByUserId(user.getId());
            
            // 获取学习统计信息
            Map<String, Integer> stats = learnService.getUserLearnStats(user.getId());
            
            response.put("success", true);
            response.put("learns", learns);
            response.put("stats", stats);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取课程数据失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 根据状态筛选课程
     */
    @GetMapping("/courses/filter")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> filterCourses(@RequestParam String status, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            List<Learn> learns = learnService.filterByStatus(user.getId(), status);
            
            response.put("success", true);
            response.put("learns", learns);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "筛选课程失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 开始/继续学习课程
     */
    @PostMapping("/start-learning")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> startLearning(@RequestBody Map<String, Object> request,
                                                            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            Long courseId = Long.valueOf(request.get("courseId").toString());
            
            // 检查用户是否已购买该课程
            List<Long> paidCourseIds = orderService.getPaidCourseIdsByUserId(user.getId());
            if (!paidCourseIds.contains(courseId)) {
                response.put("success", false);
                response.put("message", "您尚未购买此课程，请先购买后再学习");
                return ResponseEntity.ok(response);
            }
            
            // 开始学习
            Learn learn = learnService.startLearning(user.getId(), courseId);
            
            response.put("success", true);
            response.put("message", "开始学习成功");
            response.put("redirectUrl", "/bafangwy/student/course/learn/" + courseId);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "开始学习失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 课程学习页面
     */
    @GetMapping("/course/learn/{courseId}")
    public String courseLearn(@PathVariable Long courseId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        // 获取课程信息
        Course course = courseService.findById(courseId);
        if (course == null) {
            model.addAttribute("error", "课程不存在");
            return "student/dashboard";
        }
        
        // 检查用户是否已购买该课程
        List<Long> paidCourseIds = orderService.getPaidCourseIdsByUserId(user.getId());
        if (!paidCourseIds.contains(courseId)) {
            model.addAttribute("error", "您尚未购买此课程，请先购买后再学习");
            return "student/dashboard";
        }
        
        // 获取学习记录
        Learn learn = learnService.findByUserIdAndCourseId(user.getId(), courseId);
        if (learn == null) {
            // 如果没有学习记录，创建一个
            learn = learnService.startLearning(user.getId(), courseId);
        }
        
        model.addAttribute("user", user);
        model.addAttribute("course", course);
        model.addAttribute("learn", learn);
        return "student/course-learn";
    }
    
    /**
     * 更新学习进度
     */
    @PostMapping("/update-progress")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateProgress(@RequestBody Map<String, Object> request,
                                                             HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            Long courseId = Long.valueOf(request.get("courseId").toString());
            Integer progress = Integer.valueOf(request.get("progress").toString());
            
            // 更新学习进度
            Learn learn = learnService.updateProgress(user.getId(), courseId, progress);
            
            if (learn != null) {
                response.put("success", true);
                response.put("message", "学习进度更新成功");
                response.put("learn", learn);
            } else {
                response.put("success", false);
                response.put("message", "学习记录不存在");
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新进度失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 取消订单
     */
    @PostMapping("/cancel-order")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cancelOrder(@RequestBody Map<String, Object> request,
                                                          HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return ResponseEntity.ok(response);
        }
        
        try {
            String orderNo = request.get("orderNo").toString();
            Order order = orderService.findByOrderNo(orderNo);
            
            if (order == null) {
                response.put("success", false);
                response.put("message", "订单不存在");
                return ResponseEntity.ok(response);
            }
            
            // 验证订单是否属于当前用户
            if (!order.getUserId().equals(user.getId())) {
                response.put("success", false);
                response.put("message", "无权操作此订单");
                return ResponseEntity.ok(response);
            }
            
            // 只能取消待支付的订单
            if (order.getStatus() != Order.OrderStatus.PENDING) {
                response.put("success", false);
                response.put("message", "只能取消待支付的订单");
                return ResponseEntity.ok(response);
            }
            
            // 更新订单状态
            order.setStatus(Order.OrderStatus.CANCELLED);
            orderService.save(order);
            
            response.put("success", true);
            response.put("message", "订单取消成功");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "取消订单失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 课程详情页面
     */
    @GetMapping("/course/{courseId}")
    public String courseDetail(@PathVariable Long courseId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        // 获取课程信息
        Course course = courseService.findById(courseId);
        if (course == null) {
            model.addAttribute("error", "课程不存在");
            return "student/courses";
        }
        
        // 检查用户是否已购买该课程
        List<Long> paidCourseIds = orderService.getPaidCourseIdsByUserId(user.getId());
        boolean isPurchased = paidCourseIds.contains(courseId);
        
        model.addAttribute("user", user);
        model.addAttribute("course", course);
        model.addAttribute("isPurchased", isPurchased);
        return "student/course";
    }
    
}