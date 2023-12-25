package com.api.repository;

import com.api.model.FeedbackModel;
import java.util.List;
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
    
    @Query(value = "SELECT * FROM feedbacks WHERE total_stars = :total_stars", nativeQuery = true)
    public List<FeedbackModel> find(@Param("total_stars") Integer totalStars);
}
