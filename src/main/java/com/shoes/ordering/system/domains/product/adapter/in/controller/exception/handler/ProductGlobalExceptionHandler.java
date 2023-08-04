package com.shoes.ordering.system.domains.product.adapter.in.controller.exception.handler;

import com.shoes.ordering.system.common.handler.ErrorDTO;
import com.shoes.ordering.system.common.handler.GlobalExceptionHandler;
import com.shoes.ordering.system.domains.product.domain.application.dto.ProductDTOException;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductDomainException;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProductGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({ProductDomainException.class, ProductDTOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleProductException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleProductNotFoundException(ProductNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }
}
