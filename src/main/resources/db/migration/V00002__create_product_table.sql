CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    cost NUMERIC(15, 2),
    is_available BOOLEAN,
    customer_id INTEGER,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);