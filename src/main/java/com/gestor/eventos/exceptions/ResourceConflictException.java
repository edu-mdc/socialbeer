package com.gestor.eventos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException(String mensaje) {
        super(mensaje);
    }
}
