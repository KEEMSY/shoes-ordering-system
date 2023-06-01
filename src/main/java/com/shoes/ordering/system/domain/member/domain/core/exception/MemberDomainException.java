package com.shoes.ordering.system.domain.member.domain.core.exception;

import com.shoes.ordering.system.domain.common.exception.DomainException;

public class MemberDomainException extends DomainException {
    public MemberDomainException(String message) {
        super(message);
    }

    public MemberDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
