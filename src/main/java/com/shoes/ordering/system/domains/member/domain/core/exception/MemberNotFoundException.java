package com.shoes.ordering.system.domains.member.domain.core.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class MemberNotFoundException extends DomainException {
    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
