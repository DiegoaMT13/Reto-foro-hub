package com.foro_hub.Reto.foro.hub.dominio.topico.topicos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
  //  Page<Topico> findByActivoTrue(Pageable pageable);

  //  Page<Topico> findByActivoTrueAndUsuarioId(Long usuarioId, Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE t.activo = true AND t.usuario.login = :login")
    Page<Topico> findActiveTopicsByUser(@Param("login") String login, Pageable pageable);

   // Page<Topico> findByActivoTrueAndUsuarioLogin(String loginUsuario, Pageable paginacion);

    @Query("SELECT t FROM Topico t WHERE t.activo = true")
    Page<Topico> findActiveTopics(Pageable pageable);


   // List<Topico> findAllByActivoTrue();

    boolean existsByTituloAndUsuarioId(@NotBlank String titulo, @NotNull Long aLong);

   // Topico findFirstByUsuarioId(Long usuarioId);

    List<Topico> findByUsuarioId(Long usuarioId);


}


