CREATE TABLE IF NOT EXISTS user(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS expense(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
description VARCHAR(80) NOT NULL,
amount DECIMAL(10,2) NOT NULL,
expense_date TIMESTAMP,
user_id BIGINT NOT NULL,

FOREIGN KEY(user_id) REFERENCES user(id)
ON DELETE CASCADE
);

INSERT INTO user (name) VALUES ('Maria');
INSERT INTO user (name) VALUES ('Belen');
INSERT INTO user (name) VALUES ('Juan');

INSERT INTO expense (description, amount, expense_date, user_id) VALUES ('Compra de material 1', 15.00, '2024-04-29 17:56:32.450', 1);
INSERT INTO expense (description, amount, expense_date, user_id) VALUES ('Compra de material 2', 150.00, '2024-05-1 18:30:32.450', 2);
INSERT INTO expense (description, amount, expense_date, user_id) VALUES ('Compra de material 3', 10.00, '2024-05-5 17:01:32.450', 3);