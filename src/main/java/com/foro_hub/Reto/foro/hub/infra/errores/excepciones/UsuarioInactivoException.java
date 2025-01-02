package com.foro_hub.Reto.foro.hub.infra.errores.excepciones;

public class UsuarioInactivoException extends RuntimeException {
    public UsuarioInactivoException(String mensaje) {
        super(mensaje);
    }
}

