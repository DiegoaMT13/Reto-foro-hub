package com.foro_hub.Reto.foro.hub.dominio.usuario.dto;

import com.foro_hub.Reto.foro.hub.dominio.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(

        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefono,
        @NotBlank
        String documento,
        @NotNull @Valid DatosDireccion direccion,
        @NotBlank
        String perfil,
        @NotBlank
        String login,
        @NotBlank
        String clave,
        String fotoUrl

) {



}
