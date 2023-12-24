/**
 * Author:  Miguel Castro
 */
CREATE TABLE simple_finances (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  total_value DECIMAL NOT NULL,
  form_payment VARCHAR(45) NOT NULL,
  month_payment DATE NOT NULL,
  total_installment BIGINT NOT NULL,
  description VARCHAR(255),
  all_status_payment VARCHAR(45) NOT NULL,
  user_id BIGINT NOT NULL,
  enabled BOOLEAN NOT NULL,
  created_date DATE NOT NULL,
  updated_date DATE NOT NULL
);

ALTER TABLE simple_finances
ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);