package com.api.service;

import com.api.model.SimpleFinanceModel;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceService {
    
    SimpleFinanceModel save(SimpleFinanceModel simpleFinanceModel);
    
    SimpleFinanceModel update(Long id, SimpleFinanceModel simpleFinanceModel);
    
    List<SimpleFinanceModel> list();
    
    Optional<SimpleFinanceModel> find(Long id);
    
    SimpleFinanceModel disabled(Long id);
    
    void deleteAll();
}
