DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(2000) NOT NULL,
    password VARCHAR(2000) NOT NULL
);
