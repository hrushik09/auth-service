package io.hrushik09.authservice.clients.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumConstraintValidator implements ConstraintValidator<ValueOfEnumConstraint, String> {
    private Set<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnumConstraint constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return acceptedValues.contains(value);
    }
}
