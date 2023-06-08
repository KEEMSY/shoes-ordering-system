package com.shoes.ordering.system.domain.member.domain.core.exception;

import com.shoes.ordering.system.domain.common.exception.DomainException;

public class MemberNotFoundException extends DomainException {
    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
