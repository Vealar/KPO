-- liquibase formatted sql
CREATE TABLE session (
                         id SERIAL PRIMARY KEY,
                         user_id INT NOT NULL,
                         token VARCHAR(255) NOT NULL,
                         expires TIMESTAMP NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users(id)
);