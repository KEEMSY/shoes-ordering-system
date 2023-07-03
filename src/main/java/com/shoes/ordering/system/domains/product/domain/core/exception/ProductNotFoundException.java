package com.shoes.ordering.system.domains.product.domain.core.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
