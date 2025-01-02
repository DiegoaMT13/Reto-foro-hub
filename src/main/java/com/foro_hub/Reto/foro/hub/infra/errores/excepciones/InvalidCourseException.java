package com.foro_hub.Reto.foro.hub.infra.errores.excepciones;

public class InvalidCourseException extends RuntimeException {
    public InvalidCourseException(String message) {
        super(message);
    }
}
