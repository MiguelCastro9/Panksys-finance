package com.api.dto.request;

import com.api.enums.FormPaymentEnum;
import com.api.model.SimpleFinanceModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Miguel Castro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleFinanceRequestDto {

    private Long id;

    @NotBlank(message = "Name is required.")
    @Length(min = 3, max = 45, message = "Name required at minimum {min} and at maximum {max} characters.")
    private String name;

    @NotNull(message = "Value is required.")
    private double total_value;

    @NotNull(message = "Form of payment is required.")
    private FormPaymentEnum form_payment;

    @NotNull(message = "Month of payment is required.")
    private LocalDate month_payment;
    
    @Max(value = 45, message = "Total installment required at maximum 45 installments.")
    @Min(value = 1, message = "Total installment required at minimum 1 installment.")
    private Integer total_installment;

    private String description;

    public SimpleFinanceRequestDto(String name, double total_value, FormPaymentEnum form_payment, LocalDate month_payment, 
            Integer total_installment, String description) {
        this.name = name;
        this.total_value = total_value;
        this.form_payment = form_payment;
        this.month_payment = month_payment;
        this.total_installment = total_installment;
        this.description = description;
    }

    public SimpleFinanceModel convertSimpleFinanceDtoForEntity() {
        SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder();
        builder.setName(name)
                .setTotalValue(total_value)
                .setForm_payment(form_payment)
                .setMonth_payment(month_payment)
                .setTotal_installment(total_installment)
                .setDescription(description);
        return new SimpleFinanceModel(builder);
    }

    public SimpleFinanceModel convertSimpleFinanceUpdateDtoForEntity() {
        SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder();
        builder.setId(id)
                .setName(name)
                .setTotalValue(total_value)
                .setForm_payment(form_payment)
                .setMonth_payment(month_payment)
                .setTotal_installment(total_installment)
                .setDescription(description);
        return new SimpleFinanceModel(builder);
    }
}
