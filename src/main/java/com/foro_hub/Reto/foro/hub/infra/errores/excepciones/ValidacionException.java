package com.foro_hub.Reto.foro.hub.infra.errores.excepciones;

public class ValidacionException extends RuntimeException {
    public ValidacionException(String message) {
        super(message);
    }
}
