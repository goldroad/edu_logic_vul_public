package com.edu.repository;

import com.edu.entity.Learn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LearnRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Learn> learnRowMapper = new RowMapper<Learn>() {
        @Override
        public Learn mapRow(ResultSet rs, int rowNum) throws SQLException {
            Learn learn = new Learn();
            learn.setId(rs.getLong("id"));
            learn.setUserId(rs.getLong("user_id"));
            learn.setCourseId(rs.getLong("course_id"));
            learn.setProgress(rs.getInt("progress"));
            learn.setStatus(Learn.LearnStatus.valueOf(rs.getString("status")));
            
            if (rs.getTimestamp("start_time") != null) {
                learn.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
            }
            if (rs.getTimestamp("last_study_time") != null) {
                learn.setLastStudyTime(rs.getTimestamp("last_study_time").toLocalDateTime());
            }
            if (rs.getTimestamp("complete_time") != null) {
                learn.setCompleteTime(rs.getTimestamp("complete_time").toLocalDateTime());
            }
            if (rs.getTimestamp("create_time") != null) {
                learn.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
            }
            if (rs.getTimestamp("update_time") != null) {
                learn.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            }
            
            return learn;
        }
    };
    
    public Learn save(Learn learn) {
        if (learn.getId() == null) {
            return insert(learn);
        } else {
            return update(learn);
        }
    }
    
    private Learn insert(Learn learn) {
        String sql = "INSERT INTO learn (user_id, course_id, progress, status, start_time, last_study_time, complete_time, create_time, update_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, learn.getUserId());
            ps.setLong(2, learn.getCourseId());
            ps.setInt(3, learn.getProgress());
            ps.setString(4, learn.getStatus().name());
            ps.setObject(5, learn.getStartTime());
            ps.setObject(6, learn.getLastStudyTime());
            ps.setObject(7, learn.getCompleteTime());
            ps.setObject(8, learn.getCreateTime());
            ps.setObject(9, learn.getUpdateTime());
            return ps;
        }, keyHolder);
        
        learn.setId(keyHolder.getKey().longValue());
        return learn;
    }
    
    private Learn update(Learn learn) {
        String sql = "UPDATE learn SET progress = ?, status = ?, start_time = ?, last_study_time = ?, " +
                    "complete_time = ?, update_time = ? WHERE id = ?";
        
        learn.setUpdateTime(LocalDateTime.now());
        
        jdbcTemplate.update(sql, 
            learn.getProgress(),
            learn.getStatus().name(),
            learn.getStartTime(),
            learn.getLastStudyTime(),
            learn.getCompleteTime(),
            learn.getUpdateTime(),
            learn.getId()
        );
        
        return learn;
    }
    
    public Learn findById(Long id) {
        String sql = "SELECT * FROM learn WHERE id = ?";
        List<Learn> results = jdbcTemplate.query(sql, learnRowMapper, id);
        return results.isEmpty() ? null : results.get(0);
    }
    
    public List<Learn> findByUserId(Long userId) {
        String sql = "SELECT * FROM learn WHERE user_id = ? ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, learnRowMapper, userId);
    }
    
    public List<Learn> findByUserIdAndStatus(Long userId, Learn.LearnStatus status) {
        String sql = "SELECT * FROM learn WHERE user_id = ? AND status = ? ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, learnRowMapper, userId, status.name());
    }
    
    public Learn findByUserIdAndCourseId(Long userId, Long courseId) {
        String sql = "SELECT * FROM learn WHERE user_id = ? AND course_id = ?";
        List<Learn> results = jdbcTemplate.query(sql, learnRowMapper, userId, courseId);
        return results.isEmpty() ? null : results.get(0);
    }
    
    public void deleteById(Long id) {
        String sql = "DELETE FROM learn WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public List<Learn> findAll() {
        String sql = "SELECT * FROM learn ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, learnRowMapper);
    }
    
    // 统计方法
    public int countByUserIdAndStatus(Long userId, Learn.LearnStatus status) {
        String sql = "SELECT COUNT(*) FROM learn WHERE user_id = ? AND status = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId, status.name());
    }
    
    public int countByUserId(Long userId) {
        String sql = "SELECT COUNT(*) FROM learn WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }
}