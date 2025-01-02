package com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosRespuesta(
        Long id,
        Long idTopico,
        String mensaje,
        String topicoMensaje,
        String nombre,
        LocalDateTime fechaCreacion,
        String mensajeSolucion
) {
    public DatosRespuesta(Respuesta respuesta, long idTopico, String preguntaProbandoCursoError, String topicoMensaje, String lauraLopez, String fecha, String mensajeSolucion) {
        this(
                respuesta.getId(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo(),
                respuesta.getTopico().getMensaje(),
                respuesta.getUsuario().getNombre(),
                respuesta.getFechaCreacion(),
                respuesta.getMensajeSolucion()
        );
    }


}
