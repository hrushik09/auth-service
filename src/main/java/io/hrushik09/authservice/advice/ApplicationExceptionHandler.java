package io.hrushik09.authservice.advice;

import io.hrushik09.authservice.authorities.exceptions.AuthorityAlreadyExists;
import io.hrushik09.authservice.authorities.exceptions.AuthorityDoesNotExist;
import io.hrushik09.authservice.clients.exceptions.ClientIdAlreadyExistsException;
import io.hrushik09.authservice.clients.exceptions.PidAlreadyExistsException;
import io.hrushik09.authservice.users.exceptions.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleMethodArgumentsNotValid(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .collect(groupingBy(FieldError::getField,
                        mapping(FieldError::getDefaultMessage, toList())));
    }

    @ExceptionHandler({AuthorityAlreadyExists.class, UsernameAlreadyExistsException.class, PidAlreadyExistsException.class, ClientIdAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAlreadyExists(RuntimeException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(AuthorityDoesNotExist.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAuthorityDoesNotExist(AuthorityDoesNotExist e) {
        return Map.of("error", e.getMessage());
    }
}
