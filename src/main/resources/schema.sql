drop table if exists users;
drop table if exists confirmation_tokens;

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE  confirmation_tokens
(
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE ,
    user_id BIGSERIAL NOT NULL
)