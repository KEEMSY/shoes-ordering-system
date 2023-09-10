package com.shoes.ordering.system.domains.order.domain.core.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
