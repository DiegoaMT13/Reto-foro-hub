package com.foro_hub.Reto.foro.hub.validaciones.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.RespuestaRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionRespuestaDuplicada implements ValidadorRespuestas {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public void validar(DatosRegistroRespuesta datos, String usuarioAutenticado) {
        boolean respuestaDuplicada = respuestaRepository.existsByMensajeSolucionAndUsuarioIdAndTopicoId(
                datos.mensajeSolucion(), datos.idUsuario(), datos.idTopico());

        if (respuestaDuplicada) {
            throw new ValidacionException("Ya existe una respuesta idéntica para este usuario en el tópico.");
        }
    }
}
