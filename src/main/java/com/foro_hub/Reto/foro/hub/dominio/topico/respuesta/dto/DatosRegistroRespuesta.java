package com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotNull Long idTopico,
        @NotNull Long idUsuario,
        @NotBlank String mensajeSolucion


) {
    public Long getId() {
        return null;
    }

    public String getTexto() {
        return null;
    }
}
