package com.api.repository;

import com.api.enums.FormPaymentEnum;
import com.api.model.SimpleFinanceModel;
import java.time.LocalDate;
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
    public Optional<SimpleFinanceModel> findSimpleFinance(@Param("id") Long id);

@Query(value = "SELECT " +
               "sf.id, sf.name, sf.total_value, sf.form_payment, sf.month_payment, sf.total_installment, sf.description, " +
               "CASE WHEN COUNT(sfi.id) = COUNT(CASE WHEN sfi.status_payment = 'PAID' THEN 1 END) THEN 'INSTALLMENTS PAID' ELSE 'INSTALLMENTS NOT PAID' END as all_status_payment, " +
               "sf.user_id, sf.enabled, sf.created_date, sf.updated_date " +
               "FROM simple_finances sf " +
               "LEFT JOIN simple_finance_installments sfi ON sf.id = sfi.simple_finance_id " +
               "WHERE sf.user_id = :user_id AND sf.enabled = true " +
               "GROUP BY sf.id, sf.name, sf.total_value, sf.form_payment, sf.month_payment, sf.total_installment, sf.description, sf.user_id, sf.enabled, sf.created_date, sf.updated_date",
               nativeQuery = true)
public List<SimpleFinanceModel> findAllSimpleFinances(@Param("user_id") Long userId);

    @Query(value = "SELECT * FROM simple_finances WHERE (name = :name"
            + " OR form_payment = :form_payment"
            + " OR month_payment = :month_payment"
            + " OR total_installment = :total_installment)"
            + " AND user_id = :user_id"
            + " AND enabled = true", nativeQuery = true)
    public List<SimpleFinanceModel> filterSimpleFinances(
            @Param("name") String name,
            @Param("form_payment") FormPaymentEnum formPayment,
            @Param("month_payment") LocalDate monthPayment,
            @Param("total_installment") Integer totalInstallment,
            @Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE form_payment = 'MONEY'"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByFormPaymentMoney(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE form_payment = 'DEBIT'"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByFormPaymentDebit(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE form_payment = 'CREDIT'"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByFormPaymentCredit(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 1"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthJanuary(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 2"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthFebruary(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 3"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthMarch(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 4"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthApril(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 5"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthMay(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 6"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthJune(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 7"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthJuly(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 8"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthAugust(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 9"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthSeptember(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 10"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthOctober(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 11"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthNovember(@Param("user_id") Long userId);

    @Query(value = "SELECT COUNT(*) FROM simple_finances WHERE MONTH(month_payment) = 12"
            + " AND user_id = :user_id AND enabled = true", nativeQuery = true)
    public Integer getTotalByMonthDecember(@Param("user_id") Long userId);
}
