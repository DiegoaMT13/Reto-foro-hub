package com.foro_hub.Reto.foro.hub.infra.errores.excepciones;


public class TopicoNotFoundException extends RuntimeException {
    public TopicoNotFoundException(String message) {
        super(message);
    }
}