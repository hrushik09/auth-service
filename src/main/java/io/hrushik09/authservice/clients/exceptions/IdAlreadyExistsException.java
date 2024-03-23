package io.hrushik09.authservice.clients.exceptions;

public class IdAlreadyExistsException extends RuntimeException {
    public IdAlreadyExistsException(String id) {
        super("Client with id " + id + " already exists");
    }
}
