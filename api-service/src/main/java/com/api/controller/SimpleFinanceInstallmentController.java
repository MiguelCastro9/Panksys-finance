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
import org.springframework.hateoas.Link;
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
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

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
        SimpleFinanceInstallmentModel simpleFinanceInstallmentModel = simpleFinanceInstallmentService.update(id, simpleFinanceInstallmentRequestDto.convertSimpleFinanceInstallmentUpdateRequestDtoForEntity());
        SimpleFinanceInstallmentResponseDto simpleFinanceInstallmentResponseDto = SimpleFinanceInstallmentResponseDto.convertEntityForSimpleFinanceInstallmentResponseDto(simpleFinanceInstallmentModel);
        simpleFinanceInstallmentResponseDto.add(linkTo(methodOn(SimpleFinanceInstallmentController.class).update(id, simpleFinanceInstallmentRequestDto)).withSelfRel());
        return new ResponseEntity<>(simpleFinanceInstallmentResponseDto, HttpStatus.OK);
    }

    @GetMapping("/list/{simpleFinanceId}")
    public ResponseEntity<List<SimpleFinanceInstallmentResponseDto>> list(@PathVariable("simpleFinanceId") Long simpleFinanceId) {
        List<SimpleFinanceInstallmentResponseDto> simpleFinanceInstallmentResponseDtoList = simpleFinanceInstallmentService.findAllSimpleFinanceInstallments(simpleFinanceId).stream()
                .map(simpleFinanceInstallment -> {
                    SimpleFinanceInstallmentResponseDto simpleFinanceInstallmentResponseDto = SimpleFinanceInstallmentResponseDto.convertEntityForSimpleFinanceInstallmentResponseDto(simpleFinanceInstallment);
                    WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SimpleFinanceInstallmentController.class)
                            .list(simpleFinanceId));
                    Link selfLink = linkBuilder.withSelfRel();
                    simpleFinanceInstallmentResponseDto.add(selfLink);
                    return simpleFinanceInstallmentResponseDto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(simpleFinanceInstallmentResponseDtoList, HttpStatus.OK);
    }
}
