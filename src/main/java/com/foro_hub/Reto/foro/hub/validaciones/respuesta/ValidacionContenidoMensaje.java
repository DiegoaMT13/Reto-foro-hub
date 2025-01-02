package com.foro_hub.Reto.foro.hub.validaciones.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.ValidacionException;
import org.springframework.stereotype.Component;

@Component
public class ValidacionContenidoMensaje implements ValidadorRespuestas {
    private static final int MAX_LONGITUD = 500;

    @Override
    public void validar(DatosRegistroRespuesta datos, String loginUsuario) {
        if (datos.mensajeSolucion().length() > MAX_LONGITUD) {
            throw new ValidacionException("El mensaje excede la longitud máxima permitida.");
        }
    }
}
