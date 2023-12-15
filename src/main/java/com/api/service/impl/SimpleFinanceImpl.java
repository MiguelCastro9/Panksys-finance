package com.api.service.impl;

import com.api.model.SimpleFinanceModel;
import com.api.model.UserModel;
import com.api.repository.SimpleFinanceRepository;
import com.api.service.SimpleFinanceService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public SimpleFinanceModel save(SimpleFinanceModel simpleFinanceModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel infoUserAuthenticated = (UserModel) authentication.getPrincipal();
        SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder()
                .setName(simpleFinanceModel.getName())
                .setValue(simpleFinanceModel.getValue())
                .setForm_payment(simpleFinanceModel.getForm_payment())
                .setMounth_payment(LocalDate.parse(dateFormatter.format(simpleFinanceModel.getMounth_payment()), dateFormatter))
                .setInstallment(simpleFinanceModel.getInstallment())
                .setDescription(simpleFinanceModel.getDescription())
                .setStatus_payment(simpleFinanceModel.getStatus_payment())
                .setUser(infoUserAuthenticated)
                .setEnabled(true)
                .setCreated_date(LocalDateTime.now())
                .setUpdated_date(LocalDateTime.now());
        return simpleFinanceRepository.save(builder.build());
    }

    @Override
    public SimpleFinanceModel update(Long id, SimpleFinanceModel simpleFinanceModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        UserModel infoUserAuthenticated = (UserModel) authentication.getPrincipal();
        Long userId = simpleFinanceRepository.getUserId(id);
        if (userId == null) {
            throw new IllegalArgumentException("User ID not found.");
        }
        if (!userId.equals(infoUserAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finances.");
        }
        return simpleFinanceRepository.findById(id)
                .map(existingSimpleFinance -> {
                    SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder()
                            .setId(existingSimpleFinance.getId())
                            .setName(simpleFinanceModel.getName())
                            .setValue(simpleFinanceModel.getValue())
                            .setForm_payment(simpleFinanceModel.getForm_payment())
                            .setMounth_payment(LocalDate.parse(dateFormatter.format(simpleFinanceModel.getMounth_payment()), dateFormatter))
                            .setInstallment(simpleFinanceModel.getInstallment())
                            .setDescription(simpleFinanceModel.getDescription())
                            .setStatus_payment(simpleFinanceModel.getStatus_payment())
                            .setUser(infoUserAuthenticated)
                            .setEnabled(true)
                            .setCreated_date(existingSimpleFinance.getCreated_date())
                            .setUpdated_date(LocalDateTime.now());
                    return simpleFinanceRepository.save(builder.build());
                })
                .orElseThrow(() -> new IllegalArgumentException("fimple finance not found."));
    }

    @Override
    public List<SimpleFinanceModel> list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        UserModel infoUserAuthenticated = (UserModel) authentication.getPrincipal();
        return simpleFinanceRepository.list(infoUserAuthenticated.getId())
                .stream()
                .sorted(Comparator.comparing(SimpleFinanceModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SimpleFinanceModel> find(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = simpleFinanceRepository.getUserId(id);
        if (userId == null) {
            throw new IllegalArgumentException("User ID not found.");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserModel infoUserAuthenticated = (UserModel) authentication.getPrincipal();
            if (!userId.equals(infoUserAuthenticated.getId())) {
                throw new IllegalArgumentException("You are not allowed to search for simple finances from other users.");
            }
        } else {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        return simpleFinanceRepository.findById(id);
    }

    @Override
    public SimpleFinanceModel delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new IllegalArgumentException("User details not found in the authentication context.");
        }
        UserModel infoUserAuthenticated = (UserModel) authentication.getPrincipal();
        Long userId = simpleFinanceRepository.getUserId(id);
        if (userId == null) {
            throw new IllegalArgumentException("User ID not found.");
        }
        if (!userId.equals(infoUserAuthenticated.getId())) {
            throw new IllegalArgumentException("You are not allowed to change other users' simple finances.");
        }
        return simpleFinanceRepository.findById(id)
                .map(existingSimpleFinance -> {
                    SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder()
                            .setId(existingSimpleFinance.getId())
                            .setName(existingSimpleFinance.getName())
                            .setValue(existingSimpleFinance.getValue())
                            .setForm_payment(existingSimpleFinance.getForm_payment())
                            .setMounth_payment(LocalDate.parse(dateFormatter.format(existingSimpleFinance.getMounth_payment()), dateFormatter))
                            .setInstallment(existingSimpleFinance.getInstallment())
                            .setDescription(existingSimpleFinance.getDescription())
                            .setStatus_payment(existingSimpleFinance.getStatus_payment())
                            .setUser(infoUserAuthenticated)
                            .setEnabled(false)
                            .setCreated_date(existingSimpleFinance.getCreated_date())
                            .setUpdated_date(LocalDateTime.now());
                    return simpleFinanceRepository.save(builder.build());
                })
                .orElseThrow(() -> new IllegalArgumentException("fimple finance not found."));
    }

    @Override
    public void deleteAll() {
        simpleFinanceRepository.deleteAll();
    }
}
