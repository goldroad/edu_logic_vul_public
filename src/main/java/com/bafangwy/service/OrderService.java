package com.bafangwy.service;

import com.bafangwy.entity.Course;
import com.bafangwy.entity.Order;
import com.bafangwy.entity.User;
import com.bafangwy.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2025-09
 */
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private LearnService learnService;
    
    /**
     * 创建订单 - 包含支付逻辑漏洞
     */
    public Order createOrder(Long userId, Long courseId, BigDecimal clientPrice, 
                           Integer quantity, BigDecimal discount, BigDecimal shippingFee) {
        
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        Course course = courseService.findById(courseId);
        if (course == null) {
            throw new RuntimeException("课程不存在");
        }
        
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(user.getId());
        order.setCourseId(course.getId());
        order.setUser(user);
        order.setCourse(course);
        
        // 漏洞1：直接使用客户端传来的价格，不验证
        order.setOriginalAmount(clientPrice);
        
        // 漏洞2：允许负数数量
        order.setQuantity(quantity);
        
        // 漏洞3：允许任意折扣
        order.setDiscountAmount(discount != null ? discount : BigDecimal.ZERO);
        
        // 漏洞4：允许修改运费（在线课程不应该有运费）
        order.setShippingFee(shippingFee != null ? shippingFee : BigDecimal.ZERO);
        
        // 计算最终金额（存在漏洞的计算逻辑）
        BigDecimal finalAmount = clientPrice
                .multiply(BigDecimal.valueOf(quantity))
                .subtract(order.getDiscountAmount())
                .add(order.getShippingFee());
        
        order.setFinalAmount(finalAmount);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setCreateTime(LocalDateTime.now());
        
        orderRepository.save(order);
        return order;
    }
    
    /**
     * 正确的订单创建方法（用于对比）
     */
    public Order createOrderSecure(Long userId, Long courseId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        Course course = courseService.findById(courseId);
        if (course == null) {
            throw new RuntimeException("课程不存在");
        }
        
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(user.getId());
        order.setCourseId(course.getId());
        order.setUser(user);
        order.setCourse(course);
        order.setOriginalAmount(course.getPrice()); // 使用服务端价格
        order.setQuantity(1); // 固定数量
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setShippingFee(BigDecimal.ZERO); // 在线课程无运费
        order.setFinalAmount(course.getPrice());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setCreateTime(LocalDateTime.now());
        
        orderRepository.save(order);
        return order;
    }
    
    /**
     * 支付订单 - 支持抓包修改金额的漏洞版本
     */
    public boolean payOrder(String orderNo, Order.PaymentMethod paymentMethod) {
        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null) {
            return false;
        }
        
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            return false;
        }
        
        // 重新获取最新的用户信息
        User user = userService.findById(order.getUserId());
        if (user == null) {
            return false;
        }
        
        // 模拟支付逻辑
        if (paymentMethod == Order.PaymentMethod.BALANCE) {
            // 漏洞：直接使用订单中的finalAmount，不验证是否被篡改
            double paymentAmount = order.getFinalAmount().doubleValue();
            
            if (user.getBalance() >= paymentAmount) {
                // 扣除用户余额
                user.setBalance(user.getBalance() - paymentAmount);
                userService.save(user);
                
                // 更新订单状态
                order.setStatus(Order.OrderStatus.PAID);
                order.setPaymentMethod(paymentMethod);
                order.setPayTime(LocalDateTime.now());
                order.setPaymentTransactionId(UUID.randomUUID().toString());
                
                // 确保订单数据更新到数据库
                orderRepository.update(order);
                
                // 支付成功后创建学习记录
                try {
                    learnService.createLearnRecord(order.getUserId(), order.getCourseId());
                } catch (Exception e) {
                    System.err.println("创建学习记录失败: " + e.getMessage());
                    // 不影响支付流程，只记录错误
                }
                
                return true;
            }
            return false;
        }
        
        // 其他支付方式模拟成功
        order.setStatus(Order.OrderStatus.PAID);
        order.setPaymentMethod(paymentMethod);
        order.setPayTime(LocalDateTime.now());
        order.setPaymentTransactionId(UUID.randomUUID().toString());
        
        // 确保订单数据更新到数据库
        orderRepository.update(order);
        
        // 支付成功后创建学习记录
        try {
            learnService.createLearnRecord(order.getUserId(), order.getCourseId());
        } catch (Exception e) {
            System.err.println("创建学习记录失败: " + e.getMessage());
            // 不影响支付流程，只记录错误
        }
        
        return true;
    }
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserId(user.getId());
    }
    
    public Order findByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo);
    }
    
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + String.format("%04d", (int)(Math.random() * 10000));
    }
    
    public List<Order> findByUser(User user) {
        return orderRepository.findByUserId(user.getId());
    }
    
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    
    public List<Order> findByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status.name());
    }
    
    public Order save(Order order) {
        if (order.getId() == null) {
            orderRepository.save(order);
        } else {
            orderRepository.update(order);
        }
        return order;
    }
    
    /**
     * 获取用户已购买的课程ID列表
     */
    public List<Long> getPaidCourseIdsByUserId(Long userId) {
        return orderRepository.findPaidCourseIdsByUserId(userId);
    }
    
    /**
     * 分页查询订单
     */
    public List<Order> findOrdersWithPagination(String status, String search, int offset, int limit) {
        List<Order> allOrders = orderRepository.findAll();
        
        return allOrders.stream()
                .filter(order -> {
                    boolean statusMatch = status == null || status.trim().isEmpty() || status.equals("ALL") || 
                            order.getStatus().name().equals(status);
                    boolean searchMatch = search == null || search.trim().isEmpty() || 
                            (order.getOrderNo() != null && order.getOrderNo().toLowerCase().contains(search.toLowerCase())) ||
                            (order.getUser() != null && order.getUser().getRealName() != null && 
                             order.getUser().getRealName().toLowerCase().contains(search.toLowerCase())) ||
                            (order.getCourse() != null && order.getCourse().getTitle() != null && 
                             order.getCourse().getTitle().toLowerCase().contains(search.toLowerCase()));
                    return statusMatch && searchMatch;
                })
                .sorted((o1, o2) -> {
                    // 按创建时间倒序排列，确保分页结果稳定
                    return o2.getCreateTime().compareTo(o1.getCreateTime());
                })
                .skip(offset)
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * 统计符合条件的订单数量
     */
    public int countOrdersByStatusAndSearch(String status, String search) {
        List<Order> allOrders = orderRepository.findAll();
        
        return (int) allOrders.stream()
                .filter(order -> {
                    boolean statusMatch = status == null || status.trim().isEmpty() || status.equals("ALL") || 
                            order.getStatus().name().equals(status);
                    boolean searchMatch = search == null || search.trim().isEmpty() || 
                            (order.getOrderNo() != null && order.getOrderNo().toLowerCase().contains(search.toLowerCase())) ||
                            (order.getUser() != null && order.getUser().getRealName() != null && 
                             order.getUser().getRealName().toLowerCase().contains(search.toLowerCase())) ||
                            (order.getCourse() != null && order.getCourse().getTitle() != null && 
                             order.getCourse().getTitle().toLowerCase().contains(search.toLowerCase()));
                    return statusMatch && searchMatch;
                })
                .count();
    }
    
    /**
     * 获取订单统计信息
     */
    public OrderStatistics getOrderStatistics() {
        List<Order> allOrders = orderRepository.findAll();
        
        long totalOrders = allOrders.size();
        long paidCount = allOrders.stream().filter(o -> o.getStatus() == Order.OrderStatus.PAID).count();
        long pendingCount = allOrders.stream().filter(o -> o.getStatus() == Order.OrderStatus.PENDING).count();
        long cancelledCount = allOrders.stream().filter(o -> o.getStatus() == Order.OrderStatus.CANCELLED).count();
        long refundedCount = allOrders.stream().filter(o -> o.getStatus() == Order.OrderStatus.REFUNDED).count();
        
        // 计算总收入（已支付订单的金额总和）
        double totalRevenue = allOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.PAID)
                .mapToDouble(o -> o.getFinalAmount().doubleValue())
                .sum();
        
        // 计算支付率（已支付订单数 / 总订单数 * 100）
        double paymentRate = totalOrders > 0 ? (double) paidCount / totalOrders * 100 : 0;
        
        // 计算今日订单数
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        long todayOrders = allOrders.stream()
                .filter(o -> o.getCreateTime().isAfter(todayStart))
                .count();
        
        return new OrderStatistics(totalOrders, paidCount, pendingCount, cancelledCount, 
                                 refundedCount, totalRevenue, paymentRate, todayOrders);
    }
    
    /**
     * 订单统计信息类
     */
    public static class OrderStatistics {
        private final long totalOrders;
        private final long paidCount;
        private final long pendingCount;
        private final long cancelledCount;
        private final long refundedCount;
        private final double totalRevenue;
        private final double paymentRate;
        private final long todayOrders;
        
        public OrderStatistics(long totalOrders, long paidCount, long pendingCount, long cancelledCount,
                             long refundedCount, double totalRevenue, double paymentRate, long todayOrders) {
            this.totalOrders = totalOrders;
            this.paidCount = paidCount;
            this.pendingCount = pendingCount;
            this.cancelledCount = cancelledCount;
            this.refundedCount = refundedCount;
            this.totalRevenue = totalRevenue;
            this.paymentRate = Math.round(paymentRate * 100.0) / 100.0; // 保留两位小数
            this.todayOrders = todayOrders;
        }
        
        public long getTotalOrders() { return totalOrders; }
        public long getPaidCount() { return paidCount; }
        public long getPendingCount() { return pendingCount; }
        public long getCancelledCount() { return cancelledCount; }
        public long getRefundedCount() { return refundedCount; }
        public double getTotalRevenue() { return totalRevenue; }
        public double getPaymentRate() { return paymentRate; }
        public long getTodayOrders() { return todayOrders; }
    }
    
    /**
     * 退款订单
     */
    public boolean refundOrder(String orderNo, String refundReason) {
        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null) {
            return false;
        }

        // 只有已支付的订单才能退款
        if (order.getStatus() != Order.OrderStatus.PAID) {
            return false;
        }

        try {
            // 更新订单状态为已退款
            order.setStatus(Order.OrderStatus.REFUNDED);
            order.setRemark(refundReason != null ? "退款原因: " + refundReason : "管理员退款");
            
            // 更新订单到数据库
            orderRepository.update(order);

            // 如果是余额支付，需要退还余额给用户
            if (order.getPaymentMethod() == Order.PaymentMethod.BALANCE) {
                User user = userService.findById(order.getUserId());
                if (user != null) {
                    double refundAmount = order.getFinalAmount().doubleValue();
                    double oldBalance = user.getBalance();
                    double newBalance = oldBalance + refundAmount;
                    user.setBalance(newBalance);
                    
                    // 保存用户信息
                    User savedUser = userService.save(user);
                    
                    // 验证余额是否更新成功
                    User verifyUser = userService.findById(order.getUserId());
                    if (verifyUser != null && Math.abs(verifyUser.getBalance() - newBalance) < 0.01) {
                        System.out.println("退款成功：用户ID=" + user.getId() + 
                                         ", 退款金额=" + refundAmount + 
                                         ", 原余额=" + oldBalance + 
                                         ", 新余额=" + newBalance);
                    } else {
                        System.err.println("余额更新可能失败：用户ID=" + user.getId() + 
                                         ", 期望余额=" + newBalance + 
                                         ", 实际余额=" + (verifyUser != null ? verifyUser.getBalance() : "null"));
                    }
                } else {
                    System.err.println("退款失败：找不到用户ID=" + order.getUserId());
                }
            } else {
                System.out.println("非余额支付订单退款：订单号=" + orderNo + 
                                 ", 支付方式=" + order.getPaymentMethod());
            }

            // 删除学习记录（如果存在）
            try {
                learnService.deleteLearnRecord(order.getUserId(), order.getCourseId());
            } catch (Exception e) {
                // 忽略删除学习记录的错误，不影响退款流程
                System.err.println("删除学习记录失败: " + e.getMessage());
            }

            return true;
        } catch (Exception e) {
            System.err.println("退款处理失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 取消订单
     */
    public boolean cancelOrder(String orderNo, String cancelReason) {
        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null) {
            return false;
        }

        // 只有待支付的订单才能取消
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            return false;
        }

        try {
            // 更新订单状态为已取消
            order.setStatus(Order.OrderStatus.CANCELLED);
            order.setRemark(cancelReason != null ? "取消原因: " + cancelReason : "管理员取消");
            
            // 更新订单到数据库
            orderRepository.update(order);
            return true;
        } catch (Exception e) {
            System.err.println("取消订单失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 自动关闭过期订单的定时任务
     * 每分钟执行一次，关闭15分钟前创建且仍未支付的订单
     */
    @Scheduled(fixedRate = 60000) // 每分钟执行一次（60000毫秒）
    public void autoCloseExpiredOrders() {
        try {
            // 计算15分钟前的时间
            LocalDateTime expireTime = LocalDateTime.now().minusMinutes(15);
            
            // 查询过期的待支付订单
            List<Order> expiredOrders = orderRepository.findPendingOrdersBeforeTime(expireTime);
            
            // 批量关闭过期订单
            for (Order order : expiredOrders) {
                order.setStatus(Order.OrderStatus.CANCELLED);
                orderRepository.update(order);
                System.out.println("自动关闭过期订单: " + order.getOrderNo() + 
                                 ", 创建时间: " + order.getCreateTime() + 
                                 ", 课程: " + (order.getCourse() != null ? order.getCourse().getTitle() : "未知"));
            }
            
            // 输出执行结果
            if (!expiredOrders.isEmpty()) {
                System.out.println("定时任务执行完成，本次自动关闭了 " + expiredOrders.size() + " 个过期订单");
            }
            
        } catch (Exception e) {
            System.err.println("自动关闭过期订单失败【首次启动请到首页初始化数据库！】: " + e.getMessage());
            e.printStackTrace();
        }
    }
}