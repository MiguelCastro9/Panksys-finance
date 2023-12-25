/**
 * Author:  Miguel Castro
 */
CREATE TABLE simple_finance_installments (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  number_installment BIGINT NOT NULL,
  value_installment DECIMAL NOT NULL,
  month_payment_installment VARCHAR(45) NOT NULL,
  status_payment VARCHAR(45) NOT NULL,
  simple_finance_id BIGINT NOT NULL
);

ALTER TABLE simple_finance_installments
ADD CONSTRAINT fk_sfi_simple_finance_id FOREIGN KEY (simple_finance_id) REFERENCES simple_finances(id);