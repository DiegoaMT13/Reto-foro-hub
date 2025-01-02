package com.foro_hub.Reto.foro.hub.validaciones.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionUsuarioAutenticado implements ValidadorRespuestas {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DatosRegistroRespuesta datos, String loginUsuario) {
        usuarioRepository.findById(datos.idUsuario())
                .filter(usuario -> usuario.getLogin().equals(loginUsuario))
                .orElseThrow(() -> new ValidacionException("El usuario autenticado no coincide con el usuario asociado a la respuesta."));
    }
}
