package com.foro_hub.Reto.foro.hub.dominio.topico.respuesta;

import com.foro_hub.Reto.foro.hub.dominio.topico.topicos.Topico;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

   // Page<Respuesta> findByTopicoActivoTrue(Pageable pageable);
   // Page<Respuesta> findAllByActivoTrue(Pageable paginacion);
    List<Respuesta> findByTopicoId(Long topicoId);
   // Page<Respuesta> findByTopicoId(Long idTopico, Pageable pageable);
    boolean existsByMensajeSolucionAndUsuarioIdAndTopicoId(String mensajeSolucion, Long usuarioId, Long topicoId);
    @Query("""
        SELECT r
        FROM Respuesta r
        WHERE r.topico.id = :idTopico AND r.activo = true
    """)
    Page<Respuesta> findRespuestasByTopicoIdAndActivoTrue(@Param("idTopico") Long idTopico, Pageable pageable);


    Page<Respuesta> findAllByActivoTrueAndUsuarioLogin(String login, Pageable pageable);

    List<Respuesta> findByTopicoAndActivoTrue(Topico topico);


    long countByUsuarioIdAndTopicoId(@NotNull Long aLong, @NotNull Long aLong1);


}
