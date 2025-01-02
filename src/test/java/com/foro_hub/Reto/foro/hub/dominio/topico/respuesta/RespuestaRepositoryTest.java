package com.foro_hub.Reto.foro.hub.dominio.topico.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.curso.CategoriaCurso;
import com.foro_hub.Reto.foro.hub.dominio.direccion.Direccion;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.StatusPregunta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.TopicoRepository;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.Perfil;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.TipoPerfil;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RespuestaRepositoryTest {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Debe encontrar respuestas activas por ID de tópico")
    void findRespuestasByTopicoIdAndActivoTrue_escenario1() {
        // Crear un usuario
        Direccion direccion1 = new Direccion(
                "Avenida 7",
                "Distrito 4",
                "Bogotá",
                "4",
                "a"
        );

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Diego Lopez");
        usuario1.setEmail("Diego@example.com");
        usuario1.setDocumento("123456789");
        usuario1.setTelefono("5551234");
        usuario1.setActivo(true);
        usuario1.setLogin("diego.lopez");
        usuario1.setClave("789123");
        usuario1.setDireccion(direccion1);
        usuario1.setFotoUrl("https://imgcdn.stablediffusionweb.com/2024/9/15/74bfaded-4966-4cfe-9e6e-da76f55037ac.jpg");
        Perfil perfil1 = new Perfil(usuario1, TipoPerfil.ALUMNO);
        usuario1.setPerfil(perfil1.toString());
        usuarioRepository.save(usuario1);


        // Crear un tópico
        Topico topico1 = new Topico();
        topico1.setActivo(true);
        topico1.setUsuario(usuario1);
        topico1.setMensaje("Mensaje de prueba para el tópico 1");
        topico1.setStatusPregunta(StatusPregunta.valueOf("DUDA"));
        topico1.setTitulo("Título del tópico 1, de Diego");
        topico1.setCurso(CategoriaCurso.valueOf("BACKEND"));
        topicoRepository.save(topico1);

        // Crear respuestas asociadas al tópico
        Respuesta respuestaActiva = new Respuesta();
        respuestaActiva.setTopico(topico1);
        respuestaActiva.setUsuario(usuario1);
        respuestaActiva.setMensajeSolucion("Respuesta activa");
        respuestaActiva.setActivo(true);
        respuestaActiva.setFechaCreacion(LocalDateTime.now());
        respuestaRepository.save(respuestaActiva);

        Respuesta respuestaInactiva = new Respuesta();
        respuestaInactiva.setTopico(topico1);
        respuestaInactiva.setUsuario(usuario1);
        respuestaInactiva.setMensajeSolucion("Respuesta inactiva");
        respuestaInactiva.setActivo(false);
        respuestaInactiva.setFechaCreacion(LocalDateTime.now());
        respuestaRepository.save(respuestaInactiva);

        // Ejecutar el método del repositorio
        Page<Respuesta> respuestas = respuestaRepository.findRespuestasByTopicoIdAndActivoTrue(
                topico1.getId(), PageRequest.of(0, 10)
        );

        // Verificar resultados
        assertEquals(1, respuestas.getTotalElements());
        assertEquals("Respuesta activa", respuestas.getContent().get(0).getMensajeSolucion());
    }


    @Test
    @DisplayName("Debe devolver respuestas solo para el tópico específico")
    void debeDevolverRespuestasSoloParaElTopicoEspecifico_escenario2() {
        // Crear usuarios y tópicos
        Direccion direccion1 = new Direccion(
                "Avenida 7",
                "Distrito 4",
                "Bogotá",
                "4",
                "a"
        );

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Diego Lopez");
        usuario1.setEmail("Diego@example.com");
        usuario1.setDocumento("123456789");
        usuario1.setTelefono("5551234");
        usuario1.setActivo(true);
        usuario1.setLogin("diego.lopez");
        usuario1.setClave("789123");
        usuario1.setDireccion(direccion1);
        usuario1.setFotoUrl("https://imgcdn.stablediffusionweb.com/2024/9/15/74bfaded-4966-4cfe-9e6e-da76f55037ac.jpg");
        Perfil perfil1 = new Perfil(usuario1, TipoPerfil.ALUMNO);
        usuario1.setPerfil(perfil1.toString());
        usuarioRepository.save(usuario1);

        Direccion direccion2 = new Direccion(
                "Avenida 5",
                "Distrito 3",
                "Bogotá",
                "3",
                "a"
        );

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Daenerys Targaryen");
        usuario2.setEmail("Daenerys@example.com");
        usuario2.setDocumento("723456789");
        usuario2.setTelefono("5551234");
        usuario2.setActivo(true);
        usuario2.setLogin("Daenerys.Targaryen");
        usuario2.setClave("889123");
        usuario2.setDireccion(direccion2);
        usuario2.setFotoUrl("https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg");
        Perfil perfil2 = new Perfil(usuario2, TipoPerfil.ALUMNO);
        usuario2.setPerfil(perfil2.toString());
        usuarioRepository.save(usuario2);

        Topico topico1 = new Topico();
        topico1.setActivo(true);
        topico1.setUsuario(usuario1);
        topico1.setMensaje("Mensaje de prueba para el tópico 1");
        topico1.setStatusPregunta(StatusPregunta.valueOf("DUDA"));
        topico1.setTitulo("Título del tópico 1, de Diego");
        topico1.setCurso(CategoriaCurso.valueOf("BACKEND"));
        topicoRepository.save(topico1);

        Topico topico2 = new Topico();
        topico2.setActivo(true);
        topico2.setUsuario(usuario2);
        topico2.setMensaje("Mensaje de prueba para el tópico 2");
        topico2.setStatusPregunta(StatusPregunta.valueOf("DUDA")); // Asignar un valor adecuado para 'status_pregunta'
        topico2.setTitulo("Título del tópico 2"); // Asignar un valor adecuado para 'titulo'
        topico2.setCurso(CategoriaCurso.valueOf("BACKEND"));
        topicoRepository.save(topico2);



        // Crear respuestas activas asociadas a cada tópico
        Respuesta respuesta1 = new Respuesta();
        respuesta1.setTopico(topico1);
        respuesta1.setUsuario(usuario2);
        respuesta1.setMensajeSolucion("Respuesta para Tópico 1");
        respuesta1.setActivo(true);
        respuestaRepository.save(respuesta1);

        Respuesta respuesta2 = new Respuesta();
        respuesta2.setTopico(topico2);
        respuesta2.setUsuario(usuario1);
        respuesta2.setMensajeSolucion("Respuesta para Tópico 2");
        respuesta2.setActivo(true);
        respuestaRepository.save(respuesta2);

        // Ejecutar consulta para tópico 1
        Page<Respuesta> respuestasTopico1 = respuestaRepository.findRespuestasByTopicoIdAndActivoTrue(
                topico1.getId(), PageRequest.of(0, 10)
        );

        // Verificar resultados
        assertEquals(1, respuestasTopico1.getTotalElements());
        assertEquals("Respuesta para Tópico 1", respuestasTopico1.getContent().get(0).getMensajeSolucion());
    }
    @Test
    @DisplayName("Debe desactivar una respuesta correctamente")
    void debeDesactivarRespuesta_escenario3() {


        // Crear usuarios y tópicos
        Direccion direccion1 = new Direccion(
                "Avenida 7",
                "Distrito 4",
                "Bogotá",
                "4",
                "a"
        );

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Diego Lopez");
        usuario1.setEmail("Diego@example.com");
        usuario1.setDocumento("123456789");
        usuario1.setTelefono("5551234");
        usuario1.setActivo(true);
        usuario1.setLogin("diego.lopez");
        usuario1.setClave("789123");
        usuario1.setDireccion(direccion1);
        usuario1.setFotoUrl("https://imgcdn.stablediffusionweb.com/2024/9/15/74bfaded-4966-4cfe-9e6e-da76f55037ac.jpg");
        Perfil perfil1 = new Perfil(usuario1, TipoPerfil.ALUMNO);
        usuario1.setPerfil(perfil1.toString());
        usuarioRepository.save(usuario1);

        // Crear usuario, tópico y respuesta
        Direccion direccion2 = new Direccion(
                "Avenida 5",
                "Distrito 3",
                "Bogotá",
                "3",
                "a"
        );

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Daenerys Targaryen");
        usuario2.setEmail("Daenerys@example.com");
        usuario2.setDocumento("723456789");
        usuario2.setTelefono("5551234");
        usuario2.setActivo(true);
        usuario2.setLogin("Daenerys.Targaryen");
        usuario2.setClave("889123");
        usuario2.setDireccion(direccion2);
        usuario2.setFotoUrl("https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg");
        Perfil perfil2 = new Perfil(usuario2, TipoPerfil.ALUMNO);
        usuario2.setPerfil(perfil2.toString());
        usuarioRepository.save(usuario2);

        Topico topico2 = new Topico();
        topico2.setActivo(true);
        topico2.setUsuario(usuario2);
        topico2.setMensaje("Mensaje de prueba para el tópico 2");
        topico2.setStatusPregunta(StatusPregunta.valueOf("DUDA")); // Asignar un valor adecuado para 'status_pregunta'
        topico2.setTitulo("Título del tópico 2"); // Asignar un valor adecuado para 'titulo'
        topico2.setCurso(CategoriaCurso.valueOf("BACKEND"));
        topicoRepository.save(topico2);

        Respuesta respuesta2 = new Respuesta();
        respuesta2.setTopico(topico2);
        respuesta2.setUsuario(usuario1);
        respuesta2.setMensajeSolucion("Respuesta para Tópico 2");
        respuesta2.setActivo(true);
        respuestaRepository.save(respuesta2);

        // Desactivar la respuesta
        respuesta2.desactivar();
        respuestaRepository.save(respuesta2);

        // Verificar que la respuesta esté inactiva
        Respuesta respuestaDesactivada = respuestaRepository.findById(respuesta2.getId()).orElseThrow();
        assertFalse(respuestaDesactivada.isActivo());
    }
}


