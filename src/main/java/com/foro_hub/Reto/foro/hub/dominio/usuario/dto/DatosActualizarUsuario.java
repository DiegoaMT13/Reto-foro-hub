package com.foro_hub.Reto.foro.hub.dominio.usuario.dto;

import com.foro_hub.Reto.foro.hub.dominio.direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;


public record DatosActualizarUsuario(
        @NotNull
        Long id,
        String nombre,
        String documento,
        String email,
        String telefono,
        DatosDireccion direccion,
        String perfil,
        String login,
        String clave,
        String fotoUrl
) {

}
