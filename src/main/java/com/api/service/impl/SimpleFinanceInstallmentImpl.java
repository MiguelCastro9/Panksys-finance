package com.api.service.impl;

import com.api.model.SimpleFinanceInstallmentModel;
import com.api.model.UserModel;
import com.api.repository.SimpleFinanceInstallmentRepository;
import com.api.repository.SimpleFinanceRepository;
import com.api.service.SimpleFinanceInstallmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Miguel Castro
 */
@Service
public class SimpleFinanceInstallmentImpl implements SimpleFinanceInstallmentService {

    @Autowired
    private SimpleFinanceInstallmentRepository simpleFinanceInstallmentRepository;

    @Autowired
    private SimpleFinanceRepository simpleFinanceRepository;

    @Override
    public SimpleFinanceInstallmentModel update(Long id, SimpleFinanceInstallmentModel simpleFinanceInstallmentModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        Long simpleFinanceId = simpleFinanceInstallmentRepository.getSimpleFinanceId(id);
        Long userId = simpleFinanceRepository.getUserId(simpleFinanceId);
        boolean getCheckSimpleFinanceEnabled = simpleFinanceRepository.checkSimpleFinanceEnabled(simpleFinanceId);
        if (simpleFinanceId == null) {
            throw new IllegalArgumentException("Simple finance installment don't exists.");
        }
        if (!getCheckSimpleFinanceEnabled) {
            throw new IllegalArgumentException("It is not allowed to view disabled simple finance installments.");
        }
        if (!userId.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finance installments.");
        }
        return simpleFinanceInstallmentRepository.findById(id)
                .map(existingSimpleFinanceInsttament -> {
                    SimpleFinanceInstallmentModel builder = new SimpleFinanceInstallmentModel.Builder()
                            .setId(existingSimpleFinanceInsttament.getId())
                            .setNumberInstallment(existingSimpleFinanceInsttament.getNumber_installment())
                            .setValueInstallment(existingSimpleFinanceInsttament.getValue_installment())
                            .setStatusPayment(simpleFinanceInstallmentModel.getStatus_payment())
                            .setSimpleFinance(existingSimpleFinanceInsttament.getSimple_finance())
                            .build();
                    return simpleFinanceInstallmentRepository.save(builder);
                })
                .orElseThrow(() -> new IllegalArgumentException("Simple finance installment don't exists."));
    }

    @Override
    public List<SimpleFinanceInstallmentModel> list(Long simpleFinanceId) {
        UserModel userAuthenticated = getUserAuthenticated();
        Long getSimpleFinanceId = simpleFinanceInstallmentRepository.getSimpleFinanceId(simpleFinanceId);
        Long getUserId = simpleFinanceRepository.getUserId(getSimpleFinanceId);
        boolean getCheckSimpleFinanceEnabled = simpleFinanceRepository.checkSimpleFinanceEnabled(simpleFinanceId);
        if (getSimpleFinanceId == null) {
            throw new IllegalArgumentException("Simple finance installment don't exists.");
        }
        if (!getCheckSimpleFinanceEnabled) {
            throw new IllegalArgumentException("It is not allowed to view disabled simple finance installments.");
        }
        if (getUserId == null) {
            throw new IllegalArgumentException("Don't exists users vinculated at simple finance installments.");
        }
        if (!getUserId.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finance installments.");
        }
        return simpleFinanceInstallmentRepository.list(simpleFinanceId);
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
}
