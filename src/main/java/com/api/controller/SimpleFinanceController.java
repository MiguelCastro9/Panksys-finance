package com.api.controller;

import com.api.dto.request.SimpleFinanceRequestDto;
import com.api.dto.response.SimpleFinanceResponseDto;
import com.api.model.SimpleFinanceModel;
import com.api.service.SimpleFinanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> save(@Valid @RequestBody SimpleFinanceRequestDto simpleFinanceRequestDto) {
        SimpleFinanceModel builder = simpleFinanceService.save(simpleFinanceRequestDto.convertSimpleFinanceDtoForEntity());
        builder.add(linkTo(methodOn(SimpleFinanceController.class).save(simpleFinanceRequestDto)).withSelfRel());
        return new ResponseEntity<>(builder, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody SimpleFinanceRequestDto simpleFinanceRequestDto) {
        SimpleFinanceModel builder = simpleFinanceService.update(id, simpleFinanceRequestDto.convertSimpleFinanceUpdateDtoForEntity());
        builder.add(linkTo(methodOn(SimpleFinanceController.class).update(id, simpleFinanceRequestDto)).withSelfRel());
        return new ResponseEntity<>(builder, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<SimpleFinanceResponseDto>> list() {
        List<SimpleFinanceResponseDto> simpleFinances = simpleFinanceService.list().stream()
                .map(simpleFinance -> SimpleFinanceResponseDto.convertEntityForSimpleFinanceDto(simpleFinance))
                .collect(Collectors.toList());
        simpleFinances.forEach(simpleFinance -> simpleFinance
                .add(linkTo(methodOn(SimpleFinanceController.class)
                        .find(simpleFinance.getId())).withSelfRel()));
        return new ResponseEntity<>(simpleFinances, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        SimpleFinanceModel simpleFinanceModel = simpleFinanceService.find(id).orElseThrow();
        simpleFinanceModel.add(linkTo(methodOn(SimpleFinanceController.class).find(id)).withSelfRel());
        return new ResponseEntity<>(simpleFinanceModel, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        simpleFinanceService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
