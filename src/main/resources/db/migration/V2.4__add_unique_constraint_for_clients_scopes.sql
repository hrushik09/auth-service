ALTER TABLE clients_scopes
    ADD CONSTRAINT UK_clients_scopes_value_client_id UNIQUE (value, client_id);