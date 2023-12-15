package com.api.repository;

import com.api.model.SimpleFinanceModel;
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
public interface SimpleFinanceRepository extends JpaRepository<SimpleFinanceModel, Long> {
    
    @Query(value = "SELECT user_id FROM simple_finances WHERE id = :id AND enabled = 1", nativeQuery = true)
    public Long getUserId(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM simple_finances WHERE user_id = :user_id AND enabled = 1", nativeQuery = true)
    public List<SimpleFinanceModel> list(@Param("user_id") Long user_id);
}
