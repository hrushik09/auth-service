ALTER TABLE clients
    CHANGE client_authentication_method authentication_method ENUM ('CLIENT_SECRET_BASIC') NOT NULL;