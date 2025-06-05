-- liquibase formatted sql

-- changeset postgres:13
create table order_dish
(
    order_id UUID not null,
    dish_id UUID not null,
    primary key (order_id, dish_id),
    foreign key (order_id) references orders(id),
    foreign key (dish_id) references cuisine(id)
);

-- changeset postgres:9
alter table order_dish
    owner to postgres;