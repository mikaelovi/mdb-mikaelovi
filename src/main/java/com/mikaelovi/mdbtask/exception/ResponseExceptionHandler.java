package com.mikaelovi.mdbtask.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {
    private final MessageSource messageSource;

    public ResponseExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<ErrorPayload> handleNotFoundException(NotFoundException exception, Locale locale) {
        String messageCode = exception.getMessage();
        Object[] args = exception.getArgs();

        String message = messageSource.getMessage(messageCode, args, locale);

        return ResponseEntity.badRequest().body(new ErrorPayload(String.valueOf(HttpStatus.NOT_FOUND.value()), message));
    }

}
