-- liquibase formatted sql

-- changeset postgres:14
create table cuisine
(
    id          UUID primary key,
    name        varchar(255),
    price       int,
    description varchar(255),
    time        int
);

-- changeset postgres:6
alter table cuisine
    owner to postgres;
