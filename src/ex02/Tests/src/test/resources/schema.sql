CREATE SCHEMA test_database;

CREATE TABLE test_database.product_tbl (
    id INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY (id)
);