-- liquibase formatted sql

CREATE TABLE stations (
                         id SERIAL PRIMARY KEY,
                         station VARCHAR(50) NOT NULL
);