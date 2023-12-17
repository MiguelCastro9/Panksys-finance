package com.api.service.impl;

import com.api.model.SimpleFinanceInstallmentModel;
import com.api.model.UserModel;
import com.api.repository.SimpleFinanceInstallmentRepository;
import com.api.repository.SimpleFinanceRepository;
import com.api.service.SimpleFinanceInstallmentService;
import java.util.List;
import java.util.Optional;
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
        if (simpleFinanceId == null) {
            throw new IllegalArgumentException("Simple finance installment don't exists.");
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

    @Override
    public List<SimpleFinanceInstallmentModel> list(Long simpleFinanceId) {
        System.out.println(simpleFinanceId);
        UserModel userAuthenticated = getUserAuthenticated();
        Long getSimpleFinanceId = simpleFinanceInstallmentRepository.getSimpleFinanceId(simpleFinanceId);
        Long getUserId = simpleFinanceRepository.getUserId(getSimpleFinanceId);
        if (getSimpleFinanceId == null) {
            throw new IllegalArgumentException("Simple finance installment don't exists.");
        }
        if (!getUserId.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finance installments.");
        }
        return simpleFinanceInstallmentRepository.list(simpleFinanceId);
    }
    
    @Override
    public Optional<SimpleFinanceInstallmentModel> find(Long id) {
        UserModel userAuthenticated = getUserAuthenticated();
        Long userId = simpleFinanceRepository.getUserId(id);
        if (userId == null) {
            throw new IllegalArgumentException("Simple finance installment don't exists.");
        }
        if (!userId.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to search for simple finance installments from other users.");
        }
        return simpleFinanceInstallmentRepository.findById(id);
    }
}
