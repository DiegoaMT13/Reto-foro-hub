package com.foro_hub.Reto.foro.hub.validaciones.usuarios;

import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosRegistroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaciionUsuarioActivo implements ValidadorUsuarios {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DatosRegistroUsuario datos) {
        if (datos.documento() == null) {
            throw new ValidacionException("El ID del usuario no puede ser nulo.");
        }

        // Verificar si el usuario ya existe en la base de datos
        var usuarioExistente = usuarioRepository.findById(Long.valueOf(datos.documento()));

        if (usuarioExistente.isEmpty()) {
            // Permitir el registro si el usuario no existe
            return;
        }

        // Verificar si el usuario está activo
        Boolean estaActivo = usuarioExistente.map(u -> u.getActivo())
                .orElseThrow(() -> new ValidacionException("Usuario no encontrado."));

        if (!estaActivo) {
            throw new ValidacionException("El usuario no está activo.");
        }
    }
}
