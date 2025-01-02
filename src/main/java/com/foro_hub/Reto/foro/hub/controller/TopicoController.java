package com.foro_hub.Reto.foro.hub.controller;


import com.foro_hub.Reto.foro.hub.dominio.curso.Curso;
import com.foro_hub.Reto.foro.hub.dominio.curso.CursoRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.RespuestaService;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosListarRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.*;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.RespuestaRepository;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.dto.*;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.InvalidCourseException;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.TopicoNotFoundException;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.UnauthorizedAccessException;
import com.foro_hub.Reto.foro.hub.validaciones.topicos.ValidadorTopicos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {


    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private List<ValidadorTopicos> validadores;
    @Autowired
    private RespuestaService respuestaService;



    @Autowired
    public TopicoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroTopico datos,
                                       UriComponentsBuilder uriBuilder,
                                       Authentication authentication) {
        try {
            // Ejecutar todas las validaciones
            validadores.forEach(v -> v.validar(datos));

            // Obtener el usuario autenticado
            String loginUsuarioAutenticado = authentication.getName();

            // Buscar el usuario por ID y verificar si coincide con el autenticado
            Usuario usuario = usuarioRepository.findById(datos.idUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            if (!usuario.getLogin().equals(loginUsuarioAutenticado)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permisos. Usuario incorrecto.");
            }

            // Crear y guardar el Tópico con el curso indicado
            Topico topico = new Topico(datos, usuario);
            topico = topicoRepository.save(topico);

            // Crear y guardar el Curso relacionado
            Curso curso = new Curso(topico, topico.getCurso(), topico.getStatusPregunta());
            cursoRepository.save(curso);


            // Generar la URI de respuesta
            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

            return ResponseEntity.created(uri).body(new DatosRespuestaTopico(
                    topico.getId(),
                    topico.getUsuario().getId(),
                    topico.getUsuario().getNombre(),
                    topico.getUsuario().getPerfil(),
                    topico.getCurso(),
                    topico.getTitulo(),
                    topico.getStatusPregunta(),
                    topico.getMensaje(),
                    topico.getFechaCreacion()
            ));
        } catch (Exception e) {
            System.err.println("Error al registrar tópico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar el tópico " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar(
            @PageableDefault(page = 0, size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        try {
            // Obtiene todos los tópicos activos
            Page<Topico> topicosActivos = topicoRepository.findActiveTopics(paginacion);
            System.out.println("Tópicos encontrados: " + topicosActivos.getContent());

            // Mapea los tópicos a DatosListaTopico, incluyendo solo las respuestas activas
            Page<DatosListarTopicos> datosListaTopicos = topicosActivos.map(topico -> {
                // Filtra solo las respuestas activas para cada tópico
                List<Respuesta> respuestasActivas = respuestaRepository.findByTopicoAndActivoTrue(topico);
                // Mapea las respuestas activas a DatosListarRespuesta
                List<DatosListarRespuesta> respuestas = respuestasActivas.stream()
                        .map(DatosListarRespuesta::new)
                        .collect(Collectors.toList());

                // Devuelve un nuevo objeto DatosListaTopico con respuestas activas
                return new DatosListarTopicos(topico, respuestas);
            });

            return ResponseEntity.ok(datosListaTopicos);
        } catch (Exception e) {
            System.err.println("Error al listar tópicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al listar los tópicos."+  e.getMessage());
        }
    }
    @GetMapping("/topico")
    public ResponseEntity<?> listarSoloTopicos(
            @PageableDefault(page = 0, size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        try {
            // Obtiene todos los tópicos activos
            Page<Topico> topicosActivos = topicoRepository.findActiveTopics(paginacion);
            System.out.println("Tópicos encontrados: " + topicosActivos.getContent());

            // Mapea los tópicos a DatosListarSoloTopicos (sin el mensajeSolucion)
            Page<DatosListarSoloTopicos> datosListaSoloTopicos = topicosActivos.map(topico -> {
                // Mapea el tópico (sin mensajeSolucion) a DatosListarSoloTopicos
                return new DatosListarSoloTopicos(topico);
            });

            // Devuelve los datos mapeados de solo los tópicos
            return ResponseEntity.ok(datosListaSoloTopicos);
        } catch (Exception e) {
            System.err.println("Error al listar tópicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al listar los tópicos." + e.getMessage());
        }
    }


    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico,
                                              Authentication authentication) {
        try {
            // Obtenemos el nombre del usuario autenticado
            String loginUsuario = authentication.getName();
            System.out.println("Usuario autenticado: " + loginUsuario);

            // Obtenemos el tópico desde la base de datos
            Topico topico = topicoRepository.findById(datosActualizarTopico.id())
                    .orElseThrow(() -> new TopicoNotFoundException("Tópico no encontrado con ID: " + datosActualizarTopico.id()));

            // Validamos que el usuario autenticado sea el creador del tópico
            if (!topico.getUsuario().getLogin().equals(loginUsuario)) {
                throw new UnauthorizedAccessException("No tiene permisos para actualizar este tópico.");
            }

            // Validamos que el curso del tópico no cambie
            if (!topico.getCurso().equals(datosActualizarTopico.curso())) {
                throw new InvalidCourseException("No puede actualizar el tópico con un curso diferente al registrado.");
            }

            // Actualizamos los datos del tópico
            topico.setTitulo(datosActualizarTopico.titulo());
            topico.setCurso(datosActualizarTopico.curso());
            topico.setMensaje(datosActualizarTopico.mensaje());
            topico.setStatusPregunta(datosActualizarTopico.statusPregunta());

            // Creamos la respuesta con los datos actualizados
            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                    topico.getId(),
                    topico.getUsuario().getId(),
                    topico.getUsuario().getNombre(),
                    topico.getUsuario().getPerfil(),
                    topico.getCurso(),
                    topico.getTitulo(),
                    topico.getStatusPregunta(),
                    topico.getMensaje(),
                    topico.getFechaCreacion()
            );

            return ResponseEntity.ok(datosRespuestaTopico);
        } catch (TopicoNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (InvalidCourseException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al actualizar el tópico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("eliminar/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id, Authentication authentication) {
        try {
            // Obtener el login del usuario autenticado
            String loginUsuario = authentication.getName();

            // Obtener el tópico desde la base de datos
            Topico topico = topicoRepository.findById(id)
                    .orElseThrow(() -> new TopicoNotFoundException("Tópico no encontrado con ID: " + id));

            // Verificar si el tópico está inactivo
            if (!topico.isActivo()) {
                throw new IllegalStateException("El tópico ya está inactivo y no puede ser eliminado.");
            }

            // Verificar si el usuario autenticado es ADMINISTRADOR o es el creador del tópico
            boolean esAdministrador = authentication.getAuthorities().stream()
                    .anyMatch(rol -> rol.getAuthority().equals("ROLE_ADMINISTRADOR"));

            if (!esAdministrador && !topico.getUsuario().getLogin().equals(loginUsuario)) {
                throw new UnauthorizedAccessException("No tienes permiso para eliminar este tópico");
            }

            // Inactivar el tópico
            topico.inactivar();

            // Obtener y desactivar todas las respuestas asociadas al tópico
            List<Respuesta> respuestas = respuestaRepository.findByTopicoId(id);
            respuestas.forEach(Respuesta::desactivar);
            respuestaRepository.saveAll(respuestas);

            // Retornar una respuesta exitosa (sin contenido)
            return ResponseEntity.noContent().build();
        } catch (TopicoNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar el tópico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }




    @PutMapping("activar/{id}")
    @Transactional
    public ResponseEntity<?> activar(@PathVariable Long id) {
        try {
            // Obtenemos el tópico desde la base de datos
            Topico topico = topicoRepository.findById(id)
                    .orElseThrow(() -> new TopicoNotFoundException("Tópico no encontrado con ID: " + id));

            // Activamos el tópico
            topico.setActivo(true);

            // Obtenemos y activamos todas las respuestas asociadas al tópico
            List<Respuesta> respuestas = respuestaRepository.findByTopicoId(id);
            respuestas.forEach(respuesta -> respuesta.setActivo(true));
            respuestaRepository.saveAll(respuestas);

            // Retornamos una respuesta exitosa
            return ResponseEntity.ok().build();
        } catch (TopicoNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al activar el tópico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }







}
