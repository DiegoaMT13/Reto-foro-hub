package com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;

import java.time.LocalDateTime;
import java.util.List;

public record DatosListarRespuesta(
        Long id,
        String mensajeSolucion,
        String nombreUsuario,
        String perfilUsuario,
        String tituloTopico,
        LocalDateTime fechaCreacion
) {
    public DatosListarRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensajeSolucion(),
                respuesta.getUsuario().getNombre(),
                respuesta.getUsuario().getPerfil(),
                respuesta.getTopico().getTitulo(),
                respuesta.getFechaCreacion()

        );
    }

}
