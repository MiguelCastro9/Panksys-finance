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

    private Integer installment;
    
    private StatusPaymentEnum status_payment;

    public SimpleFinanceInstallmentRequestDto(Integer installment) {
        this.installment = installment;
    }

    public SimpleFinanceInstallmentModel convertSimpleFinanceInstallmentDtoForEntity() {
        SimpleFinanceInstallmentModel.Builder builder = new SimpleFinanceInstallmentModel.Builder();
        builder.setInstallment(installment)
                .setStatusPayment(status_payment);
        return new SimpleFinanceInstallmentModel(builder);
    }

    public SimpleFinanceInstallmentModel convertSimpleFinanceInstallmentUpdateDtoForEntity() {
        SimpleFinanceInstallmentModel.Builder builder = new SimpleFinanceInstallmentModel.Builder();
        builder.setId(id)
                .setInstallment(installment)
                .setStatusPayment(status_payment);
        return new SimpleFinanceInstallmentModel(builder);
    }
}
