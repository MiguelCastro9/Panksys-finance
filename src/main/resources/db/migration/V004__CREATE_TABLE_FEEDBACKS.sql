/**
 * Author:  Miguel Castro
 */
CREATE TABLE feedbacks (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  total_stars INT NOT NULL,
  title VARCHAR(45) NOT NULL,
  description VARCHAR(255),
  created_date DATE NOT NULL,
  user_id BIGINT NOT NULL
);

ALTER TABLE feedbacks
ADD CONSTRAINT fk_fb_user_id FOREIGN KEY (user_id) REFERENCES users(id);