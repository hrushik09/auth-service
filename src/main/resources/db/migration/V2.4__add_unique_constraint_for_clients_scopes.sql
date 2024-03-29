ALTER TABLE client_scopes
    ADD CONSTRAINT UK_client_scopes_value_client_id UNIQUE (value, client_id);