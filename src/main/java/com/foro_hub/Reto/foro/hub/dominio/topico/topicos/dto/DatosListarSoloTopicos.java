package com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto;

import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;

import java.time.LocalDateTime;


public record DatosListarSoloTopicos(Long id,
                                     String nombreUsuario,
                                     String perfilUsuario,
                                     String tituloTopico,
                                     String mensaje,
                                     LocalDateTime fechaCreacion) {

    // Constructor para crear el DTO desde un objeto Topico
    public DatosListarSoloTopicos(Topico topico) {
        this(
                topico.getId(),
                topico.getUsuario().getNombre(),
                topico.getUsuario().getPerfil(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion()
        );
    }
}
