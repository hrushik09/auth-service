ALTER TABLE clients
    DROP COLUMN scope;

CREATE TABLE client_scopes
(
    id         INT         NOT NULL AUTO_INCREMENT,
    value      VARCHAR(45) NOT NULL,
    client_id  INT         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_client_scopes PRIMARY KEY (id),
    CONSTRAINT FK_client_scopes_client_id FOREIGN KEY (client_id) REFERENCES clients (id) ON UPDATE CASCADE ON DELETE CASCADE
);