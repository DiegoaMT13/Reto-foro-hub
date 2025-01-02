package com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto;

import com.foro_hub.Reto.foro.hub.dominio.curso.CategoriaCurso;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosListarRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.StatusPregunta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;

import java.util.List;


public record DatosListarTopicos(
        Long id,
        String titulo,
        CategoriaCurso curso,
        String nombreUsuario,
        String perfilUsuario,
        StatusPregunta statusPregunta,
        String mensaje,
        List<DatosListarRespuesta> respuestas
) {
    public DatosListarTopicos(Topico topico, List<DatosListarRespuesta> respuestas) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getCurso(),
                topico.getUsuario().getNombre(),
                topico.getUsuario().getPerfil(),
                topico.getStatusPregunta(),
                topico.getMensaje(),
                respuestas
        );
    }



}
