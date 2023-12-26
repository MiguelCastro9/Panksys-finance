package com.api.repository;

import com.api.model.FeedbackModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Miguel Castro
 */
@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {
    
    @Query(value = "SELECT * FROM feedbacks WHERE enabled = true", nativeQuery = true)
    public List<FeedbackModel> findAllFeedbacks();
    
    @Query(value = "SELECT * FROM feedbacks WHERE total_stars = :total_stars AND enabled = true", nativeQuery = true)
    public List<FeedbackModel> find(@Param("total_stars") Integer totalStars);
    
    @Query(value = "SELECT * FROM feedbacks WHERE user_id = :user_id AND enabled = true", nativeQuery = true)
    public List<FeedbackModel> myFeedbacks(@Param("user_id") Long userId);
    
    @Query(value = "SELECT * FROM feedbacks WHERE id = :id AND enabled = true", nativeQuery = true)
    public Optional<FeedbackModel> getFeedback(@Param("id") Long id);
}
