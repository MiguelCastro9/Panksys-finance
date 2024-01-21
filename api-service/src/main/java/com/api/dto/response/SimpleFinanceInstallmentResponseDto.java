package com.api.dto.response;

import com.api.enums.StatusPaymentEnum;
import com.api.model.SimpleFinanceInstallmentModel;
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
public class SimpleFinanceInstallmentResponseDto extends RepresentationModel {

    private Long id;
    
    private Integer number_installment;

    private double value_installment;

    private StatusPaymentEnum status_payment;

    private String month_payment_installment;

    public static SimpleFinanceInstallmentResponseDto convertEntityForSimpleFinanceInstallmentResponseDto(SimpleFinanceInstallmentModel simpleFinanceInstallmentModel) {
        return new SimpleFinanceInstallmentResponseDto(simpleFinanceInstallmentModel.getId(),
                simpleFinanceInstallmentModel.getNumber_installment(), simpleFinanceInstallmentModel.getValue_installment(), 
                simpleFinanceInstallmentModel.getStatus_payment(), simpleFinanceInstallmentModel.getMonth_payment_installment());
    }
}