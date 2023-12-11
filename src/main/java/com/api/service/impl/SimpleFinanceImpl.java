package com.api.service.impl;

import com.api.model.SimpleFinanceModel;
import com.api.repository.SimpleFinanceRepository;
import com.api.service.SimpleFinanceService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
        SimpleFinanceModel builder = new SimpleFinanceModel.Builder()
                .setName(simpleFinanceModel.getName())
                .setValue(simpleFinanceModel.getValue())
                .setForm_payment(simpleFinanceModel.getForm_payment())
                .setMounth_payment(LocalDate.parse(dateFormatter.format(simpleFinanceModel.getMounth_payment()), dateFormatter))
                .setInstallment(simpleFinanceModel.getInstallment())
                .setDescription(simpleFinanceModel.getDescription())
                .setStatus_payment(simpleFinanceModel.getStatus_payment())
                .build();
        return simpleFinanceRepository.save(builder);
    }

    @Override
    public SimpleFinanceModel update(Long id, SimpleFinanceModel simpleFinanceModel) {
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
                            .setStatus_payment(simpleFinanceModel.getStatus_payment());
                    return simpleFinanceRepository.save(builder.build());
                })
                .orElseThrow(() -> new IllegalArgumentException("Simple finance not found."));
    }

    @Override
    public List<SimpleFinanceModel> list() {
        return simpleFinanceRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(SimpleFinanceModel::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SimpleFinanceModel> find(Long id) {
        return simpleFinanceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        simpleFinanceRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        simpleFinanceRepository.deleteAll();
    }
}
