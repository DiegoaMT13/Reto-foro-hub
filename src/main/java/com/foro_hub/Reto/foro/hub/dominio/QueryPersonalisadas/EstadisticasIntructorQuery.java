package com.foro_hub.Reto.foro.hub.dominio.QueryPersonalisadas;

import com.foro_hub.Reto.foro.hub.dominio.topico.respuesta.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EstadisticasIntructorQuery extends JpaRepository<Respuesta, Long> {


        @org.springframework.data.jpa.repository.Query("""
            SELECT r
            FROM Respuesta r
            WHERE r.usuario.perfil = :perfilUsuario AND r.activo = true AND r.fechaCreacion = (
            SELECT MAX(r2.fechaCreacion)
            FROM Respuesta r2
            WHERE r2.usuario.perfil = :perfilUsuario AND r2.activo = true
                )
        """)
    Respuesta findTopByPerfilUsuarioAndActivoTrueOrderByFechaCreacionDesc(@Param("perfilUsuario") String perfilUsuario);

        @org.springframework.data.jpa.repository.Query("""
                SELECT r
                FROM Respuesta r
                WHERE r.usuario.perfil = :perfilUsuario AND r.activo = true
                ORDER BY r.fechaCreacion DESC
            """)
    Page<Respuesta> findAllByPerfilUsuarioAndActivoTrue(@Param("perfilUsuario") String perfilUsuario, Pageable pageable);

}

