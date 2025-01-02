package com.foro_hub.Reto.foro.hub.dominio.usuario.dto;


import com.foro_hub.Reto.foro.hub.dominio.direccion.DatosDireccion;

public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion,
        String perfil,
        String login,
        String clave,
        String fotoUrl
) {
}
