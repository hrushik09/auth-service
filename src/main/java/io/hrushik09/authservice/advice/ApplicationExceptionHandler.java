package io.hrushik09.authservice.advice;

import io.hrushik09.authservice.authorities.exceptions.AuthorityAlreadyExists;
import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(AuthorityAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAuthorityAlreadyExists(AuthorityAlreadyExists e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(AuthorityDoesNotExist.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAuthorityDoesNotExist(AuthorityDoesNotExist e) {
        return Map.of("error", e.getMessage());
    }
}
