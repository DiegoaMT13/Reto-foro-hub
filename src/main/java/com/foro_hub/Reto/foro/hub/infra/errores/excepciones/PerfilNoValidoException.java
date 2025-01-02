package com.foro_hub.Reto.foro.hub.infra.errores.excepciones;

public class PerfilNoValidoException extends RuntimeException {
  public PerfilNoValidoException(String message) {
    super(message);
  }
}
