package com.foro_hub.Reto.foro.hub.validaciones.usuarios;

import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosRegistroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionEmailDuplicado implements ValidadorUsuarios {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DatosRegistroUsuario datos) {
        if (usuarioRepository.existsByEmail(datos.email())) {
            throw new ValidacionException("El correo electrónico ya está registrado.");
        }
    }
}
