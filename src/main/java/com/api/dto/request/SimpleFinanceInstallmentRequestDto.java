package com.api.dto.request;

import com.api.enums.StatusPaymentEnum;
import com.api.model.SimpleFinanceInstallmentModel;
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
public class SimpleFinanceInstallmentRequestDto {

    private Long id;
    
    private StatusPaymentEnum status_payment;

    public SimpleFinanceInstallmentRequestDto(StatusPaymentEnum status_payment) {
        this.status_payment = status_payment;
    }

    public SimpleFinanceInstallmentModel convertSimpleFinanceInstallmentDtoForEntity() {
        SimpleFinanceInstallmentModel.Builder simpleFinanceInstallmentBuilder = new SimpleFinanceInstallmentModel.Builder();
        simpleFinanceInstallmentBuilder.setStatusPayment(status_payment);
        return new SimpleFinanceInstallmentModel(simpleFinanceInstallmentBuilder);
    }

    public SimpleFinanceInstallmentModel convertSimpleFinanceInstallmentUpdateDtoForEntity() {
        SimpleFinanceInstallmentModel.Builder simpleFinanceInstallmentBuilder = new SimpleFinanceInstallmentModel.Builder();
        simpleFinanceInstallmentBuilder.setId(id)
                .setStatusPayment(status_payment);
        return new SimpleFinanceInstallmentModel(simpleFinanceInstallmentBuilder);
    }
}
