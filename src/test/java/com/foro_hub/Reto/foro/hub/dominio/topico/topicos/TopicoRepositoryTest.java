package com.foro_hub.Reto.foro.hub.dominio.topico.topicos;

import com.foro_hub.Reto.foro.hub.dominio.curso.CategoriaCurso;
import com.foro_hub.Reto.foro.hub.dominio.direccion.Direccion;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import com.foro_hub.Reto.foro.hub.dominio.usuario.UsuarioRepository;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.Perfil;
import com.foro_hub.Reto.foro.hub.dominio.usuario.perfil.TipoPerfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;


@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicoRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Test
    @DisplayName("Debe retornar tópicos activos solo del usuario especificado")
    void findActiveTopicsByUser_escenario1() {

        // Configurar datos de prueba
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
        topico1.setStatusPregunta(StatusPregunta.valueOf("DUDA")); // Asignar un valor adecuado para 'status_pregunta'
        topico1.setTitulo("Título del tópico 1, de Diego"); // Asignar un valor adecuado para 'titulo'
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

        // Ejecutar la prueba
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<Topico> resultados = topicoRepository.findActiveTopicsByUser("diego.lopez", pageable);

        // Verificar resultados
        assertNotNull(resultados);
        assertEquals(1, resultados.getTotalElements());
        assertEquals("Título del tópico 1, de Diego", resultados.getContent().get(0).getTitulo());
    }


    @Test
    @DisplayName("El resultado no debe ser nulo, Debe haber exactamente 2 tópicos activos")
    void findActiveTopics_escenario2() {
        // Crear y guardar usuarios
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
        // Imprimir el estado de los tópicos


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

        Direccion direccion3 = new Direccion(
                "Avenida 6",
                "Distrito 9",
                "Bogotá",
                "8",
                "a"
        );

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Pablo Neruda");
        usuario3.setEmail("Pablo@example.com");
        usuario3.setDocumento("853456789");
        usuario3.setTelefono("58512348");
        usuario3.setActivo(false);
        usuario3.setLogin("Pablo.Neruda");
        usuario3.setClave("813123");
        usuario3.setDireccion(direccion3);
        usuario3.setFotoUrl("https://www.universolorca.com/wp-content/uploads/2019/12/Pablo-Neruda.jpg");
        Perfil perfil3 = new Perfil(usuario3, TipoPerfil.ALUMNO);
        usuario3.setPerfil(perfil3.toString());
        usuarioRepository.save(usuario3);


        // Crear y guardar tópicos
        Topico topico1 = new Topico();
        topico1.setActivo(true);
        topico1.setUsuario(usuario1);
        topico1.setMensaje("Mensaje de prueba para el tópico 1");
        topico1.setStatusPregunta(StatusPregunta.valueOf("DUDA")); // Asignar un valor adecuado para 'status_pregunta'
        topico1.setTitulo("Título del tópico 1"); // Asignar un valor adecuado para 'titulo'
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


        Topico topicoInactivo1 = new Topico();
        topicoInactivo1.setActivo(false);
        topicoInactivo1.setUsuario(usuario3);
        topicoInactivo1.setMensaje("Mensaje de prueba para el tópico inactivo");
        topicoInactivo1.setStatusPregunta(StatusPregunta.valueOf("DUDA")); // Asignar un valor adecuado para 'status_pregunta'
        topicoInactivo1.setTitulo("Título del tópico inactivo"); // Asignar un valor adecuado para 'titulo'
        topicoInactivo1.setCurso(CategoriaCurso.valueOf("BACKEND"));
        topicoRepository.save(topicoInactivo1);


        // Ejecutar la prueba
        Pageable pageable = Pageable.ofSize(10);
        Page<Topico> result = topicoRepository.findActiveTopics(pageable);

        // Verificar los resultados
        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(2, result.getContent().size(), "Debe haber exactamente 2 tópicos activos");


    }

}

