package com.foro_hub.Reto.foro.hub.validaciones.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;

public interface ValidadorRespuestas {
    void validar(DatosRegistroRespuesta datos, String loginUsuario);
}
