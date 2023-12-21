/**
 * Author:  Miguel Castro
 */
CREATE TABLE users (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  birth_date DATE NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(45) NOT NULL,
  enabled BOOLEAN,
  created_date DATETIME(6) NOT NULL,
  updated_date DATETIME(6) NOT NULL
);