package com.api.dto.response;

import com.api.enums.FormPaymentEnum;
import com.api.model.SimpleFinanceModel;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 *
 * @author Miguel Castro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleFinanceResponseDto extends RepresentationModel {

    private Long id;

    private String name;

    private double total_value;

    private FormPaymentEnum form_payment;

    private LocalDate month_payment;
    
    private Integer total_installment;

    private String description;
    
    private String all_status_payment;
    
    public static SimpleFinanceResponseDto convertEntityForSimpleFinanceResponseDto(SimpleFinanceModel simpleFinanceModel) {
        return new SimpleFinanceResponseDto(simpleFinanceModel.getId(), simpleFinanceModel.getName(),
                simpleFinanceModel.getTotal_value(), simpleFinanceModel.getForm_payment(),
                simpleFinanceModel.getMonth_payment(), simpleFinanceModel.getTotal_installment(), 
                simpleFinanceModel.getDescription(), simpleFinanceModel.getAll_status_payment());
    }
}
