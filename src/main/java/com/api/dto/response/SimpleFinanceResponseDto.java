package com.api.dto.response;

import com.api.enums.FormPaymentEnum;
import com.api.enums.StatusPaymentEnum;
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

    private LocalDate mounth_payment;

    private String description;

    private StatusPaymentEnum status_payment;

    public static SimpleFinanceResponseDto convertEntityForSimpleFinanceDto(SimpleFinanceModel simpleFinanceModel) {
        return new SimpleFinanceResponseDto(simpleFinanceModel.getId(), simpleFinanceModel.getName(),
                simpleFinanceModel.getTotal_value(), simpleFinanceModel.getForm_payment(),
                simpleFinanceModel.getMounth_payment(), simpleFinanceModel.getDescription(),
                simpleFinanceModel.getStatus_payment());
    }
}
