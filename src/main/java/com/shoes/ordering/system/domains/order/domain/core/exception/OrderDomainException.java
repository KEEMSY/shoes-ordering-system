package com.shoes.ordering.system.domains.order.domain.core.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
