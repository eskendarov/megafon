DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id       INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    login    VARCHAR(2000)                  NOT NULL,
    password VARCHAR(2000)                  NOT NULL
);
