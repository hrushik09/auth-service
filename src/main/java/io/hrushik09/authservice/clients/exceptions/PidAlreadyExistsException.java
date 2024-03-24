package io.hrushik09.authservice.clients.exceptions;

public class PidAlreadyExistsException extends RuntimeException {
    public PidAlreadyExistsException(String pid) {
        super("Client with pid " + pid + " already exists");
    }
}
