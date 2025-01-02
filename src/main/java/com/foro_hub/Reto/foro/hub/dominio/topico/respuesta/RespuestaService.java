package com.foro_hub.Reto.foro.hub.dominio.topico.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosListarRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.TopicoRepository;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.validaciones.respuesta.ValidadorRespuestas;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final List<ValidadorRespuestas> validadores;

    // Eliminar la inyección del Usuario en el constructor
    public RespuestaService(RespuestaRepository respuestaRepository,
                            TopicoRepository topicoRepository,
                            UsuarioRepository usuarioRepository,
                            List<ValidadorRespuestas> validadores) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.validadores = validadores;
    }

    @Transactional
    public DatosRespuesta registrarRespuesta(DatosRegistroRespuesta datos, String loginUsuario) {
        // Ejecutar validaciones
        validadores.forEach(v -> v.validar(datos, loginUsuario));

        // Obtener el tópico
        Topico topico = topicoRepository.findById(datos.idTopico())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        // Obtener el usuario desde el repositorio (no lo inyectamos en el constructor)
        Usuario usuario = usuarioRepository.findById(datos.idUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Validar si el login del usuario coincide con el proporcionado
        if (!usuario.getLogin().equals(loginUsuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario no autorizado.");
        }

        // Crear y guardar la respuesta
        Respuesta respuesta = new Respuesta(datos, topico, usuario, true);
        respuesta.setMensajeSolucion(datos.mensajeSolucion());
        respuestaRepository.save(respuesta);

        return new DatosRespuesta(
                respuesta.getId(),
                respuesta.getTopico().getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                respuesta.getUsuario().getNombre(),
                respuesta.getFechaCreacion(),
                respuesta.getMensajeSolucion()
        );
    }


}

