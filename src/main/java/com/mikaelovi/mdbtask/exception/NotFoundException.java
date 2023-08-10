package com.mikaelovi.mdbtask.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final Object[] args;

    private final String message;

    public NotFoundException(String message, Object[] args) {
        super(message);
        this.message = message;
        this.args = args;
    }
}
