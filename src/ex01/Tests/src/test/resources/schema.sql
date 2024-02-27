CREATE SCHEMA test_database;
CREATE TABLE product_tbl (
    id INT NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY (id)
);