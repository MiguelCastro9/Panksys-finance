package com.api.dto.response;

import com.api.model.SimpleFinanceModel;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Miguel Castro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleFinanceResponseDto {

    private Long id;

    private String name;

    private double value;

    private String form_payment;

    private LocalDate mounth_payment;

    private Integer installment;

    private String description;

    private String status_payment;

    public static SimpleFinanceResponseDto convertEntityForSimpleFinanceDto(SimpleFinanceModel simpleFinanceModel) {
        return new SimpleFinanceResponseDto(simpleFinanceModel.getId(), simpleFinanceModel.getName(),
                simpleFinanceModel.getValue(), simpleFinanceModel.getForm_payment(),
                simpleFinanceModel.getMounth_payment(), simpleFinanceModel.getInstallment(), simpleFinanceModel.getDescription(),
                simpleFinanceModel.getStatus_payment());
    }
}
