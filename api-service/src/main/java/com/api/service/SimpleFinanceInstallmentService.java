package com.api.service;

import com.api.model.SimpleFinanceInstallmentModel;
import java.util.List;
/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceInstallmentService {
    
    public SimpleFinanceInstallmentModel update(Long id, SimpleFinanceInstallmentModel simpleFinanceInstallmentModel);
    
    public List<SimpleFinanceInstallmentModel> findAllSimpleFinanceInstallments(Long simpleFinanceId);
}
