package com.foro_hub.Reto.foro.hub.controller;


import com.foro_hub.Reto.foro.hub.dominio.direccion.DatosDireccion;
import com.foro_hub.Reto.foro.hub.dominio.usuario.*;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosActualizarUsuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosRegistroUsuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.dto.DatosRespuestaUsuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.Perfil;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.PerfilRepository;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.TipoPerfil;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.PerfilNoValidoException;
import com.foro_hub.Reto.foro.hub.infra.errores.excepciones.UsuarioNoEncontradoException;
import com.foro_hub.Reto.foro.hub.validaciones.usuarios.ValidadorUsuarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private List<ValidadorUsuarios> validadores;


    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> registrar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder) {

        try {
        validadores.forEach(v -> v.validar(datos));
        // Guardar al usuario primero
        Usuario usuario = usuarioRepository.save(new Usuario(datos));
            TipoPerfil tipoPerfil;
            try {
                tipoPerfil = TipoPerfil.valueOf(datos.perfil().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new PerfilNoValidoException("El perfil proporcionado no es válido: " + datos.perfil());
            }// Convertimos el String a TipoPerfil

            Perfil perfil = new Perfil(usuario, tipoPerfil); // Creamos el perfil
            perfilRepository.save(perfil); // Guardamos el perfil


        // Crear respuesta con los datos del usuario
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDocumento(),
                new DatosDireccion(
                        usuario.getDireccion().getCalle(),
                        usuario.getDireccion().getDistrito(),
                        usuario.getDireccion().getCiudad(),
                        usuario.getDireccion().getNumero(),
                        usuario.getDireccion().getComplemento()
                ),
                perfil.getPerfil().name(), // Convertimos TipoPerfil a String para la respuesta
                usuario.getLogin(),
                usuario.getClave(),
                usuario.getFotoUrl()
        );

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaUsuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
        }
    }


    //    @GetMapping
//    public ResponseEntity<Page<DatosListaUsuario>> listar(
//            @PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion) {
//        Page<DatosListaUsuario> usuarios = usuarioRepository.findAll(paginacion).map(DatosListaUsuario::new);
//        return ResponseEntity.ok(usuarios);
//    }
    @GetMapping
    public ResponseEntity<Page<DatosListaUsuario>> listar(
            @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacion) {

        // Filtramos los usuarios activos
        Page<Usuario> usuariosActivos = usuarioRepository.findByActivoTrue(paginacion);

        // Convertimos los usuarios a DatosListaUsuario
        Page<DatosListaUsuario> datosListaUsuarios = usuariosActivos.map(DatosListaUsuario::new);

        return ResponseEntity.ok(datosListaUsuarios);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        // Verificar si el usuario existe y está activo en un solo paso
        Boolean activo = usuarioRepository.findActivoById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));

        // Si no está activo, lanzar una excepción
        if (!activo) {
            throw new IllegalStateException("El usuario está inactivo.");
        }

        // Cargar el usuario para desactivarlo
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));

        usuario.inactivar();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> activarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.activar();
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDocumento(),
                new DatosDireccion(
                        usuario.getDireccion().getCalle(),
                        usuario.getDireccion().getDistrito(),
                        usuario.getDireccion().getCiudad(),
                        usuario.getDireccion().getNumero(),
                        usuario.getDireccion().getComplemento()
                ),
                usuario.getPerfil(),
                usuario.getLogin(),
                usuario.getClave(),
                usuario.getFotoUrl()
        );
        return ResponseEntity.ok(datosRespuestaUsuario);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        // Obtener el usuario por ID
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarDatos(datosActualizarUsuario);

        // Buscar o crear un perfil para el usuario
        Perfil perfil = perfilRepository.findById(usuario.getId())
                .orElseGet(() -> new Perfil(usuario, TipoPerfil.valueOf(datosActualizarUsuario.perfil().toUpperCase())));

        perfil.setPerfil(TipoPerfil.valueOf(datosActualizarUsuario.perfil().toUpperCase())); // Actualizamos el perfil
        perfilRepository.save(perfil); // Guardamos el perfil

        // Crear la respuesta con los datos actualizados
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDocumento(),
                new DatosDireccion(
                        usuario.getDireccion().getCalle(),
                        usuario.getDireccion().getDistrito(),
                        usuario.getDireccion().getCiudad(),
                        usuario.getDireccion().getNumero(),
                        usuario.getDireccion().getComplemento()
                ),
                perfil.getPerfil().name(), // Convertimos TipoPerfil a String
                usuario.getLogin(),
                usuario.getClave(),
                usuario.getFotoUrl()
        );

        return ResponseEntity.ok(datosRespuestaUsuario);
    }

}

