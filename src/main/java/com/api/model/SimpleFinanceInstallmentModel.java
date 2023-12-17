package com.api.model;

import com.api.enums.StatusPaymentEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Miguel Castro
 */
@Entity
@Table(name = "simple_finance_installments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleFinanceInstallmentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer number_installment;
    
    @Column(nullable = false)
    private double value_installment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPaymentEnum status_payment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "simple_finance_id")
    private SimpleFinanceModel simple_finance;

    public SimpleFinanceInstallmentModel(Integer number_installment, double value_installment, StatusPaymentEnum status_payment, SimpleFinanceModel simple_finance) {
        this.number_installment = number_installment;
        this.status_payment = status_payment;
        this.simple_finance = simple_finance;
        this.value_installment = value_installment;
    }

    public SimpleFinanceInstallmentModel(Builder builder) {
        this.id = builder.id;
        this.status_payment = builder.status_payment;
        this.number_installment = builder.number_installment;
        this.simple_finance = builder.simple_finance;
        this.value_installment = builder.value_installment;
    }

    public static class Builder {

        private Long id;

        private StatusPaymentEnum status_payment;

        private Integer number_installment;
        
        private double value_installment;
        
        private SimpleFinanceModel simple_finance;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setStatusPayment(StatusPaymentEnum status_payment) {
            this.status_payment = status_payment;
            return this;
        }

        public Builder setNumberInstallment(Integer number_installment) {
            this.number_installment = number_installment;
            return this;
        }
        
        public Builder setValueInstallment(double value_installment) {
            this.value_installment = value_installment;
            return this;
        }
        
        public Builder setSimpleFinance(SimpleFinanceModel simple_finance) {
            this.simple_finance = simple_finance;
            return this;
        }

        public SimpleFinanceInstallmentModel build() {
            return new SimpleFinanceInstallmentModel(this);
        }
    }
}
