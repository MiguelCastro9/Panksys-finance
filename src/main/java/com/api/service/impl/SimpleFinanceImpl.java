package com.api.service.impl;

import com.api.model.SimpleFinanceModel;
import com.api.model.UserModel;
import com.api.repository.SimpleFinanceRepository;
import com.api.service.SimpleFinanceService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
public class SimpleFinanceImpl implements SimpleFinanceService {

    @Autowired
    private SimpleFinanceRepository simpleFinanceRepository;


    @Override
    public SimpleFinanceModel save(SimpleFinanceModel simpleFinanceModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder()
                .setName(simpleFinanceModel.getName())
                .setValue(simpleFinanceModel.getValue())
                .setForm_payment(simpleFinanceModel.getForm_payment())
                .setMounth_payment(simpleFinanceModel.getMounth_payment())
                .setInstallment(simpleFinanceModel.getInstallment())
                .setDescription(simpleFinanceModel.getDescription())
                .setStatus_payment(simpleFinanceModel.getStatus_payment())
                .setUser(userAuthenticated)
                .setEnabled(true)
                .setCreated_date(LocalDateTime.now())
                .setUpdated_date(LocalDateTime.now());
        return simpleFinanceRepository.save(builder.build());
    }

    @Override
    public SimpleFinanceModel update(Long id, SimpleFinanceModel simpleFinanceModel) {
        UserModel userAuthenticated = getUserAuthenticated();
        Long userId = simpleFinanceRepository.getUserId(id);
        if (userId == null) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (!userId.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finances.");
        }
        return simpleFinanceRepository.findById(id)
                .map(existingSimpleFinance -> {
                    SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder()
                            .setId(existingSimpleFinance.getId())
                            .setName(simpleFinanceModel.getName())
                            .setValue(simpleFinanceModel.getValue())
                            .setForm_payment(simpleFinanceModel.getForm_payment())
                            .setMounth_payment(simpleFinanceModel.getMounth_payment())
                            .setInstallment(simpleFinanceModel.getInstallment())
                            .setDescription(simpleFinanceModel.getDescription())
                            .setStatus_payment(simpleFinanceModel.getStatus_payment())
                            .setUser(userAuthenticated)
                            .setEnabled(true)
                            .setCreated_date(existingSimpleFinance.getCreated_date())
                            .setUpdated_date(LocalDateTime.now());
                    return simpleFinanceRepository.save(builder.build());
                })
                .orElseThrow(() -> new IllegalArgumentException("Simple finance don't exists."));
    }

    @Override
    public List<SimpleFinanceModel> list() {
        UserModel userAuthenticated = getUserAuthenticated();
        return simpleFinanceRepository.list(userAuthenticated.getId())
                .stream()
                .sorted(Comparator.comparing(SimpleFinanceModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SimpleFinanceModel> find(Long id) {
        UserModel userAuthenticated = getUserAuthenticated();
        Long userId = simpleFinanceRepository.getUserId(id);
        if (userId == null) {
            throw new IllegalArgumentException("Simple finance don't exists.");
        }
        if (!userId.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to search for simple finances from other users.");
        }
        return simpleFinanceRepository.findById(id);
    }

    @Override
    public SimpleFinanceModel disabled(Long id) {
        UserModel userAuthenticated = getUserAuthenticated();
        Long userId = simpleFinanceRepository.getUserId(id);
        if (userId == null) {
            throw new IllegalArgumentException("Simple finance dont't exists.");
        }
        if (!userId.equals(userAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finances.");
        }
        return simpleFinanceRepository.findById(id)
                .map(existingSimpleFinance -> {
                    SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder()
                            .setId(existingSimpleFinance.getId())
                            .setName(existingSimpleFinance.getName())
                            .setValue(existingSimpleFinance.getValue())
                            .setForm_payment(existingSimpleFinance.getForm_payment())
                            .setMounth_payment(existingSimpleFinance.getMounth_payment())
                            .setInstallment(existingSimpleFinance.getInstallment())
                            .setDescription(existingSimpleFinance.getDescription())
                            .setStatus_payment(existingSimpleFinance.getStatus_payment())
                            .setUser(userAuthenticated)
                            .setEnabled(false)
                            .setCreated_date(existingSimpleFinance.getCreated_date())
                            .setUpdated_date(LocalDateTime.now());
                    return simpleFinanceRepository.save(builder.build());
                })
                .orElseThrow(() -> new IllegalArgumentException("Simple finance don't exists."));
    }

    @Override
    public void deleteAll() {
        simpleFinanceRepository.deleteAll();
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
