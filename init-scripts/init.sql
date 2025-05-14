CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    money NUMERIC(15, 2)
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    cost NUMERIC(15, 2),
    is_available BOOLEAN,
    customer_id INTEGER,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

INSERT INTO public.customer (name, email, money) VALUES
('John Doe', 'john.doe@example.com', 1000.00),
('Jane Smith', 'jane.smith@example.com', 1500.50);

INSERT INTO public.product (name, cost, is_available, customer_id) VALUES
('Product A', 100.00, TRUE, 1),
('Product B', 200.00, FALSE, 1),
('Product C', 150.00, TRUE, 2),
('Product D', 250.00, TRUE, 2);
