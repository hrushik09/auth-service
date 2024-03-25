package io.hrushik09.authservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class UniqueEntriesConstraintValidator implements ConstraintValidator<UniqueEntriesConstraint, List<String>> {
    @Override
    public boolean isValid(List<String> authorities, ConstraintValidatorContext context) {
        return authorities.size() == new HashSet<>(authorities).size();
    }
}
