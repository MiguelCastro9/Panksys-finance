package com.api.repository;

import com.api.model.SimpleFinanceInstallmentModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceInstallmentRepository extends JpaRepository<SimpleFinanceInstallmentModel, Long> {
    
    @Query(value = "SELECT * FROM simple_finance_installments WHERE simple_finance_id = :simple_finance_id", nativeQuery = true)
    public List<SimpleFinanceInstallmentModel> list(@Param("simple_finance_id") Long simpleFinanceId);
}
