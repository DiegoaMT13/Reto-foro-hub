package com.foro_hub.Reto.foro.hub.controller;

import com.foro_hub.Reto.foro.hub.dominio.QueryPersonalisadas.EstadisticasIntructorQuery;
import com.foro_hub.Reto.foro.hub.dominio.QueryPersonalisadas.EstadisticasQuery;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.dto.DatosListarRespuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;
import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/query")
@SecurityRequirement(name = "bearer-key")
public class QueryController {

   @Autowired
   private EstadisticasIntructorQuery queryIstructor; // Inyectar el nuevo repositorio

    @Autowired
    private EstadisticasQuery estadisticasQuery;


    //***********Query personalizada **********************************************************************
    @GetMapping("/instructor/reciente")
    public ResponseEntity<?> obtenerRespuestaMasRecienteDeInstructor() {
        // Buscar la respuesta más reciente de un usuario con perfil "INSTRUCTOR"
        Respuesta respuesta = queryIstructor.findTopByPerfilUsuarioAndActivoTrueOrderByFechaCreacionDesc("INSTRUCTOR");

        // Validar si se encontró una respuesta
        if (respuesta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró ninguna respuesta reciente de un instructor");
        }

        // Crear el DTO para devolver la información
        DatosListarRespuesta datosRespuesta = new DatosListarRespuesta(respuesta);

        return ResponseEntity.ok(datosRespuesta);
    }

    @GetMapping("/instructores/respuestas")
    public ResponseEntity<Page<DatosListarRespuesta>> listarRespuestasDeInstructores(

            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        // Obtener respuestas paginadas de instructores
        Page<Respuesta> respuestas;
        respuestas = queryIstructor.findAllByPerfilUsuarioAndActivoTrue("INSTRUCTOR", pageable);

        // Convertir las respuestas a DTO
        Page<DatosListarRespuesta> datosRespuestas = respuestas.map(DatosListarRespuesta::new);

        // Retornar la página de respuestas
        return ResponseEntity.ok(datosRespuestas);
    }
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        try {
            Long totalTopicos = estadisticasQuery.countActiveTopics();
            Long totalRespuestas = estadisticasQuery.countActiveResponses();
            List<Topico> topicosSinRespuestas = estadisticasQuery.findActiveTopicsWithoutResponses();

            Map<String, Object> respuesta = Map.of(
                    "totalTopicos", totalTopicos,
                    "totalRespuestas", totalRespuestas,
                    "topicosSinRespuestas", topicosSinRespuestas.size()
            );

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener estadísticas", e);
        }
    }
    @GetMapping("/topicos/sin-respuestas")
    public ResponseEntity<Page<Map<String, Object>>> obtenerTopicosSinRespuestas(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        // Invocar el método del repositorio con paginación
        Page<Topico> topicosSinRespuestas = estadisticasQuery.findActiveTopicsWithoutResponsesOrderedByFechaCreacion(pageable);

        // Transformar a un formato JSON con paginación
        Page<Map<String, Object>> respuesta = topicosSinRespuestas.map(topico -> Map.of(
                "id", topico.getId(),
                "titulo", topico.getTitulo(),
                "fechaCreacion", topico.getFechaCreacion()
        ));

        return ResponseEntity.ok(respuesta);
    }
}
