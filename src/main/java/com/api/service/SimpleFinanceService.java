package com.api.service;

import com.api.model.SimpleFinanceModel;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Miguel Castro
 */
public interface SimpleFinanceService {
    
    public SimpleFinanceModel save(SimpleFinanceModel simpleFinanceModel);
    
    public SimpleFinanceModel update(Long id, SimpleFinanceModel simpleFinanceModel);
    
    public List<SimpleFinanceModel> list();
    
    public Optional<SimpleFinanceModel> find(Long id);
    
    public SimpleFinanceModel disabled(Long id);
    
    public Integer getTotalByFormPaymentMoney();
    
    public Integer getTotalByFormPaymentDebit();
    
    public Integer getTotalByFormPaymentCredit();
    
    public Integer getTotalByMonthJanuary();
    
    public Integer getTotalByMonthFebruary();
    
    public Integer getTotalByMonthMarch();
    
    public Integer getTotalByMonthApril();
    
    public Integer getTotalByMonthMay();
    
    public Integer getTotalByMonthJune();
    
    public Integer getTotalByMonthJuly();
    
    public Integer getTotalByMonthAugust();
    
    public Integer getTotalByMonthSeptember();
    
    public Integer getTotalByMonthOctober();
    
    public Integer getTotalByMonthNovember();
    
    public Integer getTotalByMonthDecember();
}
