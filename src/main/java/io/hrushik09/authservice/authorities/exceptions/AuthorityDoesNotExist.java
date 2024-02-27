package io.hrushik09.authservice.authorities.exceptions;

public class AuthorityDoesNotExist extends RuntimeException {
    public AuthorityDoesNotExist(Integer id) {
        super("Authority with id " + id + " does not exist");
    }

    public AuthorityDoesNotExist(String name) {
        super("Authority with name " + name + " does not exist");
    }
}
