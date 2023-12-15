package com.api.dto.request;

import com.api.enums.FormPaymentEnum;
import com.api.enums.StatusPaymentEnum;
import com.api.model.SimpleFinanceModel;
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
    private double value;

    @NotNull(message = "Form of payment is required.")
    private FormPaymentEnum form_payment;

    @NotNull(message = "Mounth of payment is required.")
    private LocalDate mounth_payment;

    @NotNull(message = "Installment is required.")
    private Integer installment;

    private String description;

    @NotNull(message = "Status of payment is required.")
    private StatusPaymentEnum status_payment;
    
    public SimpleFinanceRequestDto(String name, double value, FormPaymentEnum form_payment, LocalDate mounth_payment, 
            Integer installment, String description, StatusPaymentEnum status_payment) {
        this.name = name;
        this.value = value;
        this.form_payment = form_payment;
        this.mounth_payment = mounth_payment;
        this.installment = installment;
        this.description = description;
        this.status_payment = status_payment;
    }
    
    public SimpleFinanceModel convertSimpleFinanceDtoForEntity() {
        SimpleFinanceModel.Builder userBuilder = new SimpleFinanceModel.Builder();
        userBuilder.setName(name)
                .setValue(value)
                .setForm_payment(form_payment)
                .setMounth_payment(mounth_payment)
                .setInstallment(installment)
                .setDescription(description)
                .setStatus_payment(status_payment);
        return new SimpleFinanceModel(userBuilder);
    }

    public SimpleFinanceModel convertSimpleFinanceUpdateDtoForEntity() {
        SimpleFinanceModel.Builder builder = new SimpleFinanceModel.Builder();
        builder.setId(id)
                .setName(name)
                .setValue(value)
                .setForm_payment(form_payment)
                .setMounth_payment(mounth_payment)
                .setInstallment(installment)
                .setDescription(description)
                .setStatus_payment(status_payment);
        return new SimpleFinanceModel(builder);
    }
}
