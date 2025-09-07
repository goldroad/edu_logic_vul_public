package com.edu.service;

import com.edu.entity.Course;
import com.edu.entity.User;
import com.edu.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public Course createCourse(String title, String description, BigDecimal price, User teacher) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setOriginalPrice(price);
        course.setTeacherId(teacher.getId());
        course.setTeacher(teacher);
        course.setStatus(Course.CourseStatus.DRAFT);
        course.setStudentCount(0);
        course.setCreateTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());
        
        courseRepository.save(course);
        return course;
    }
    
    public List<Course> getPublishedCourses() {
        return courseRepository.findByStatus("PUBLISHED");
    }
    
    public List<Course> getCoursesByTeacher(User teacher) {
        return courseRepository.findByTeacherId(teacher.getId());
    }
    
    public List<Course> searchCourses(String keyword) {
        return courseRepository.findByTitleContaining(keyword);
    }
    
    public Course findById(Long id) {
        return courseRepository.findById(id);
    }
    
    public Course save(Course course) {
        course.setUpdateTime(LocalDateTime.now());
        if (course.getId() == null) {
            courseRepository.save(course);
        } else {
            courseRepository.update(course);
        }
        return course;
    }
    
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
    
    public void publishCourse(Long courseId) {
        Course course = courseRepository.findById(courseId);
        if (course != null) {
            course.setStatus(Course.CourseStatus.PUBLISHED);
            courseRepository.update(course);
        }
    }
    
    /**
     * 分页查询课程
     */
    public CoursePageResult getCoursesByPage(int page, int size, String status, String search) {
        int offset = (page - 1) * size;
        List<Course> courses;
        long totalCount;
        
        if (search != null && !search.trim().isEmpty()) {
            courses = courseRepository.findByTitleContainingWithPagination(search.trim(), offset, size);
            totalCount = courseRepository.findByTitleContaining(search.trim()).size();
        } else if (status != null && !status.equals("ALL")) {
            courses = courseRepository.findByStatusWithPagination(status, offset, size);
            totalCount = courseRepository.countByStatus(status);
        } else {
            courses = courseRepository.findWithPagination(offset, size);
            totalCount = courseRepository.countTotalCourses();
        }
        
        int totalPages = (int) Math.ceil((double) totalCount / size);
        
        return new CoursePageResult(courses, totalCount, totalPages, page, size);
    }
    
    /**
     * 获取课程统计信息
     */
    public CourseStatistics getCourseStatistics() {
        long totalCourses = courseRepository.countTotalCourses();
        long publishedCount = courseRepository.countByStatus("PUBLISHED");
        long draftCount = courseRepository.countByStatus("DRAFT");
        
        // 计算总学员数（这里简化处理，实际应该从订单表统计）
        long totalStudents = publishedCount * 50; // 模拟数据
        
        return new CourseStatistics(totalCourses, publishedCount, draftCount, totalStudents);
    }
    
    /**
     * 更新课程状态
     */
    public boolean updateCourseStatus(Long courseId, Course.CourseStatus status) {
        Course course = courseRepository.findById(courseId);
        if (course != null) {
            course.setStatus(status);
            course.setUpdateTime(LocalDateTime.now());
            courseRepository.update(course);
            return true;
        }
        return false;
    }
    
    /**
     * 删除课程
     */
    public boolean deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId);
        if (course != null) {
            courseRepository.deleteById(courseId);
            return true;
        }
        return false;
    }
    
    /**
     * 更新课程信息
     */
    public boolean updateCourse(Long courseId, String title, String description, BigDecimal price) {
        Course course = courseRepository.findById(courseId);
        if (course != null) {
            if (title != null && !title.trim().isEmpty()) {
                course.setTitle(title);
            }
            if (description != null && !description.trim().isEmpty()) {
                course.setDescription(description);
            }
            if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
                course.setPrice(price);
            }
            course.setUpdateTime(LocalDateTime.now());
            courseRepository.update(course);
            return true;
        }
        return false;
    }
    
    /**
     * 课程分页结果类
     */
    public static class CoursePageResult {
        private final List<Course> courses;
        private final long totalCount;
        private final int totalPages;
        private final int currentPage;
        private final int pageSize;
        
        public CoursePageResult(List<Course> courses, long totalCount, int totalPages, int currentPage, int pageSize) {
            this.courses = courses;
            this.totalCount = totalCount;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }
        
        public List<Course> getCourses() { return courses; }
        public long getTotalCount() { return totalCount; }
        public int getTotalPages() { return totalPages; }
        public int getCurrentPage() { return currentPage; }
        public int getPageSize() { return pageSize; }
        public boolean isHasPrevious() { return currentPage > 1; }
        public boolean isHasNext() { return currentPage < totalPages; }
    }
    
    /**
     * 课程统计信息类
     */
    public static class CourseStatistics {
        private final long totalCourses;
        private final long publishedCount;
        private final long draftCount;
        private final long totalStudents;
        
        public CourseStatistics(long totalCourses, long publishedCount, long draftCount, long totalStudents) {
            this.totalCourses = totalCourses;
            this.publishedCount = publishedCount;
            this.draftCount = draftCount;
            this.totalStudents = totalStudents;
        }
        
        public long getTotalCourses() { return totalCourses; }
        public long getPublishedCount() { return publishedCount; }
        public long getDraftCount() { return draftCount; }
        public long getTotalStudents() { return totalStudents; }
    }
}