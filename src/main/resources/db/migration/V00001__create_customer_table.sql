CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    money NUMERIC(15, 2)
);