--liquibase formatted sql

--changeset postgres:11
create table orders
(
    id UUID not null primary key,
    client_email varchar(255),
    total_price int,
    status varchar(255)
);
alter table orders
    owner to postgres;
