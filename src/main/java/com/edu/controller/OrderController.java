package com.edu.controller;

import com.edu.entity.Order;
import com.edu.entity.User;
import com.edu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2025 八方网域-无涯老师. All rights reserved.
 * 作者：八方网域-无涯老师
 * 创建时间：2024-09
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 创建订单 - 支付逻辑漏洞
     */
    @PostMapping("/create")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return response;
        }
        
        try {
            Long courseId = ((Number) request.get("courseId")).longValue();
            
            // 支付逻辑漏洞：直接使用客户端传来的价格和参数
            BigDecimal clientPrice = new BigDecimal(request.get("price").toString());
            Integer quantity = request.containsKey("quantity") ? 
                ((Number) request.get("quantity")).intValue() : 1;
            BigDecimal discount = request.containsKey("discount") ? 
                new BigDecimal(request.get("discount").toString()) : BigDecimal.ZERO;
            BigDecimal shippingFee = request.containsKey("shippingFee") ? 
                new BigDecimal(request.get("shippingFee").toString()) : BigDecimal.ZERO;
            
            Order order = orderService.createOrder(currentUser.getId(), courseId, 
                clientPrice, quantity, discount, shippingFee);
            
            response.put("success", true);
            response.put("message", "订单创建成功");
            response.put("order", order);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "创建订单失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 支付订单
     */
    @PostMapping("/{orderNo}/pay")
    public Map<String, Object> payOrder(@PathVariable String orderNo, 
                                       @RequestBody Map<String, String> request, 
                                       HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return response;
        }
        
        String paymentMethod = request.get("paymentMethod");
        Order.PaymentMethod method = Order.PaymentMethod.valueOf(paymentMethod);
        
        boolean success = orderService.payOrder(orderNo, method);
        
        if (success) {
            response.put("success", true);
            response.put("message", "支付成功");
        } else {
            response.put("success", false);
            response.put("message", "支付失败");
        }
        
        return response;
    }
    
    /**
     * 获取用户订单列表
     */
    @GetMapping("/my")
    public Map<String, Object> getMyOrders(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "请先登录");
            return response;
        }
        
        List<Order> orders = orderService.getOrdersByUser(currentUser);
        
        response.put("success", true);
        response.put("orders", orders);
        
        return response;
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/{orderNo}")
    public Map<String, Object> getOrderDetail(@PathVariable String orderNo, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // 漏洞：不验证订单是否属于当前用户
        Order order = orderService.findByOrderNo(orderNo);
        
        if (order != null) {
            response.put("success", true);
            response.put("order", order);
        } else {
            response.put("success", false);
            response.put("message", "订单不存在");
        }
        
        return response;
    }
    
    /**
     * 获取所有订单 - 未授权访问
     */
    @GetMapping("/all")
    public Map<String, Object> getAllOrders() {
        Map<String, Object> response = new HashMap<>();
        
        // 漏洞：接口未鉴权，任何人都可以查看所有订单
        List<Order> orders = orderService.findAll();
        
        response.put("success", true);
        response.put("orders", orders);
        response.put("count", orders.size());
        
        return response;
    }
    
    /**
     * 修改订单金额 - 支付逻辑漏洞
     */
    @PutMapping("/{orderNo}/amount")
    public Map<String, Object> updateOrderAmount(@PathVariable String orderNo, 
                                                @RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        // 漏洞：允许修改订单金额
        Order order = orderService.findByOrderNo(orderNo);
        if (order == null) {
            response.put("success", false);
            response.put("message", "订单不存在");
            return response;
        }
        
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            response.put("success", false);
            response.put("message", "订单状态不允许修改");
            return response;
        }
        
        if (request.containsKey("finalAmount")) {
            BigDecimal newAmount = new BigDecimal(request.get("finalAmount").toString());
            order.setFinalAmount(newAmount);
            // 直接保存修改后的金额，不重新计算
            orderService.save(order);
        }
        
        if (request.containsKey("discountAmount")) {
            BigDecimal discount = new BigDecimal(request.get("discountAmount").toString());
            order.setDiscountAmount(discount);
        }
        
        if (request.containsKey("shippingFee")) {
            BigDecimal shippingFee = new BigDecimal(request.get("shippingFee").toString());
            order.setShippingFee(shippingFee);
        }
        
        // 如果只修改了finalAmount，直接使用修改后的值，不重新计算
        if (request.containsKey("finalAmount") && request.size() == 1) {
            // 直接使用客户端传来的金额
        } else {
            // 重新计算最终金额（仍然存在漏洞）
            BigDecimal finalAmount = order.getOriginalAmount()
                    .multiply(BigDecimal.valueOf(order.getQuantity()))
                    .subtract(order.getDiscountAmount())
                    .add(order.getShippingFee());
            order.setFinalAmount(finalAmount);
            orderService.save(order);
        }
        
        response.put("success", true);
        response.put("message", "订单金额修改成功");
        response.put("order", order);
        
        return response;
    }
}