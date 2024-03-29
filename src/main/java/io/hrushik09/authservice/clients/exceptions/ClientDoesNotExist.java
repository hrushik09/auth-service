package io.hrushik09.authservice.clients.exceptions;

public class ClientDoesNotExist extends RuntimeException {
    public ClientDoesNotExist(String message) {
        super(message);
    }
}
