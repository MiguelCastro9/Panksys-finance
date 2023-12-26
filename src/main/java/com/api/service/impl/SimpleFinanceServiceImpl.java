package com.api.service.impl;

import com.api.enums.FormPaymentEnum;
import com.api.enums.StatusPaymentEnum;
import com.api.model.NotificationModel;
import com.api.model.SimpleFinanceInstallmentModel;
import com.api.model.SimpleFinanceModel;
import com.api.model.UserModel;
import com.api.repository.NotificationRepository;
import com.api.repository.SimpleFinanceInstallmentRepository;
import com.api.repository.SimpleFinanceRepository;
import com.api.service.SimpleFinanceService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miguel Castro
 */
@Service
@Primary
public class SimpleFinanceServiceImpl implements SimpleFinanceService {

    @Autowired
    private SimpleFinanceRepository simpleFinanceRepository;

    @Autowired
    private SimpleFinanceInstallmentRepository simpleFinanceInstallmentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public SimpleFinanceModel save(SimpleFinanceModel simpleFinanceModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        SimpleFinanceModel simpleFinanceBuilder = new SimpleFinanceModel.Builder()
                .setName(simpleFinanceModel.getName())
                .setTotalValue(simpleFinanceModel.getTotal_value())
                .setForm_payment(simpleFinanceModel.getForm_payment())
                .setMonth_payment(simpleFinanceModel.getMonth_payment())
                .setTotal_installment(simpleFinanceModel.getTotal_installment())
                .setDescription(simpleFinanceModel.getDescription())
                .setAll_status_payment(simpleFinanceModel.getAll_status_payment())
                .setUser(userAuthenticated)
                .setEnabled(true)
                .setCreated_date(LocalDateTime.now())
                .setUpdated_date(LocalDateTime.now())
                .build();
        if (simpleFinanceBuilder.getTotal_installment() > 1
                && simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.MONEY)) {
            throw new IllegalArgumentException("For DEBIT or MONEY payment methods, the installment must be equal to 1.");
        }

        if (simpleFinanceBuilder.getTotal_installment() > 1
                && simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.DEBIT)) {
            throw new IllegalArgumentException("For DEBIT or MONEY payment methods, the installment must be equal to 1.");
        }
        simpleFinanceRepository.save(simpleFinanceBuilder);

        if ((simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.DEBIT)
                || simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.MONEY))
                && simpleFinanceBuilder.getAll_status_payment().equals(StatusPaymentEnum.PAID)) {
            saveSimpleFinanceInstallmentForDebitOrMoney(simpleFinanceBuilder, StatusPaymentEnum.PAID);
        }

        if ((simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.DEBIT)
                || simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.MONEY))
                && simpleFinanceBuilder.getAll_status_payment().equals(StatusPaymentEnum.PENDING)) {
            saveSimpleFinanceInstallmentForDebitOrMoney(simpleFinanceBuilder, StatusPaymentEnum.PENDING);
        }

        if ((simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.DEBIT)
                || simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.MONEY))
                && simpleFinanceBuilder.getAll_status_payment().equals(StatusPaymentEnum.LATE)) {
            saveSimpleFinanceInstallmentForDebitOrMoney(simpleFinanceBuilder, StatusPaymentEnum.LATE);
        }

        if ((simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.CREDIT))
                && simpleFinanceBuilder.getAll_status_payment().equals(StatusPaymentEnum.PAID)) {
            saveSimpleFinanceInstallmentForCredit(simpleFinanceBuilder, StatusPaymentEnum.PAID);
        }

        if ((simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.CREDIT))
                && simpleFinanceBuilder.getAll_status_payment().equals(StatusPaymentEnum.PENDING)) {
            saveSimpleFinanceInstallmentForCredit(simpleFinanceBuilder, StatusPaymentEnum.PENDING);
        }

        if ((simpleFinanceBuilder.getForm_payment().equals(FormPaymentEnum.CREDIT))
                && simpleFinanceBuilder.getAll_status_payment().equals(StatusPaymentEnum.LATE)) {
            saveSimpleFinanceInstallmentForCredit(simpleFinanceBuilder, StatusPaymentEnum.LATE);
        }
        NotificationModel notificationModel = new NotificationModel.Builder()
                .setUser(userAuthenticated.getId())
                .setDescription("Your new simple finance was saved with success.")
                .setCreated_date(LocalDateTime.now())
                .build();
        notificationRepository.save(notificationModel);
        return simpleFinanceBuilder;
    }

    @Override
    public SimpleFinanceModel update(Long id, SimpleFinanceModel simpleFinanceModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        Optional<SimpleFinanceModel> getSimpleFinance = simpleFinanceRepository.findSimpleFinance(id);
        if (getSimpleFinance.isEmpty()) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (getSimpleFinance.get().getUser().getId() == null) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (!getSimpleFinance.get().getUser().getId().equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finances.");
        }
        return simpleFinanceRepository.findById(id)
                .map(existingSimpleFinance -> {
                    SimpleFinanceModel simpleFinanceBuider = new SimpleFinanceModel.Builder()
                            .setId(existingSimpleFinance.getId())
                            .setName(simpleFinanceModel.getName())
                            .setTotalValue(existingSimpleFinance.getTotal_value())
                            .setForm_payment(existingSimpleFinance.getForm_payment())
                            .setMonth_payment(existingSimpleFinance.getMonth_payment())
                            .setTotal_installment(existingSimpleFinance.getTotal_installment())
                            .setDescription(simpleFinanceModel.getDescription())
                            .setAll_status_payment(existingSimpleFinance.getAll_status_payment())
                            .setUser(existingSimpleFinance.getUser())
                            .setEnabled(existingSimpleFinance.isEnabled())
                            .setCreated_date(existingSimpleFinance.getCreated_date())
                            .setUpdated_date(LocalDateTime.now())
                            .build();
                    return simpleFinanceRepository.save(simpleFinanceBuider);
                })
                .orElseThrow(() -> new IllegalArgumentException("Simple finance don't exists."));
    }

    @Override
    public List<SimpleFinanceModel> findAllSimpleFinances() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.findAllSimpleFinances(userAuthenticated.getId())
                .stream()
                .sorted(Comparator.comparing(SimpleFinanceModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SimpleFinanceModel> filterSimpleFinances(String name, FormPaymentEnum formPayment, 
            LocalDate monthPayment, Integer totalInstallment) {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.filterSimpleFinances(name, formPayment, monthPayment, totalInstallment, userAuthenticated.getId())
                .stream()
                .sorted(Comparator.comparing(SimpleFinanceModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SimpleFinanceModel> findSimpleFinance(Long id) {
        UserModel userAuthenticated = getUserAuthenticated();
        Optional<SimpleFinanceModel> getSimpleFinance = simpleFinanceRepository.findSimpleFinance(id);
        if (getSimpleFinance.isEmpty()) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (getSimpleFinance.get().getUser().getId() == null) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (!getSimpleFinance.get().getUser().getId().equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to search for simple finances from other users.");
        }
        return simpleFinanceRepository.findById(id);
    }

    @Override
    public SimpleFinanceModel disabled(Long id) {
        UserModel userAuthenticated = getUserAuthenticated();
        Optional<SimpleFinanceModel> getSimpleFinance = simpleFinanceRepository.findSimpleFinance(id);
        if (getSimpleFinance.isEmpty()) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (getSimpleFinance.get().getUser().getId() == null) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (!getSimpleFinance.get().getUser().getId().equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finances.");
        }
        return simpleFinanceRepository.findById(id)
                .map(existingSimpleFinance -> {
                    SimpleFinanceModel simpleFinanceBuilder = new SimpleFinanceModel.Builder()
                            .setId(existingSimpleFinance.getId())
                            .setName(existingSimpleFinance.getName())
                            .setTotalValue(existingSimpleFinance.getTotal_value())
                            .setForm_payment(existingSimpleFinance.getForm_payment())
                            .setMonth_payment(existingSimpleFinance.getMonth_payment())
                            .setTotal_installment(existingSimpleFinance.getTotal_installment())
                            .setDescription(existingSimpleFinance.getDescription())
                            .setAll_status_payment(existingSimpleFinance.getAll_status_payment())
                            .setUser(userAuthenticated)
                            .setEnabled(false)
                            .setCreated_date(existingSimpleFinance.getCreated_date())
                            .setUpdated_date(LocalDateTime.now())
                            .build();
                    return simpleFinanceRepository.save(simpleFinanceBuilder);
                })
                .orElseThrow(() -> new IllegalArgumentException("Simple finance don't exists."));
    }

    @Override
    public Integer getTotalByFormPaymentMoney() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByFormPaymentMoney(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByFormPaymentDebit() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByFormPaymentDebit(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByFormPaymentCredit() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByFormPaymentCredit(userAuthenticated.getId());
    }

    private UserModel getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel userAuthenticated = (UserModel) authentication.getPrincipal();
        if (authentication.getPrincipal() instanceof UserDetails) {
            if (!userAuthenticated.isEnabled()) {
                throw new IllegalArgumentException("Your user is disabled.");
            }
        } else {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        return userAuthenticated;
    }

    private void saveSimpleFinanceInstallmentForDebitOrMoney(SimpleFinanceModel simpleFinanceModel, StatusPaymentEnum allStatusPayment) {
        LocalDate currentMonth = simpleFinanceModel.getMonth_payment();
        SimpleFinanceInstallmentModel simpleFinanceInstallmentBuilder = new SimpleFinanceInstallmentModel.Builder()
                .setNumberInstallment(1)
                .setStatusPayment(allStatusPayment)
                .setValueInstallment(simpleFinanceModel.getTotal_value())
                .seMonthPaymentInstallment(currentMonth.getMonthValue() + "/" + currentMonth.getYear())
                .setSimpleFinance(simpleFinanceModel)
                .build();
        simpleFinanceInstallmentRepository.save(simpleFinanceInstallmentBuilder);
    }

    private void saveSimpleFinanceInstallmentForCredit(SimpleFinanceModel simpleFinanceModel, StatusPaymentEnum allStatusPayment) {
        double calculetedInstallment = simpleFinanceModel.getTotal_value() / simpleFinanceModel.getTotal_installment();
        LocalDate currentMonth = simpleFinanceModel.getMonth_payment();
        for (int i = 0; i < simpleFinanceModel.getTotal_installment(); i++) {
            currentMonth = currentMonth.plusMonths(1);
            SimpleFinanceInstallmentModel simpleFinanceInstallmentBuilder = new SimpleFinanceInstallmentModel.Builder()
                    .setNumberInstallment(i + 1)
                    .setStatusPayment(allStatusPayment)
                    .setValueInstallment(calculetedInstallment)
                    .seMonthPaymentInstallment(currentMonth.getMonthValue() + "/" + currentMonth.getYear())
                    .setSimpleFinance(simpleFinanceModel)
                    .build();
            simpleFinanceInstallmentRepository.save(simpleFinanceInstallmentBuilder);
        }
    }

    @Override
    public Integer getTotalByMonthJanuary() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthJanuary(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthFebruary() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthFebruary(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthMarch() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthMarch(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthApril() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthApril(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthMay() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthMay(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthJune() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthJune(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthJuly() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthJuly(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthAugust() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthAugust(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthSeptember() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthSeptember(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthOctober() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthOctober(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthNovember() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthNovember(userAuthenticated.getId());
    }

    @Override
    public Integer getTotalByMonthDecember() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.getTotalByMonthDecember(userAuthenticated.getId());
    }
}
