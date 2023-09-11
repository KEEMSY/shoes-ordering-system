package com.shoes.ordering.system.domains.order.adapter.in.controller.exception.handler;

import com.shoes.ordering.system.common.handler.ErrorDTO;
import com.shoes.ordering.system.common.handler.GlobalExceptionHandler;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderDomainException;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice(basePackages = "com.shoes.ordering.system.domains.order.adapter.in.controller.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({OrderDomainException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleOrderException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleOrderNotFoundException(OrderNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }
}
