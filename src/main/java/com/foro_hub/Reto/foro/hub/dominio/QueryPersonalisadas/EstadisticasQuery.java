package com.foro_hub.Reto.foro.hub.dominio.QueryPersonalisadas;

import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstadisticasQuery extends JpaRepository<Topico, Long> {

    // Consulta para obtener el total de tópicos activos
    @Query("""
        SELECT COUNT(t)
        FROM Topico t
        WHERE t.activo = true
    """)
    Long countActiveTopics();

    // Consulta para obtener el total de respuestas activas
    @Query("""
        SELECT COUNT(r)
        FROM Respuesta r
        WHERE r.activo = true
    """)
    Long countActiveResponses();

    // Consulta para obtener los tópicos activos que no tienen respuestas activas
    @Query("""
        SELECT t
        FROM Topico t
        WHERE t.activo = true AND NOT EXISTS (
            SELECT r
            FROM Respuesta r
            WHERE r.topico = t AND r.activo = true
        )
    """)
    List<Topico> findActiveTopicsWithoutResponses();



    @org.springframework.data.jpa.repository.Query("""
    SELECT t
    FROM Topico t
    WHERE t.activo = true AND t.id NOT IN (
        SELECT DISTINCT r.topico.id
        FROM Respuesta r
        WHERE r.activo = true
    )
    ORDER BY t.fechaCreacion DESC
""")
    Page<Topico> findActiveTopicsWithoutResponsesOrderedByFechaCreacion(Pageable pageable);

    @Query("""
    SELECT t
    FROM Topico t
    WHERE t.activo = true 
    AND NOT EXISTS (
        SELECT r
        FROM Respuesta r
        WHERE r.topico = t AND r.activo = true
    )
    AND t.fechaCreacion <= :fechaLimite
""")
    List<Topico> findTopicsWithoutResponsesOlderThan(java.time.LocalDateTime fechaLimite);

}

