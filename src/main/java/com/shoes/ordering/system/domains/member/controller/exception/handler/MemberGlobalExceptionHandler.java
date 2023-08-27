package com.shoes.ordering.system.domains.member.controller.exception.handler;

import com.shoes.ordering.system.common.handler.ErrorDTO;
import com.shoes.ordering.system.common.handler.GlobalExceptionHandler;
import com.shoes.ordering.system.domains.member.domain.core.exception.MemberDomainException;
import com.shoes.ordering.system.domains.member.domain.core.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(basePackages = "com.shoes.ordering.system.domains.member.controller.rest")
public class MemberGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {MemberDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handlerException(MemberDomainException memberDomainException) {
        log.error(memberDomainException.getMessage(), memberDomainException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(memberDomainException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {MemberNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handlerException(MemberNotFoundException memberNotFoundException) {
        log.error(memberNotFoundException.getMessage(), memberNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(memberNotFoundException.getMessage())
                .build();
    }
}
