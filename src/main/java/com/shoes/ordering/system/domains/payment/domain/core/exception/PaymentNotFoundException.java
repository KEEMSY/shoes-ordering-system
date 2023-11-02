package com.shoes.ordering.system.domains.payment.domain.core.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class PaymentNotFoundException extends DomainException {
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
