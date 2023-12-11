package com.api.repository;

import com.api.model.SimpleFinanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Miguel Castro
 */
@Repository
public interface SimpleFinanceRepository extends JpaRepository<SimpleFinanceModel, Long> {
}
