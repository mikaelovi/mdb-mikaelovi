package com.mikaelovi.mdbtask.exception;

public class EntityNotFoundException extends BadRequestException{

    public EntityNotFoundException(String param) {
        super("not-found", new Object[]{param} );
    }
}
