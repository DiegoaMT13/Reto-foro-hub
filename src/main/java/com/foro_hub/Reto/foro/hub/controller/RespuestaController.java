package com.foro_hub.Reto.foro.hub.controller;



import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.RespuestaRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosListarRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRegistroRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.TopicoRepository;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.validaciones.respuesta.ValidadorRespuestas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {


    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private List<ValidadorRespuestas> validadores;



    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos,
                                                UriComponentsBuilder uriBuilder,
                                                Authentication authentication) {
        try {
            // Ejecutamos todos los validadores, incluyendo el de respuestas duplicadas y el de máximo de respuestas
            validadores.forEach(v -> v.validar(datos, authentication.getName()));

            // Obtener el usuario autenticado
            String loginUsuarioAutenticado = authentication.getName();

            // Obtenemos el tópico relacionado
            Topico topico = topicoRepository.findById(datos.idTopico())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

            // Obtenemos el usuario relacionado
            Usuario usuario = usuarioRepository.findById(datos.idUsuario())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            // Verificamos que el usuario autenticado coincida con el usuario que desea registrar la respuesta
            if (!usuario.getLogin().equals(loginUsuarioAutenticado)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permisos. Usuario incorrecto.");
            }

            // Creamos la nueva respuesta usando el constructor
            Respuesta respuesta = new Respuesta(datos, topico, usuario, true);

            // Establecemos el mensaje de solución si está presente
            respuesta.setMensajeSolucion(datos.mensajeSolucion());

            // Guardamos la respuesta en el repositorio
            respuestaRepository.save(respuesta);

            // Creamos la URI para la nueva respuesta
            URI uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

            return ResponseEntity.created(uri).body(new DatosRespuesta(
                    respuesta.getId(),
                    respuesta.getTopico().getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    respuesta.getUsuario().getNombre(),
                    respuesta.getFechaCreacion(),
                    respuesta.getMensajeSolucion()
            ));
        } catch (Exception e) {
            System.err.println("Error al registrar respuesta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar la respuesta.");
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<Page<DatosListarRespuesta>> listarRespuestas(
            @PageableDefault(page = 0, size = 10, sort = {"fechaCreacion"}) Pageable paginacion,
            Authentication authentication) {
        try {
            // Obtener el login del usuario autenticado
            String loginUsuario = authentication.getName();
            System.out.println("Usuario autenticado: " + loginUsuario);

            // Filtrar las respuestas activas asociadas al usuario autenticado
            Page<Respuesta> respuestas = respuestaRepository.findAllByActivoTrueAndUsuarioLogin(loginUsuario, paginacion);
            System.out.println("Respuestas encontradas: " + respuestas.getContent());

            // Convertir a DTO
            Page<DatosListarRespuesta> datosListarRespuestas = respuestas.map(DatosListarRespuesta::new);

            return ResponseEntity.ok(datosListarRespuestas);
        } catch (Exception e) {
            System.err.println("Error al listar respuestas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }


    @GetMapping("/topico/{idTopico}")
    public ResponseEntity<Page<DatosListarRespuesta>> listarRespuestasPorTopico(
            @PathVariable Long idTopico, Pageable pageable) {

        // Verificar si el tópico existe
        Topico topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        // Obtener las respuestas activas asociadas al tópico con paginación
        Page<Respuesta> respuestas = respuestaRepository.findRespuestasByTopicoIdAndActivoTrue(idTopico, pageable);

        // Convertir las respuestas a DTO (DatosListarRespuesta)
        Page<DatosListarRespuesta> respuestaDTOs = respuestas.map(DatosListarRespuesta::new);

        return ResponseEntity.ok(respuestaDTOs);
    }

    @DeleteMapping("/topico/{idTopico}/respuesta/{idRespuesta}")
    @Transactional
    public ResponseEntity<?> desactivarRespuesta(@PathVariable Long idTopico,
                                                 @PathVariable Long idRespuesta,
                                                 Authentication authentication) {
        // Obtener el login del usuario autenticado
        String loginUsuario = authentication.getName();

        // Buscar la respuesta por su ID
        Respuesta respuesta = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada"));

        // Verificar que la respuesta esté asociada al tópico
        if (!respuesta.getTopico().getId().equals(idTopico)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La respuesta no está asociada al tópico especificado");
        }

        // Verificar si el usuario autenticado es ADMINISTRADOR o el creador de la respuesta
        boolean esAdministrador = authentication.getAuthorities().stream()
                .anyMatch(rol -> rol.getAuthority().equals("ROLE_ADMINISTRADOR"));

        if (!esAdministrador && !respuesta.getUsuario().getLogin().equals(loginUsuario)) {
            // Si el usuario no tiene permisos, devuelve una respuesta personalizada
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para desactivar esta respuesta. Usuario incorrecto.");
        }

        // Desactivar la respuesta
        respuesta.desactivar();

        // Guardar los cambios
        respuestaRepository.save(respuesta);

        // Retornar una respuesta exitosa
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/topico/{idTopico}/respuesta/{idRespuesta}")
    @Transactional
    public ResponseEntity<?> activarRespuesta(@PathVariable Long idTopico,
                                              @PathVariable Long idRespuesta,
                                              Authentication authentication) {
        // Obtener el login del usuario autenticado
        String loginUsuario = authentication.getName();

        // Buscar la respuesta por su ID
        Respuesta respuesta = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada"));

        // Verificar que la respuesta esté asociada al tópico
        if (!respuesta.getTopico().getId().equals(idTopico)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La respuesta no está asociada al tópico especificado");
        }

        // Verificar si el usuario autenticado es ADMINISTRADOR o el creador de la respuesta
        boolean esAdministrador = authentication.getAuthorities().stream()
                .anyMatch(rol -> rol.getAuthority().equals("ROLE_ADMINISTRADOR"));

        if (!esAdministrador && !respuesta.getUsuario().getLogin().equals(loginUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para activar esta respuesta. Usuario incorrecto.");
        }

        // Activar la respuesta
        respuesta.setActivo(true);

        // Guardar los cambios
        respuestaRepository.save(respuesta);

        // Retornar una respuesta exitosa
        return ResponseEntity.noContent().build();
    }



}
