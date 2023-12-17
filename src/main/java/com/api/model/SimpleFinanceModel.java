package com.api.model;

import com.api.enums.FormPaymentEnum;
import com.api.enums.StatusPaymentEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Miguel Castro
 */
@Entity
@Table(name = "simple_finances")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleFinanceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private double total_value;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private FormPaymentEnum form_payment;

    @Column(nullable = false)
    private LocalDate mounth_payment;
    
    private Integer total_installment;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SimpleFinanceInstallmentModel> installments;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private StatusPaymentEnum all_status_payment = StatusPaymentEnum.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private LocalDateTime created_date;

    @Column(nullable = false)
    private LocalDateTime updated_date;

    public SimpleFinanceModel(String name, double total_value, FormPaymentEnum form_payment, LocalDate mounth_payment, 
            Integer total_installment, String description, StatusPaymentEnum all_status_payment, UserModel user, 
            boolean enabled,LocalDateTime created_date, LocalDateTime updated_date) {
        this.name = name;
        this.total_value = total_value;
        this.form_payment = form_payment;
        this.mounth_payment = mounth_payment;
        this.total_installment = total_installment;
        this.description = description;
        this.all_status_payment = all_status_payment;
        this.user = user;
        this.enabled = enabled;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public SimpleFinanceModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.total_value = builder.total_value;
        this.form_payment = builder.form_payment;
        this.mounth_payment = builder.mounth_payment;
        this.total_installment = builder.total_installment;
        this.description = builder.description;
        this.all_status_payment = builder.all_status_payment;
        this.user = builder.user;
        this.enabled = builder.enabled;
        this.created_date = builder.created_date;
        this.updated_date = builder.updated_date;

    }

    public static class Builder {

        private Long id;

        private String name;

        private double total_value;

        private FormPaymentEnum form_payment;

        private LocalDate mounth_payment;
        
        private Integer total_installment;

        private String description;

        private StatusPaymentEnum all_status_payment = StatusPaymentEnum.PENDING;

        private UserModel user;

        private boolean enabled;

        private LocalDateTime created_date;

        private LocalDateTime updated_date;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTotalValue(double total_value) {
            this.total_value = total_value;
            return this;
        }

        public Builder setForm_payment(FormPaymentEnum form_payment) {
            this.form_payment = form_payment;
            return this;
        }

        public Builder setMounth_payment(LocalDate mounth_payment) {
            this.mounth_payment = mounth_payment;
            return this;
        }
        
        public Builder setTotal_installment(Integer total_installment) {
            this.total_installment = total_installment;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAll_status_payment(StatusPaymentEnum all_status_payment) {
            this.all_status_payment = all_status_payment;
            return this;
        }

        public Builder setUser(UserModel user) {
            this.user = user;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder setCreated_date(LocalDateTime created_date) {
            this.created_date = created_date;
            return this;
        }

        public Builder setUpdated_date(LocalDateTime updated_date) {
            this.updated_date = updated_date;
            return this;
        }

        public SimpleFinanceModel build() {
            return new SimpleFinanceModel(this);
        }
    }
}
