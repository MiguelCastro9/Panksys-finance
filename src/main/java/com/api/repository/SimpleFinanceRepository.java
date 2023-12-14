package com.api.repository;

import com.api.model.SimpleFinanceModel;
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
    
    @Query(value = "SELECT user_id FROM simple_finances WHERE id = :id", nativeQuery = true)
    public Long getUserId(@Param("id") Long id);
}
