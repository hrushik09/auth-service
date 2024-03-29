package io.hrushik09.authservice.clients.validation;

import io.hrushik09.authservice.clients.AuthorizationGrantType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class UniqueAuthorizationGrantTypesConstraintValidator implements ConstraintValidator<UniqueAuthorizationGrantTypesConstraint, List<AuthorizationGrantType>> {
    @Override
    public boolean isValid(List<AuthorizationGrantType> list, ConstraintValidatorContext context) {
        return list != null && list.size() == new HashSet<>(list).size();
    }
}
