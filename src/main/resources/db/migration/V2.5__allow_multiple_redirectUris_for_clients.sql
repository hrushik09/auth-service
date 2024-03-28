ALTER TABLE clients
    DROP COLUMN redirect_uri;

CREATE TABLE clients_redirect_uris
(
    id         INT          NOT NULL AUTO_INCREMENT,
    value      VARCHAR(100) NOT NULL,
    client_id  INT          NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_clients_redirect_uris PRIMARY KEY (id),
    CONSTRAINT UK_clients_redirect_uris_value_client_id UNIQUE (value, client_id),
    CONSTRAINT FK_clients_redirect_uris_client_id FOREIGN KEY (client_id) REFERENCES clients (id) ON UPDATE CASCADE ON DELETE CASCADE
);