package com.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 *
 * @author Miguel Castro
 */
@Entity
@Table(name = "simple_finances")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleFinanceModel extends RepresentationModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private String form_payment;

    @Column(nullable = false)
    private LocalDate mounth_payment;

    @Column(nullable = false)
    private Integer installment;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private String status_payment;

    public SimpleFinanceModel(String name, double value, String form_payment, LocalDate mounth_payment, Integer installment, String description, String status_payment) {
        this.name = name;
        this.value = value;
        this.form_payment = form_payment;
        this.mounth_payment = mounth_payment;
        this.installment = installment;
        this.description = description;
        this.status_payment = status_payment;
    }

    public SimpleFinanceModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.value = builder.value;
        this.form_payment = builder.form_payment;
        this.mounth_payment = builder.mounth_payment;
        this.installment = builder.installment;
        this.description = builder.description;
        this.status_payment = builder.status_payment;
    }

    public static class Builder {

        private Long id;

        private String name;

        private double value;

        private String form_payment;

        private LocalDate mounth_payment;

        private Integer installment;

        private String description;

        private String status_payment;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setValue(double value) {
            this.value = value;
            return this;
        }

        public Builder setForm_payment(String form_payment) {
            this.form_payment = form_payment;
            return this;
        }

        public Builder setMounth_payment(LocalDate mounth_payment) {
            this.mounth_payment = mounth_payment;
            return this;
        }

        public Builder setInstallment(Integer installment) {
            this.installment = installment;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStatus_payment(String status_payment) {
            this.status_payment = status_payment;
            return this;
        }

        public SimpleFinanceModel build() {
            return new SimpleFinanceModel(this);
        }
    }
}
