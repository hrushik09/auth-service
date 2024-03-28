package io.hrushik09.authservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class UniqueEntriesConstraintValidator implements ConstraintValidator<UniqueEntriesConstraint, List<String>> {
    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        return list != null && list.size() == new HashSet<>(list).size();
    }
}
