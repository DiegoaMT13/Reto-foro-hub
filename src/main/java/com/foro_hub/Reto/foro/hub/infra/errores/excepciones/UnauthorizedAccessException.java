package com.foro_hub.Reto.foro.hub.infra.errores.excepciones;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
