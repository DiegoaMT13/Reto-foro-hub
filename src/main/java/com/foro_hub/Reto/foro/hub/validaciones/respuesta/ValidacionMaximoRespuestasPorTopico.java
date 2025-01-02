package com.foro_hub.Reto.foro.hub.validaciones.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.RespuestaRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMaximoRespuestasPorTopico implements ValidadorRespuestas{

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public void validar(DatosRegistroRespuesta datos, String usuarioAutenticado) {
        long respuestasPorUsuarioYTopico = respuestaRepository.countByUsuarioIdAndTopicoId(
                datos.idUsuario(), datos.idTopico());

        if (respuestasPorUsuarioYTopico >= 2) {
            throw new ValidacionException("El usuario ya ha registrado el máximo de 2 respuestas en este tópico.");
        }
    }
}
