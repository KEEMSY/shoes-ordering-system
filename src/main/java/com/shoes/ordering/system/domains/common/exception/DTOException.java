package com.shoes.ordering.system.domains.common.exception;

public class DTOException extends RuntimeException {

    public DTOException(String message) {
        super(message);
    }

    public DTOException(String message, Throwable cause) {
        super(message, cause);
    }
}
