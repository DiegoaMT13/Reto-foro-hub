package com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto;

import com.foro_hub.Reto.foro.hub.dominio.curso.CategoriaCurso;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.StatusPregunta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotNull Long idUsuario,
        @NotNull CategoriaCurso curso,
        @NotBlank String titulo,
        @NotNull StatusPregunta statusPregunta,
        @NotBlank String mensaje
) {
}
