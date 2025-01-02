package com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto;

import com.foro_hub.Reto.foro.hub.dominio.curso.CategoriaCurso;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.StatusPregunta;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        Long idUsuario,
        String nombre,
        String perfil,
        CategoriaCurso curso,
        String titulo,
        StatusPregunta statusPregunta,
        String mensaje,
        LocalDateTime fechaCreacion

) {
}
