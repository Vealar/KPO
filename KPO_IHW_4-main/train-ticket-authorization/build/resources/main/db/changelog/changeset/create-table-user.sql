-- liquibase formatted sql
CREATE TABLE users (
                        id SERIAL PRIMARY KEY,
                        nickname VARCHAR(50) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);