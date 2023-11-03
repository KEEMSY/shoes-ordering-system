package com.shoes.ordering.system.domains.payment.domain.application.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class PaymentApplicationServiceException extends DomainException {
    public PaymentApplicationServiceException(String message) {
        super(message);
    }

    public PaymentApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
