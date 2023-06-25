package com.shoes.ordering.system.domains.member.domain.core.exception;

import com.shoes.ordering.system.domains.common.exception.DomainException;

public class MemberDomainException extends DomainException {
    public MemberDomainException(String message) {
        super(message);
    }

    public MemberDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
