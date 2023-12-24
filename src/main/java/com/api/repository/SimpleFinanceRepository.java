package com.api.repository;

import com.api.model.SimpleFinanceModel;
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
public interface SimpleFinanceRepository extends JpaRepository<SimpleFinanceModel, Long> {
    
    @Query(value = "SELECT * FROM simple_finances WHERE id = :id AND enabled = true", nativeQuery = true)
    public Optional<SimpleFinanceModel> getSimpleFinance(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM simple_finances WHERE user_id = :user_id AND enabled = true", nativeQuery = true)
    public List<SimpleFinanceModel> list(@Param("user_id") Long userId);
}
