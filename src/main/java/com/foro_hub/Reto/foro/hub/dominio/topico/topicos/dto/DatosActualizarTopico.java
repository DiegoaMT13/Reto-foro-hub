package com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto;

import com.foro_hub.Reto.foro.hub.dominio.curso.CategoriaCurso;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.StatusPregunta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull Long id,
        @NotBlank String titulo,
        @NotNull CategoriaCurso curso,
        @NotBlank String mensaje,
        @NotNull StatusPregunta statusPregunta
) {
}
