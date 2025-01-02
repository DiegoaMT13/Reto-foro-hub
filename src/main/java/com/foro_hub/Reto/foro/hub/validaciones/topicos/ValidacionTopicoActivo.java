package com.foro_hub.Reto.foro.hub.validaciones.topicos;

import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.TopicoRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto.DatosRegistroTopico;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidacionTopicoActivo implements ValidadorTopicos {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public void validar(DatosRegistroTopico datos) {

        if (datos.idUsuario() == null) {
            return;
        }

        Optional<Boolean> usuarioEstaActivo = usuarioRepository.findActivoById(datos.idUsuario());

        if (usuarioEstaActivo.isEmpty() || !usuarioEstaActivo.get()) {
            throw new ValidacionException("Usuario excluido seleccione otro");
        }
    }
}
