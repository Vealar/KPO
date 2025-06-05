--liquibase formatted sql

--changeset postgres:12


create table users
(
    id       UUID not null primary key,
    name     varchar(255),
    email    varchar(255) not null unique ,
    password varchar(255),
    role   varchar(255),
    status varchar(255)
);
alter table users
    owner to postgres;
