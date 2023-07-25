package com.mikaelovi.mdbtask.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public ResponseExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<ErrorPayload> handleBadRequestException(BadRequestException exception, Locale locale) {
        String messageCode = exception.getMessage();
        Object[] args = exception.getArgs();

        String message = messageSource.getMessage(messageCode, args, locale);
        return ResponseEntity.badRequest().body(new ErrorPayload(String.valueOf(HttpStatus.BAD_REQUEST.value()), message));
    }

}
