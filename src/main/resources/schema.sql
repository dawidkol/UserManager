drop table if exists users;

create table users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL
);