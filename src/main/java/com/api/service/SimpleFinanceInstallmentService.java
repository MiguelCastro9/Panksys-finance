package com.api.service;

import com.api.model.SimpleFinanceInstallmentModel;
import java.util.List;

/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceInstallmentService {
    
    SimpleFinanceInstallmentModel update(Long id, SimpleFinanceInstallmentModel simpleFinanceInstallmentModel);
    
    List<SimpleFinanceInstallmentModel> list();
    
}
