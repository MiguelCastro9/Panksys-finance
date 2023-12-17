package com.api.controller;

import com.api.dto.request.SimpleFinanceInstallmentRequestDto;
import com.api.dto.response.SimpleFinanceInstallmentResponseDto;
import com.api.model.SimpleFinanceInstallmentModel;
import com.api.service.SimpleFinanceInstallmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miguel Castro
 */
@RestController
@SecurityRequirement(name = "BearerAuthentication")
@Tag(name = "Simple Finance Installments")
@RequestMapping("api/v1/simple-finance-installment")
public class SimpleFinanceInstallmentController {
    
    @Autowired
    private SimpleFinanceInstallmentService simpleFinanceInstallmentService;
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<SimpleFinanceInstallmentResponseDto> update(@PathVariable Long id, @Valid @RequestBody SimpleFinanceInstallmentRequestDto simpleFinanceInstallmentRequestDto) {
        SimpleFinanceInstallmentModel simpleFinanceInstallmentModel = simpleFinanceInstallmentService.update(id, simpleFinanceInstallmentRequestDto.convertSimpleFinanceInstallmentUpdateDtoForEntity());
        SimpleFinanceInstallmentResponseDto simpleFinanceInstallmentResponseDto = SimpleFinanceInstallmentResponseDto.convertEntityForSimpleFinanceInstallmentDto(simpleFinanceInstallmentModel);
        simpleFinanceInstallmentResponseDto.add(linkTo(methodOn(SimpleFinanceInstallmentController.class).update(id, simpleFinanceInstallmentRequestDto)).withSelfRel());
        return new ResponseEntity<>(simpleFinanceInstallmentResponseDto, HttpStatus.OK);
    }
    
    @GetMapping("/list/{id}")
    public ResponseEntity<List<SimpleFinanceInstallmentResponseDto>> list(@PathVariable("id") Long simpleFinanceId) {
        List<SimpleFinanceInstallmentResponseDto> simpleFinanceInstallmentResponseDto = simpleFinanceInstallmentService.list(simpleFinanceId).stream()
                .map(simpleFinanceInstallment -> SimpleFinanceInstallmentResponseDto.convertEntityForSimpleFinanceInstallmentDto(simpleFinanceInstallment))
                .collect(Collectors.toList());
        simpleFinanceInstallmentResponseDto.forEach(simpleFinanceInstallment -> simpleFinanceInstallment
                .add(linkTo(methodOn(SimpleFinanceInstallmentController.class)
                        .find(simpleFinanceInstallment.getId())).withSelfRel()));
        return new ResponseEntity<>(simpleFinanceInstallmentResponseDto, HttpStatus.OK);
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<SimpleFinanceInstallmentResponseDto> find(@PathVariable Long id) {
        SimpleFinanceInstallmentModel simpleFinanceInstallmentModel = simpleFinanceInstallmentService.find(id).orElseThrow();
        SimpleFinanceInstallmentResponseDto simpleFinanceInstallmentResponseDto = SimpleFinanceInstallmentResponseDto.convertEntityForSimpleFinanceInstallmentDto(simpleFinanceInstallmentModel);
        simpleFinanceInstallmentResponseDto.add(linkTo(methodOn(SimpleFinanceController.class).find(id)).withSelfRel());
        return new ResponseEntity<>(simpleFinanceInstallmentResponseDto, HttpStatus.OK);
    }
}
