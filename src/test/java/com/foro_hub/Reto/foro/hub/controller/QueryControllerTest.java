package com.foro_hub.Reto.foro.hub.controller;

import com.foro_hub.Reto.foro.hub.dominio.QueryPersonalisadas.EstadisticasIntructorQuery;
import com.foro_hub.Reto.foro.hub.dominio.QueryPersonalisadas.EstadisticasQuery;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosListarRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import com.foro_hub.Reto.foro.hub.dominio.usuario.Usuario;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class QueryControllerTest {

    @Mock
    private EstadisticasIntructorQuery queryIstructor;

    @Mock
    private EstadisticasQuery estadisticasQuery;

    @InjectMocks
    private QueryController queryController;

    @Test
    void obtenerRespuestaMasRecienteDeInstructor() {
        // Crear el mock de Usuario
        Usuario mockUsuario = new Usuario("Genesys Rondon", "genesys@example.com");

        // Crear el mock de Topico
        Topico mockTopico = new Topico();
        mockTopico.setTitulo("Título del tópico");

        // Crear el mock de Respuesta y configurar sus propiedades
        Respuesta mockRespuesta = new Respuesta();
        mockRespuesta.setId(1L);
        mockRespuesta.setMensajeSolucion("Respuesta reciente de instructor");
        mockRespuesta.setUsuario(mockUsuario); // Asignar el usuario mock
        mockRespuesta.setTopico(mockTopico);  // Asignar el tópico mock

        // Configurar el comportamiento del mock del repositorio
        when(queryIstructor.findTopByPerfilUsuarioAndActivoTrueOrderByFechaCreacionDesc("INSTRUCTOR"))
                .thenReturn(mockRespuesta);

        // Llamar al método del controlador
        ResponseEntity<?> response = queryController.obtenerRespuestaMasRecienteDeInstructor();

        // Validar los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof DatosListarRespuesta);

        // Validar que el DTO tiene los datos esperados
        DatosListarRespuesta datosRespuesta = (DatosListarRespuesta) response.getBody();
        assertEquals("Respuesta reciente de instructor", datosRespuesta.mensajeSolucion()); // Acceder directamente al atributo
        assertEquals("Genesys Rondon", datosRespuesta.nombreUsuario()); // Acceder directamente al atributo
        assertEquals("Título del tópico", datosRespuesta.tituloTopico()); // Verificar que el título del tópico está correctamente asignado
    }



    @Test
    void obtenerRespuestaMasRecienteDeInstructor_noHayRespuestas() {
        when(queryIstructor.findTopByPerfilUsuarioAndActivoTrueOrderByFechaCreacionDesc("INSTRUCTOR"))
                .thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> queryController.obtenerRespuestaMasRecienteDeInstructor());

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No se encontró ninguna respuesta reciente de un instructor", exception.getReason());
    }

    @Test
    void listarRespuestasDeInstructores() {
        // Crear el mock de Usuario
        Usuario mockUsuario = new Usuario("Genesys Rondon", "genesys@example.com");

        // Crear el mock de Topico
        Topico mockTopico = new Topico();
        mockTopico.setTitulo("Título del Topico");

        // Crear el mock de Respuesta y asignar el mock de Usuario y Topico
        Respuesta mockRespuesta = new Respuesta();
        mockRespuesta.setId(1L);
        mockRespuesta.setUsuario(mockUsuario); // Asignar el usuario mock
        mockRespuesta.setTopico(mockTopico);   // Asignar el topico mock

        // Crear el mock de Page
        Pageable pageable = PageRequest.of(0, 10, Sort.by("fechaCreacion").descending());
        Page<Respuesta> mockPage = new PageImpl<>(List.of(mockRespuesta), pageable, 1);

        // Configurar el comportamiento del mock del repositorio
        when(queryIstructor.findAllByPerfilUsuarioAndActivoTrue("INSTRUCTOR", pageable))
                .thenReturn(mockPage);

        // Llamar al método del controlador
        ResponseEntity<Page<DatosListarRespuesta>> response = queryController.listarRespuestasDeInstructores(pageable);

        // Validar los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());

        // Validar que el DTO tiene los datos esperados
        DatosListarRespuesta datosRespuesta = response.getBody().getContent().get(0);
        assertEquals("Genesys Rondon", datosRespuesta.nombreUsuario()); // Asegurarse de que el nombre del usuario esté correctamente asignado
        assertEquals("Título del Topico", datosRespuesta.tituloTopico());
    }

    @Test
    void listarRespuestasDeInstructores_noHayRespuestas() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("fechaCreacion").descending());
        Page<Respuesta> mockPage = Page.empty();
        when(queryIstructor.findAllByPerfilUsuarioAndActivoTrue("INSTRUCTOR", pageable))
                .thenReturn(mockPage);

        ResponseEntity<Page<DatosListarRespuesta>> response = queryController.listarRespuestasDeInstructores(pageable);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void obtenerEstadisticas() {
        when(estadisticasQuery.countActiveTopics()).thenReturn(5L);
        when(estadisticasQuery.countActiveResponses()).thenReturn(20L);
        when(estadisticasQuery.findActiveTopicsWithoutResponses()).thenReturn(List.of(new Topico()));

        ResponseEntity<Map<String, Object>> response = queryController.obtenerEstadisticas();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertEquals(5L, body.get("totalTopicos"));
        assertEquals(20L, body.get("totalRespuestas"));
        assertEquals(1, body.get("topicosSinRespuestas"));
    }
    @Test
    void obtenerEstadisticas_sinDatos() {
        when(estadisticasQuery.countActiveTopics()).thenReturn(0L);
        when(estadisticasQuery.countActiveResponses()).thenReturn(0L);
        when(estadisticasQuery.findActiveTopicsWithoutResponses()).thenReturn(List.of());

        ResponseEntity<Map<String, Object>> response = queryController.obtenerEstadisticas();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertEquals(0L, body.get("totalTopicos"));
        assertEquals(0L, body.get("totalRespuestas"));
        assertEquals(0, body.get("topicosSinRespuestas"));
    }


    @Test
    void obtenerTopicosSinRespuestas_sinDatos() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("fechaCreacion").descending());
        Page<Topico> mockPage = Page.empty();
        when(estadisticasQuery.findActiveTopicsWithoutResponsesOrderedByFechaCreacion(pageable))
                .thenReturn(mockPage);

        ResponseEntity<Page<Map<String, Object>>> response = queryController.obtenerTopicosSinRespuestas(pageable);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}