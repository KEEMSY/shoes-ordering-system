package com.shoes.ordering.system.domains.product.domain.core.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class ProductDomainException extends DomainException {
    public ProductDomainException(String message) {
        super(message);
    }

    public ProductDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
