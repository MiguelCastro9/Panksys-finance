package com.api.repository;

import com.api.model.SimpleFinanceInstallmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceInstallmentRepository extends JpaRepository<SimpleFinanceInstallmentModel, Long> {
    
}
