ALTER TABLE clients
    DROP COLUMN authorization_grant_type;

CREATE TABLE clients_authorization_grant_types
(
    id         INT                                          NOT NULL AUTO_INCREMENT,
    value      ENUM ('AUTHORIZATION_CODE', 'REFRESH_TOKEN') NOT NULL,
    client_id  INT                                          NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_clients_authorization_grant_types PRIMARY KEY (id),
    CONSTRAINT UK_clients_authorization_grant_types_value_client_id UNIQUE (value, client_id),
    CONSTRAINT FK_clients_authorization_grant_types_client_id FOREIGN KEY (client_id) REFERENCES clients (id) ON UPDATE CASCADE ON DELETE CASCADE
);