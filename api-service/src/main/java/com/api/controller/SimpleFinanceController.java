package com.api.controller;

import com.api.dto.request.SimpleFinanceRequestDto;
import com.api.dto.response.SimpleFinanceResponseDto;
import com.api.enums.FormPaymentEnum;
import com.api.model.SimpleFinanceModel;
import com.api.service.SimpleFinanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Miguel Castro
 */
@RestController
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Simple Finances")
@RequestMapping("api/v1/simple-finance")
public class SimpleFinanceController {

    @Autowired
    private SimpleFinanceService simpleFinanceService;

    @PostMapping("/save")
    public ResponseEntity<SimpleFinanceResponseDto> save(@Valid @RequestBody SimpleFinanceRequestDto simpleFinanceRequestDto) {
        SimpleFinanceModel simpleFinanceModel = simpleFinanceService.save(simpleFinanceRequestDto.convertSimpleFinanceRequestDtoForEntity());
        SimpleFinanceResponseDto simpleFinanceResponseDto = SimpleFinanceResponseDto.convertEntityForSimpleFinanceResponseDto(simpleFinanceModel);
        simpleFinanceResponseDto.add(linkTo(methodOn(SimpleFinanceController.class).save(simpleFinanceRequestDto)).withSelfRel());
        return new ResponseEntity<>(simpleFinanceResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SimpleFinanceResponseDto> update(@PathVariable Long id, @Valid @RequestBody SimpleFinanceRequestDto simpleFinanceRequestDto) {
        SimpleFinanceModel simpleFinanceModel = simpleFinanceService.update(id, simpleFinanceRequestDto.convertSimpleFinanceUpdateRequestDtoForEntity());
        SimpleFinanceResponseDto simpleFinanceResponseDto = SimpleFinanceResponseDto.convertEntityForSimpleFinanceResponseDto(simpleFinanceModel);
        simpleFinanceResponseDto.add(linkTo(methodOn(SimpleFinanceController.class).update(id, simpleFinanceRequestDto)).withSelfRel());
        return new ResponseEntity<>(simpleFinanceResponseDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SimpleFinanceResponseDto>> findAllSimpleFinances() {
        List<SimpleFinanceResponseDto> simpleFinanceResponseDto = simpleFinanceService.findAllSimpleFinances().stream()
                .map(simpleFinance -> SimpleFinanceResponseDto.convertEntityForSimpleFinanceResponseDto(simpleFinance))
                .collect(Collectors.toList());
        simpleFinanceResponseDto.forEach(simpleFinance -> simpleFinance
                .add(linkTo(methodOn(SimpleFinanceController.class)
                        .find(simpleFinance.getId())).withSelfRel()));
        return new ResponseEntity<>(simpleFinanceResponseDto, HttpStatus.OK);
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<SimpleFinanceResponseDto>> filter(@RequestParam("name") String name, @RequestParam("form_payment") FormPaymentEnum formPayment,
            @RequestParam("month_payment") LocalDate monthPayment, @RequestParam("total_installment") Integer totalInstallment) {
        List<SimpleFinanceResponseDto> simpleFinanceResponseDto = simpleFinanceService
                .filterSimpleFinances(name, formPayment, monthPayment, totalInstallment).stream()
                .map(simpleFinance -> SimpleFinanceResponseDto.convertEntityForSimpleFinanceResponseDto(simpleFinance))
                .collect(Collectors.toList());
        simpleFinanceResponseDto.forEach(simpleFinance -> simpleFinance
                .add(linkTo(methodOn(SimpleFinanceController.class)
                        .find(simpleFinance.getId())).withSelfRel()));
        return new ResponseEntity<>(simpleFinanceResponseDto, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<SimpleFinanceResponseDto> find(@PathVariable Long id) {
        SimpleFinanceModel simpleFinanceModel = simpleFinanceService.findSimpleFinance(id).orElseThrow();
        SimpleFinanceResponseDto simpleFinanceResponseDto = SimpleFinanceResponseDto.convertEntityForSimpleFinanceResponseDto(simpleFinanceModel);
        simpleFinanceResponseDto.add(linkTo(methodOn(SimpleFinanceController.class).find(id)).withSelfRel());
        return new ResponseEntity<>(simpleFinanceResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/disabled/{id}")
    public ResponseEntity<SimpleFinanceResponseDto> disabled(@PathVariable Long id) {
        SimpleFinanceModel simpleFinanceModel = simpleFinanceService.disabled(id);
        SimpleFinanceResponseDto simpleFinanceResponseDto = SimpleFinanceResponseDto.convertEntityForSimpleFinanceResponseDto(simpleFinanceModel);
        simpleFinanceResponseDto.add(linkTo(methodOn(SimpleFinanceController.class).disabled(id)).withSelfRel());
        return new ResponseEntity("Simple finance [" + simpleFinanceResponseDto.getId() + "] " + simpleFinanceResponseDto.getName() + " disabled with success.", HttpStatus.OK);
    }
    
    @GetMapping("/total-money")
    public ResponseEntity<Integer> getTotalFormPaymentByMoney() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByFormPaymentMoney(), HttpStatus.OK);
    }
    
    @GetMapping("/total-debit")
    public ResponseEntity<Integer> getTotalFormPaymentByDebit() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByFormPaymentDebit(), HttpStatus.OK);
    }
    
    @GetMapping("/total-credit")
    public ResponseEntity<Integer> getTotalFormPaymentByCredit() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByFormPaymentCredit(), HttpStatus.OK);
    }
    
    @GetMapping("/total-january")
    public ResponseEntity<Integer> getTotalByMonthJanuary() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthJanuary(), HttpStatus.OK);
    }
    
    @GetMapping("/total-february")
    public ResponseEntity<Integer> getTotalByMonthFebruary() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthFebruary(), HttpStatus.OK);
    }
    
    @GetMapping("/total-march")
    public ResponseEntity<Integer> getTotalByMonthMarch() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthMarch(), HttpStatus.OK);
    }
    
    @GetMapping("/total-april")
    public ResponseEntity<Integer> getTotalByMonthApril() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthApril(), HttpStatus.OK);
    }
    
    @GetMapping("/total-may")
    public ResponseEntity<Integer> getTotalByMonthMay() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthMay(), HttpStatus.OK);
    }
    
    @GetMapping("/total-june")
    public ResponseEntity<Integer> getTotalByMonthJune() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthJune(), HttpStatus.OK);
    }
    
    @GetMapping("/total-july")
    public ResponseEntity<Integer> getTotalByMonthJuly() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthJuly(), HttpStatus.OK);
    }
    
    @GetMapping("/total-august")
    public ResponseEntity<Integer> getTotalByMonthAugust() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthAugust(), HttpStatus.OK);
    }
    
    @GetMapping("/total-september")
    public ResponseEntity<Integer> getTotalByMonthSeptember() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthSeptember(), HttpStatus.OK);
    }
    
    @GetMapping("/total-october")
    public ResponseEntity<Integer> getTotalByMonthOctober() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthOctober(), HttpStatus.OK);
    }
    
    @GetMapping("/total-november")
    public ResponseEntity<Integer> getTotalByMonthNovember() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthNovember(), HttpStatus.OK);
    }
    
    @GetMapping("/total-december")
    public ResponseEntity<Integer> getTotalByMonthDecember() {
        return new ResponseEntity<>(simpleFinanceService.getTotalByMonthDecember(), HttpStatus.OK);
    }
}
