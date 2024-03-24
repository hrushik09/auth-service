CREATE TABLE clients
(
    id                           INT                          NOT NULL AUTO_INCREMENT,
    pid                          VARCHAR(45)                  NOT NULL,
    client_id                    VARCHAR(45)                  NOT NULL,
    client_secret                VARCHAR(100)                 NOT NULL,
    client_authentication_method ENUM ('CLIENT_SECRET_BASIC') NOT NULL,
    scope                        VARCHAR(45)                  NOT NULL,
    redirect_uri                 VARCHAR(100)                 NOT NULL,
    authorization_grant_type     VARCHAR(45)                  NOT NULL,
    created_at                   TIMESTAMP                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                   TIMESTAMP                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_clients PRIMARY KEY (id),
    CONSTRAINT UK_clients_pid UNIQUE (pid),
    CONSTRAINT UK_clients_client_id UNIQUE (client_id)
);