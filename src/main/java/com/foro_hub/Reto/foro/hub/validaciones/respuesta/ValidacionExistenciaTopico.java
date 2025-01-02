package com.foro_hub.Reto.foro.hub.validaciones.respuesta;


import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.TopicoRepository;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionExistenciaTopico implements ValidadorRespuestas {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosRegistroRespuesta datos, String loginUsuario) {
        if (!topicoRepository.existsById(datos.idTopico())) {
            throw new ValidacionException("El tópico asociado no existe.");
        }
    }

}
