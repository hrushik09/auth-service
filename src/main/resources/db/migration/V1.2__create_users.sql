CREATE TABLE users
(
    id         INT          NOT NULL AUTO_INCREMENT,
    username   VARCHAR(45)  NOT NULL,
    password   VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT UK_users_username UNIQUE (username)
);