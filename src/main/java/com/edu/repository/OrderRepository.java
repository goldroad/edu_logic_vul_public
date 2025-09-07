package com.edu.repository;

import com.edu.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface OrderRepository {
    
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "user", column = "user_id", 
                one = @One(select = "com.edu.repository.UserRepository.findById")),
        @Result(property = "course", column = "course_id", 
                one = @One(select = "com.edu.repository.CourseRepository.findById"))
    })
    Order findById(Long id);
    
    @Select("SELECT * FROM `order` WHERE order_no = #{orderNo}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "user", column = "user_id", 
                one = @One(select = "com.edu.repository.UserRepository.findById")),
        @Result(property = "course", column = "course_id", 
                one = @One(select = "com.edu.repository.CourseRepository.findById"))
    })
    Order findByOrderNo(String orderNo);
    
    @Select("SELECT * FROM `order` WHERE user_id = #{userId}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "user", column = "user_id", 
                one = @One(select = "com.edu.repository.UserRepository.findById")),
        @Result(property = "course", column = "course_id", 
                one = @One(select = "com.edu.repository.CourseRepository.findById"))
    })
    List<Order> findByUserId(Long userId);
    
    @Select("SELECT * FROM `order` WHERE status = #{status}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "user", column = "user_id", 
                one = @One(select = "com.edu.repository.UserRepository.findById")),
        @Result(property = "course", column = "course_id", 
                one = @One(select = "com.edu.repository.CourseRepository.findById"))
    })
    List<Order> findByStatus(String status);
    
    @Select("SELECT * FROM `order` WHERE user_id = #{userId} AND status = #{status}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "user", column = "user_id", 
                one = @One(select = "com.edu.repository.UserRepository.findById")),
        @Result(property = "course", column = "course_id", 
                one = @One(select = "com.edu.repository.CourseRepository.findById"))
    })
    List<Order> findByUserIdAndStatus(Long userId, String status);
    
    @Select("SELECT * FROM `order`")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "user", column = "user_id", 
                one = @One(select = "com.edu.repository.UserRepository.findById")),
        @Result(property = "course", column = "course_id", 
                one = @One(select = "com.edu.repository.CourseRepository.findById"))
    })
    List<Order> findAll();
    
    @Insert("INSERT INTO `order`(order_no, user_id, course_id, original_amount, discount_amount, shipping_fee, final_amount, quantity, status, payment_method, payment_transaction_id, create_time, pay_time, remark) " +
            "VALUES(#{orderNo}, #{userId}, #{courseId}, #{originalAmount}, #{discountAmount}, #{shippingFee}, #{finalAmount}, #{quantity}, #{status}, #{paymentMethod}, #{paymentTransactionId}, #{createTime}, #{payTime}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Order order);
    
    @Update("UPDATE `order` SET order_no=#{orderNo}, user_id=#{userId}, course_id=#{courseId}, original_amount=#{originalAmount}, " +
            "discount_amount=#{discountAmount}, shipping_fee=#{shippingFee}, final_amount=#{finalAmount}, quantity=#{quantity}, " +
            "status=#{status}, payment_method=#{paymentMethod}, payment_transaction_id=#{paymentTransactionId}, " +
            "pay_time=#{payTime}, remark=#{remark} WHERE id=#{id}")
    int update(Order order);
    
    @Delete("DELETE FROM `order` WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 查询用户已支付的课程ID列表
     */
    @Select("SELECT DISTINCT course_id FROM `order` WHERE user_id = #{userId} AND status = 'PAID'")
    List<Long> findPaidCourseIdsByUserId(Long userId);
    
    /**
     * 统计总订单数
     */
    @Select("SELECT COUNT(*) FROM `order`")
    long countTotalOrders();
    
    /**
     * 统计总收入（已支付订单）
     */
    @Select("SELECT COALESCE(SUM(final_amount), 0) FROM `order` WHERE status = 'PAID'")
    double getTotalRevenue();
}