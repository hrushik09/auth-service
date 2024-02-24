package io.hrushik09.authservice.authorities.exceptions;

public class AuthorityAlreadyExists extends RuntimeException {
    public AuthorityAlreadyExists(String name) {
        super("Authority with name " + name + " already exists");
    }
}
