package com.shoes.ordering.system.domains.product.domain.application.dto;

import com.shoes.ordering.system.domains.common.exception.DTOException;

public class ProductDTOException extends DTOException {
    public ProductDTOException(String message) {
        super(message);
    }

    public ProductDTOException(String message, Throwable cause) {
        super(message, cause);
    }
}
