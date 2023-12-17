package com.api.service;

import com.api.model.SimpleFinanceInstallmentModel;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceInstallmentService {
    
    SimpleFinanceInstallmentModel update(Long id, SimpleFinanceInstallmentModel simpleFinanceInstallmentModel);
    
    List<SimpleFinanceInstallmentModel> list(Long simpleFinanceId);
    
    Optional<SimpleFinanceInstallmentModel> find(Long id);
}
