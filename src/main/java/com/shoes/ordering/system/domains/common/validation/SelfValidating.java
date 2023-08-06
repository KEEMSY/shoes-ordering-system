package com.shoes.ordering.system.domains.common.validation;

import javax.validation.*;
import java.util.Set;

public abstract class SelfValidating<T> {

    private final Validator validator;
    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    protected void validateSelf(T instance) {
        Set<ConstraintViolation<T>> violations = validator.validate(instance);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
