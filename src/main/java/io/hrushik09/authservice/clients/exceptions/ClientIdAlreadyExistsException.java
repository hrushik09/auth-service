package io.hrushik09.authservice.clients.exceptions;

public class ClientIdAlreadyExistsException extends RuntimeException {
    public ClientIdAlreadyExistsException(String clientId) {
        super("Client with clientId " + clientId + " already exists");
    }
}
