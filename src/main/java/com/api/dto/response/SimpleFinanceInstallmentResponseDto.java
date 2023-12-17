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

    private Integer installment;
    
    private StatusPaymentEnum status_payment;

    public static SimpleFinanceInstallmentResponseDto convertEntityForSimpleFinanceInstallmentDto(SimpleFinanceInstallmentModel simpleFinanceInstallmentModel) {
        return new SimpleFinanceInstallmentResponseDto(simpleFinanceInstallmentModel.getId(),
                simpleFinanceInstallmentModel.getInstallment(), simpleFinanceInstallmentModel.getStatus_payment());
    }
}
