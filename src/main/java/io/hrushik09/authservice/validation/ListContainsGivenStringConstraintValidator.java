package io.hrushik09.authservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ListContainsGivenStringConstraintValidator implements ConstraintValidator<ListContainsGivenStringConstraint, List<String>> {
    private String required;

    @Override
    public void initialize(ListContainsGivenStringConstraint constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        return list != null && list.contains(required);
    }
}
