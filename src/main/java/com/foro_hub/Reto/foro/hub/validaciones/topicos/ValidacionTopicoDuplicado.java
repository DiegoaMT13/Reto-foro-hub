package com.foro_hub.Reto.foro.hub.validaciones.topicos;

import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.TopicoRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto.DatosRegistroTopico;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionTopicoDuplicado implements ValidadorTopicos {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        boolean topicoDuplicado = topicoRepository.existsByTituloAndUsuarioId(datos.titulo(), datos.idUsuario());
        if (topicoDuplicado) {
            throw new ValidacionException("Ya existe un tópico con este título para el usuario.");
        }

    }
}
