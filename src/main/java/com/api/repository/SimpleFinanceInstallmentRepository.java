package com.api.repository;

import com.api.model.SimpleFinanceInstallmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceInstallmentRepository extends JpaRepository<SimpleFinanceInstallmentModel, Long> {
    
    
    @Query(value = "SELECT simple_finance_id FROM simple_finance_installments WHERE id = :id", nativeQuery = true)
    public Long getSimpleFinanceId(@Param("id") Long id);
}
